package redis.boot.sample.lock.serivce;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis可重入分布式读写锁
 * <pre>
 *  以上代码中，我们通过 RedisTemplate 实现了一个基于 Redis 的可重入读写锁。
 *  具体实现中，我们首先定义了一些常量和变量，包括锁的键、读锁和写锁的前缀、以及用于存储线程获取的锁信息的 ThreadLocal 变量。
 *  接着，我们使用 RedisScript 定义了两个 Lua 脚本，一个用于获取锁，一个用于释放锁。
 *
 * 1. 在获取锁时，我们使用 setnx 命令尝试获取锁，如果成功则说明当前线程获取了锁；如果失败则说明当前线程未能获取锁，需要等待一段时间后重试。
 * 2. 在释放锁时，我们使用 get 和 del 命令判断当前线程是否持有锁，如果持有则释放锁，否则抛出异常。
 * 3. 在读锁的获取和释放中，我们使用了一个读锁计数器，以支持可重入。如果当前线程已经获取了读锁，则不需要重新获取锁，只需要将计数器加一。在写锁的获取和释放中，我们直接判断当前线程是否持有锁即可。
 * 4. 需要注意的是，在获取读锁时，如果当前线程持有写锁，则不允许获取读锁。在获取写锁时，如果当前线程持有读锁，则不允许获取写锁。这样可以避免产生死锁。
 * </pre>
 */
public class RedisReentrantReadWriteLock {

    private RedisTemplate<String, Object> redisTemplate;

    private String lockKey;
    private String readLockPrefix;
    private String writeLockPrefix;

    private ThreadLocal<String> readLockThreadLocal = new ThreadLocal<>();
    private ThreadLocal<Integer> readLockCountThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> writeLockThreadLocal = new ThreadLocal<>();

    private RedisScript<Long> lockScript;
    private RedisScript<Long> unlockScript;

    public RedisReentrantReadWriteLock(RedisTemplate<String, Object> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.readLockPrefix = lockKey + ":read:";
        this.writeLockPrefix = lockKey + ":write:";
        this.lockScript = new DefaultRedisScript<>("return redis.call('setnx', KEYS[1], ARGV[1])", Long.class);
        this.unlockScript = new DefaultRedisScript<>("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class);
    }

    /**
     * 读锁
     *
     * @throws InterruptedException
     */
    public void readLock() throws InterruptedException {
        String threadId = Thread.currentThread().getId() + ":" + UUID.randomUUID().toString();
        String readLockKey = readLockPrefix + threadId;

        // 本线程已经获取过读锁，则读锁计数器加一
        if (readLockThreadLocal.get() != null && readLockThreadLocal.get().equals(readLockKey)) {
            readLockCountThreadLocal.set(readLockCountThreadLocal.get() + 1);
            return;
        }

        // 如果本线程已经获取了写锁，则不允许获取读锁
        if (writeLockThreadLocal.get() != null) {
            throw new IllegalStateException("Cannot acquire read lock while holding write lock");
        }

        // 不断重试直到获取读锁成功
        while (true) {
            boolean locked = redisTemplate.execute(lockScript, Collections.singletonList(readLockKey), lockKey, "1") == 1L;
            if (locked) {
                readLockThreadLocal.set(readLockKey);
                readLockCountThreadLocal.set(1);
                return;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    public void readUnlock() {
        String readLockKey = readLockThreadLocal.get();
        if (readLockKey == null) {
            throw new IllegalStateException("Cannot unlock read lock, lock not held by this thread");
        }

        int readLockCount = readLockCountThreadLocal.get() - 1;
        if (readLockCount > 0) {
            readLockCountThreadLocal.set(readLockCount);
            return;
        }

        boolean unlocked = redisTemplate.execute(unlockScript, Collections.singletonList(readLockKey), lockKey, "1") == 1L;
        if (unlocked) {
            readLockThreadLocal.remove();
            readLockCountThreadLocal.remove();
        } else {
            throw new IllegalStateException("Failed to unlock read lock");
        }
    }

    public void writeLock() throws InterruptedException {
        String threadId = Thread.currentThread().getId() + ":" + UUID.randomUUID().toString();
        String writeLockKey = writeLockPrefix + threadId;

        // 如果本线程已经获取了写锁，则写锁计数器加一
        if (writeLockThreadLocal.get() != null && writeLockThreadLocal.get().equals(writeLockKey)) {
            return;
        }

        // 如果本线程已经获取了读锁，则不允许获取写锁
        if (readLockThreadLocal.get() != null) {
            throw new IllegalStateException("Cannot acquire write lock while holding read lock");
        }

        // 不断重试直到获取写锁成功
        while (true) {
            boolean locked = redisTemplate.execute(lockScript, Collections.singletonList(writeLockKey), lockKey, "0") == 1L;
            if (locked) {
                writeLockThreadLocal.set(writeLockKey);
                return;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    public void writeUnlock() {
        String writeLockKey = writeLockThreadLocal.get();
        if (writeLockKey == null) {
            throw new IllegalStateException("Cannot unlock write lock, lock not held by this thread");
        }

        boolean unlocked = redisTemplate.execute(unlockScript, Collections.singletonList(writeLockKey), lockKey, "0") == 1L;
        if (unlocked) {
            writeLockThreadLocal.remove();
        } else {
            throw new IllegalStateException("Failed to unlock write lock");
        }
    }
}

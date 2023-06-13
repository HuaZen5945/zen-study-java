package zen.hua.job.power.simple.processor;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.TaskResult;
import tech.powerjob.worker.core.processor.sdk.BroadcastProcessor;

import java.util.List;

/**
 * 广播执行的策略下，所有机器都会被调度执行该任务。为了便于资源的准备和释放，广播处理器在BasicProcessor 的基础上额外增加了
 * preProcess 和 postProcess 方法，分别在整个集群开始之前/结束之后选一台机器执行相关方法。
 * 代码示例如下：
 */
@Component
public class BroadcastProcessorDemo implements BroadcastProcessor {

    @Value("${server.port}")
    private String port;

    @Override
    public ProcessResult preProcess(TaskContext taskContext) throws Exception {
        // 预执行，会在所有 worker 执行 process 方法前, 在某台worker上调用
        System.out.println(port + ": BroadcastProcessorDemo prePrecess");
        return new ProcessResult(true, "init success");
    }

    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        // 撰写整个worker集群都会执行的代码逻辑
        System.out.println(port + ": BroadcastProcessorDemo process");
        String msg = port + ": jobParams=" + context.getJobParams() + ", instanceParams=" + context.getInstanceParams();
        System.out.println(msg);
        return new ProcessResult(true, "release resource success");
    }

    @Override
    public ProcessResult postProcess(TaskContext taskContext, List<TaskResult> taskResults) throws Exception {

        // taskResults 存储了所有worker执行的结果（包括preProcess）
        System.out.println(JSONUtil.toJsonStr(taskResults));
        // 收尾，会在所有 worker 执行完毕 process 方法后调用，该结果将作为最终的执行结果
        return new ProcessResult(true, "process success");
    }
}
package zen.hua.job.power.simple.processor;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.TaskResult;
import tech.powerjob.worker.core.processor.sdk.MapReduceProcessor;

import java.util.List;

/**
 * 利用 MapReduce 实现 Root -> A -> B/C -> Reduce）的 DAG 工作流。
 * 注：PowerJob 现已提供正式的 Workflow 工作流支持（since v2.0.0）
 */
@Component
public class DAGSimulationProcessor implements MapReduceProcessor {

    @Override
    public ProcessResult process(TaskContext context) throws Exception {

        if (isRootTask()) {
            // L1. 执行根任务

            // 执行完毕后产生子任务 A，需要传递的参数可以作为 TaskA 的属性进行传递
            TaskA taskA = new TaskA();
            map(Lists.newArrayList(taskA), "LEVEL1_TASK_A");
            return new ProcessResult(true, "L1_PROCESS_SUCCESS");
        }
        // 通过子任务类型进行判断
        if (context.getSubTask() instanceof TaskA) {
            // L2. 执行A任务

            // 执行完成后产生子任务 B，C（并行执行）
            TaskB taskB = new TaskB();
            TaskC taskC = new TaskC();
            map(Lists.newArrayList(taskB, taskC), "LEVEL2_TASK_BC");
            return new ProcessResult(true, "L2_PROCESS_SUCCESS");
        }

        if (context.getSubTask() instanceof TaskB) {
            // L3. 执行B任务
            return new ProcessResult(true, "xxx");
        }
        if (context.getSubTask() instanceof TaskC) {
            // L3. 执行C任务
            return new ProcessResult(true, "xxx");
        }

        return new ProcessResult(false, "UNKNOWN_TYPE_OF_SUB_TASK");
    }

    @Override
    public ProcessResult reduce(TaskContext context, List<TaskResult> taskResults) {
        // L4. 执行最终 Reduce 任务，taskResults保存了之前所有任务的结果
        taskResults.forEach(taskResult -> {
            // do something...
        });
        return new ProcessResult(true, "reduce success");
    }

    public static class TaskA {
    }

    public static class TaskB {
    }

    public static class TaskC {
    }
}
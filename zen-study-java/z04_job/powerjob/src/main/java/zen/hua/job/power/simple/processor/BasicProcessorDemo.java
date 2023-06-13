package zen.hua.job.power.simple.processor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;


/***
 * 基础处理器示例：
 * 单机执行的策略下，server 会在所有可用 worker 中选取健康度最佳的机器进行执行。
 * 单机执行任务需要实现接口 BasicProcessor，代码示例如下
 */
@Component
public class BasicProcessorDemo implements BasicProcessor {

    @Value("${server.port}")
    private String port;

    @Override
    public ProcessResult process(TaskContext context) throws Exception {

        // 在线日志功能，可以直接在控制台查看任务日志，非常便捷
        OmsLogger omsLogger = context.getOmsLogger();
        omsLogger.info("BasicProcessorDemo start to process, current JobParams is {}.", context.getJobParams());

        // TaskContext为任务的上下文信息，包含了在控制台录入的任务元数据，常用字段为
        // jobParams（任务参数，在控制台录入），instanceParams（任务实例参数，通过 OpenAPI 触发的任务实例才可能存在该参数）

        // 进行实际处理...
        String msg = port + ": jobParams=" + context.getJobParams() + ", instanceParams=" + context.getInstanceParams();
        System.out.println(msg);

        // 返回结果，该结果会被持久化到数据库，在前端页面直接查看，极为方便
        return new ProcessResult(true, msg);
    }
}
package neo.kafka.consumer.service;

import com.alibaba.fastjson.JSON;
import com.elon.base.constant.TaskCodeConst;
import com.elon.base.model.BIReportTask;
import com.elon.base.service.kafka.KafkaConsumerService;
import com.elon.base.service.kafka.TaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * BI报表生成处理类
 *
 * @author neo
 * @since 2024-05-14
 */
@Component
public class KafkaBIReportHandler extends TaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaBIReportHandler.class);

    @Resource
    private KafkaConsumerService kafkaConsumerService;

    public KafkaBIReportHandler() {
        super(TaskCodeConst.KAFKA_REPORT_EXPORT_BI_REPORT);
    }

    @PostConstruct
    public void init() {
        kafkaConsumerService.registerHandler(this);
    }

    @Override
    public void handle(String taskJson) {
        BIReportTask task = JSON.parseObject(taskJson, BIReportTask.class);
        LOGGER.info("Create BI report. taskCode:{}|taskId:{}", task.getTaskCode(), task.getTaskId());

        // 生成BI报表. 具体处理逻辑略. 等待5秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

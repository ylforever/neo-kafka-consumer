package neo.kafka.consumer;

import com.elon.base.service.kafka.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消费者应用初始化.
 *
 * @author neo
 * @since 2024-05-15
 */
@Component
public class ConsumerApplicationInit implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApplicationInit.class);

    @Resource
    private KafkaConsumerService kafkaConsumerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaConsumerService.initKafkaConsumer();
        LOGGER.info("Init kafka consumer success.");
    }
}

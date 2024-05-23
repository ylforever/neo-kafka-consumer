package neo.kafka.consumer;

import neo.kafka.consumer.service.StreamConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.elon.base", "neo.kafka.consumer"})
public class KafkaConsumerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class);
        StreamConsumerService streamConsumerService = new StreamConsumerService();
        streamConsumerService.wordCountStream();
        LOGGER.info("Start up kafka consumer success.");
    }
}

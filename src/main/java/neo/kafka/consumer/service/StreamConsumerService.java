package neo.kafka.consumer.service;

import com.alibaba.fastjson.JSON;
import com.elon.base.model.WordCountTask;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

import java.util.Arrays;
import java.util.Properties;

public class StreamConsumerService {
    public void wordCountStream() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "lisi");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.5.128:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream("neo-stream");
        KStream<String, String> splitMapStream =  textLines.flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split(" ")));
        KGroupedStream<String, String> groupedStream = splitMapStream.groupBy((key, word) -> word);
        KTable<String, Long> resultTable = groupedStream.count(Materialized.as("counts-store"));
        resultTable.toStream().map((key, value)->{
                    System.out.println("key:"+key+", value:"+value);
                    WordCountTask task = new WordCountTask();
                    task.setWord(key);
                    task.setWordNum(value);
                    return new KeyValue<>(key, JSON.toJSONString(task));
                }).to("quick-stream");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}

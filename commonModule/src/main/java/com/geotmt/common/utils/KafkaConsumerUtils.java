package com.geotmt.common.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;


/**
 * kafka工具类
 *
 * Created by chao.zhao on 2018/11/7. */
public class KafkaConsumerUtils {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消费数据
     * @param record 记录
     */
    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());
    }
}

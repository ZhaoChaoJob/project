package com.geotmt.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;


/**
 * kafka工具类
 *
 * Created by chao.zhao on 2018/11/7. */
public class KafkaProducerUtils {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 发送到kafka
     * @param topic 主题
     * @param key 唯一键
     * @param data 数据
     */
    @Async
    public void sendKafka(String topic,String key,String data){
        //发送成功后回调
        //发送成功后回调
        SuccessCallback successCallback = new SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("发送成功");
            }
        };

        //发送失败回调
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败");
                System.out.println("发送失败");
                ex.printStackTrace();
            }
        };
        ListenableFuture<SendResult<String, String>> sendResultListenableFuture = this.kafkaTemplate.send(topic,key,data) ;
        sendResultListenableFuture.addCallback(successCallback,failureCallback);


    }
}

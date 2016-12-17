//package firstapi.kafka;
package com.uw.adc;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


 // To use this producer for testing, create a topic with the name "fail-test'
public class ProducerTest {
 public static void main(String[] args) throws InterruptedException { 
  Properties props = new Properties();
  props.put("zk.connect", "localhost:2181"); 
  props.put("serializer.class","kafka.serializer.StringEncoder");
  props.put("metadata.broker.list", "localhost:9092");
  ProducerConfig config = new ProducerConfig(props);
  Producer<String, String> producer = new Producer<String, String>(config);
  for (long nEvents = 0; nEvents < 10; nEvents++){
   System.out.println("Creating events " + nEvents);
   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
   TimeUnit.SECONDS.sleep(10);
   String msg =  timestamp + " " + nEvents + "Failure testing ";
   producer.send(new KeyedMessage<String, String>("fail-test", msg));

  } 
 }
}

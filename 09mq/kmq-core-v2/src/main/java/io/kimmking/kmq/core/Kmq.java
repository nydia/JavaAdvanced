package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class Kmq {

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
//        this.queue = new LinkedBlockingQueue(capacity);
        this.list = new ArrayList<>(capacity);
    }

    private String topic;

    private int capacity;

    //private LinkedBlockingQueue<KmqMessage> queue;

    private ArrayList<KmqMessage> list;

    private int position = 0;

    public boolean send(KmqMessage message) {
        //return queue.offer(message);
        boolean b = list.add(message);
        if(b){
            position ++;
        }
        return b;
    }

    public KmqMessage poll(KmqConsumer consumer) {


//        return queue.poll();
        int offset = consumer.getOffset();
        if(offset > position -1){
            return null;
        }
        KmqMessage message = list.get(offset++);
        consumer.setOffset(offset);
        return message;
    }

//    @SneakyThrows
//    public KmqMessage poll(long timeout) {
//        return queue.poll(timeout, TimeUnit.MILLISECONDS);
//    }

}

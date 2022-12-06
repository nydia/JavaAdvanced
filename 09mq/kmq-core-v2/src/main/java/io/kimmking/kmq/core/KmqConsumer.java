package io.kimmking.kmq.core;

public class KmqConsumer<T> {

    private final KmqBroker broker;

    private Kmq kmq;

    private int offset = 0; //消费位置

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

//    public KmqMessage<T> poll(long timeout) {
//        return kmq.poll(timeout);
//    }

    public KmqMessage<T> poll(){
        return kmq.poll(this);
    }

    public int getOffset(){
        return this.offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }


}

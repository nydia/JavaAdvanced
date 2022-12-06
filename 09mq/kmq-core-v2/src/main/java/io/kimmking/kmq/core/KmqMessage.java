package io.kimmking.kmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KmqMessage<T> {

    private HashMap<String,Object> headers;

    private T body;

    private int position;

    public KmqMessage(HashMap<String,Object> headers, T body){
        this.body = body;
        this.headers = headers;
    }

}

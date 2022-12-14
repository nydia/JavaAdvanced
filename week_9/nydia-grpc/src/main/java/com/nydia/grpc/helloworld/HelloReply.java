package com.nydia.grpc.helloworld;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/15 00:00
 * @Description: HelloReply
 */
@Data
@Builder
public class HelloReply {

    @Builder.Default
    private String message = "1";
}

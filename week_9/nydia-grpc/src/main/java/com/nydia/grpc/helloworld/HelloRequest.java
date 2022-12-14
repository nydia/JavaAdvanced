package com.nydia.grpc.helloworld;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/15 00:00
 * @Description: HelloRequest
 */
@Data
@Builder
public class HelloRequest {

    @Builder.Default
    private String name = "1";

}

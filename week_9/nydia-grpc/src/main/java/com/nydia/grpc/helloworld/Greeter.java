package com.nydia.grpc.helloworld;

import io.grpc.ServerServiceDefinition;

public class Greeter implements io.grpc.BindableService {

    @Override
    public ServerServiceDefinition bindService() {
        return null;
    }

}
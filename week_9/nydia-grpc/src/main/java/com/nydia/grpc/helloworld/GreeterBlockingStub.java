package com.nydia.grpc.helloworld;

public class GreeterBlockingStub extends io.grpc.stub.AbstractBlockingStub<GreeterBlockingStub> {
    private GreeterBlockingStub(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
        super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterBlockingStub build(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
        return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public HelloReply sayHello(HelloRequest request) {
        return io.grpc.stub.ClientCalls.blockingUnaryCall(
                getChannel(), getSayHelloMethod(), getCallOptions(), request);
    }
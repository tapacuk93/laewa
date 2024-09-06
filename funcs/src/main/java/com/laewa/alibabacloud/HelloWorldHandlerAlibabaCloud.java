package com.laewa.alibabacloud;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.laewa.HelloWorldRequest;
import com.laewa.HelloWorldResponse;

public class HelloWorldHandlerAlibabaCloud implements PojoRequestHandler<HelloWorldRequest, HelloWorldResponse> {

    @Override
    public HelloWorldResponse handleRequest(HelloWorldRequest input, Context context) {
        return null;
    }
}

// package example;
//
//import com.aliyun.fc.runtime.Context;
//import com.aliyun.fc.runtime.PojoRequestHandler;
//
//public class HelloFC implements PojoRequestHandler<SimpleRequest, SimpleResponse> {
//
//    @Override
//    public SimpleResponse handleRequest(SimpleRequest request, Context context) {
//        String message = "Hello, " + request.getFirstName() + " " + request.getLastName();
//        return new SimpleResponse(message);
//    }
//}
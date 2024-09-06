package com.laewa.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.laewa.HelloWorldHandler;
import com.laewa.HelloWorldRequest;
import com.laewa.HelloWorldResponse;

public class HelloWorldHandlerAws implements HelloWorldHandler, RequestHandler<HelloWorldRequest, HelloWorldResponse> {
    @Override
    public void handle() {

    }

    @Override
    public HelloWorldResponse  handleRequest(HelloWorldRequest input, Context context) {
        return null;
    }
}



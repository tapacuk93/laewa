package com.laewa;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        var response = new APIGatewayProxyResponseEvent();

        response.setStatusCode(200);
        response.setHeaders(Map.of("Content-Type", "text/html"));

        try {
            response.setBody(loadIndex());
        } catch (Exception e) {
            response.setBody(e.getMessage());
        }

        return response;
    }

    public String loadIndex() {
        try {
            var inputStream = getClass().getResourceAsStream("index.html");
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: index.html");
            }

            try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package com.laewa;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

@SuppressWarnings("unused")
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        var response = new APIGatewayProxyResponseEvent();

        String path = request.getPathParameters() != null ? request.getPathParameters().get("proxy") : "/";
        String fileName = (path == null || path.isBlank() || path.equals("/")) ? "index.html" :
                path.startsWith("/") ? path.substring(1) : path;

        try {
            byte[] contentBytes = loadStaticFile(fileName);
            String contentType = getContentType(fileName);
            boolean isBinary = isBinaryMimeType(contentType);

            response.setStatusCode(200);
            response.setHeaders(Map.of(
                    "Content-Type", contentType
            ));

            if (isBinary) {
                response.setIsBase64Encoded(true);
                response.setBody(Base64.getEncoder().encodeToString(contentBytes));
            } else {
                response.setBody(new String(contentBytes));
            }

        } catch (Exception e) {
            response.setStatusCode(404);
            response.setHeaders(Map.of("Content-Type", "text/plain"));
            response.setBody("File not found: " + fileName + ", path: " + fileName);
        }

        return response;
    }

    private byte[] loadStaticFile(String fileName) {

        String basePath = "/" + getClass().getPackageName().replace('.', '/');

        try (InputStream inputStream = getClass().getResourceAsStream(basePath + "/" + fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file: " + fileName, e);
        }
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".html")) return "text/html";
        if (fileName.endsWith(".css")) return "text/css";
        if (fileName.endsWith(".js")) return "application/javascript";
        if (fileName.endsWith(".json")) return "application/json";
        if (fileName.endsWith(".png")) return "image/png";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
        if (fileName.endsWith(".svg")) return "image/svg+xml";
        return "application/octet-stream";
    }

    private boolean isBinaryMimeType(String contentType) {
        return !(contentType.startsWith("text/") || contentType.equals("application/json") || contentType.equals("application/javascript"));
    }
}
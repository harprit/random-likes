package com.hs.randomlikes;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    @Test
    public void handleRequest() {
        // given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> params = new HashMap<>();
        params.put("name", "harprits");
        request.setPathParameters(params);

        // when
        APIGatewayProxyResponseEvent response = new App().handleRequest(request);

        // then
        assertEquals(200, response.getStatusCode().intValue());
        assertTrue(Long.parseLong(response.getBody()) > 0);
    }
}
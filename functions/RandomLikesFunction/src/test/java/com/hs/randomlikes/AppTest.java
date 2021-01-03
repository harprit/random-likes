package com.hs.randomlikes;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    /**
     * This is an integration test as it makes actual calls to AWS SSM Parameter Store and Twitter APIs.
     *
     * 1. AWS SSM Parameter Store is invoked for fetching Twitter APIs auth keys and tokens.
     * By default code would look for an SSM Parameter defined with name: TWITTER_AUTH of
     * StringList type containing comma separated auth strings (see TwitterConnector class).
     *
     * 2. Twitter APIs are invoked to fetch a list of liked tweets (see RandomLikesFinder class).
     */
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
        String tweetId = response.getBody();
        System.out.println("Random Liked Tweet Id: " + tweetId);
        assertTrue(Long.parseLong(tweetId) > 0);
    }
}
package com.hs.randomlikes;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Pair;
import twitter4j.Twitter;

public class App {

    private static Logger LOG = LoggerFactory.getLogger(App.class);

    private final Twitter twitter;

    public App() {
        this.twitter = TwitterConnector.getInstance();
    }

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input) {
        String username = input.getPathParameters().get("name");
        LOG.info("Going to process for username: " + username);


        Pair<Integer, String> pair = new RandomLikesFinder(twitter, username).find();
        return new APIGatewayProxyResponseEvent().withStatusCode(pair.left()).withBody(pair.right());
    }
}

package com.hs.randomlikes;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Pair;

import java.util.Objects;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private final RandomLikesFinder randomLikesFinder;

    public App() {
        this.randomLikesFinder = new RandomLikesFinder();
    }

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input) {
        String username = Objects.requireNonNull(input.getPathParameters().get("name"));
        LOG.info("Going to process for username: " + username);


        Pair<Integer, String> pair = randomLikesFinder.findFor(username);
        return new APIGatewayProxyResponseEvent().withStatusCode(pair.left()).withBody(pair.right());
    }
}

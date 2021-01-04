package com.hs.randomlikes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Objects;
import java.util.Optional;

public class TwitterConnector {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterConnector.class);

    private static final String ENV_VARIABLE_HOLDING_PARAMETER_NAME = "TWITTER_AUTH_PARAM";
    private static final String PARAMETER_NAME_DEFAULT = "TWITTER_AUTH";

    public static Twitter getInstance() {
        String[] parameters = Objects.requireNonNull(getTwitterAuthParameters());

        LOG.info("Going to create new Twitter connection");

        ConfigurationBuilder twitterConfigBuilder = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(parameters[0])
                .setOAuthConsumerSecret(parameters[1])
                .setOAuthAccessToken(parameters[2])
                .setOAuthAccessTokenSecret(parameters[3]);

        return new TwitterFactory(twitterConfigBuilder.build()).getInstance();
    }

    private static String[] getTwitterAuthParameters() {

        SsmClient ssmClient = SsmClient.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .build();

        String paramName = Optional.ofNullable(System.getenv(ENV_VARIABLE_HOLDING_PARAMETER_NAME)).orElse(PARAMETER_NAME_DEFAULT);

        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paramName)
                    .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            return parameterResponse.parameter().value().split(",");
        } catch (SsmException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
}

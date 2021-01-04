package com.hs.randomlikes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Pair;
import twitter4j.*;

import java.util.concurrent.ThreadLocalRandom;

public class RandomLikesFinder {

    private static final Logger LOG = LoggerFactory.getLogger(RandomLikesFinder.class);

    private static final int MAX_PAGE_NUM = 3_000;

    private static final int MAX_TRY_COUNT = 5;

    private final Twitter twitter;

    RandomLikesFinder() {
        LOG.info("Creating App");
        this.twitter = TwitterConnector.getInstance();
    }

    Pair<Integer, String> findFor(String username) {
        try {
            if (rateLimitExceeded()) {
                return Pair.of(500, "Sorry, Twitter rate limit exceeded. Please try again later.");
            }

            int max = MAX_PAGE_NUM;

            for (int tryCount = 0; tryCount < MAX_TRY_COUNT; tryCount++) {
                int pageNum = getRandom(max);

                ResponseList<Status> userFavorites = twitter.getFavorites(username, new Paging(pageNum, 1));

                LOG.info("tryCount: {} max: {} randomPageNumber: {} userFavorites.size(): {}", tryCount, max, pageNum, userFavorites.size());

                if (userFavorites.size() == 0) {
                    // no tweet was found most likely because of high range, retry by making pageNum the new max
                    max = pageNum;
                    continue;
                }

                return Pair.of(200, Long.valueOf(userFavorites.get(0).getId()).toString());
            }

            if (rateLimitExceeded()) {
                return Pair.of(500, "Sorry, Twitter rate limit exceeded. Please try again later.");
            }

        } catch (Exception e) { // catch all for TwitterException and other errors
            LOG.error(e.getMessage());
        }

        return Pair.of(500, "Sorry, unable to find anything.");
    }

    private boolean rateLimitExceeded() throws TwitterException {
        return twitter.getRateLimitStatus("favorites").get("/favorites/list").getRemaining() <= 0;
    }

    // upperBound is exclusive
    private int getRandom(int upperBound) {
        return ThreadLocalRandom.current().nextInt(1, upperBound);
    }
}

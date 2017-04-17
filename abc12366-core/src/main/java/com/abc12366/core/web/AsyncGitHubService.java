package com.abc12366.core.web;

import com.abc12366.core.model.GitHubUser;
import com.abc12366.core.service.GitHubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-24 11:38 AM
 * @since 1.0.0
 */
@Component
public class AsyncGitHubService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AsyncGitHubService.class);

    private final GitHubLookupService gitHubLookupService;

    public AsyncGitHubService(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<GitHubUser> page1 = gitHubLookupService.findUser("PivotalSoftware");
        Future<GitHubUser> page2 = gitHubLookupService.findUser("CloudFoundry");
        Future<GitHubUser> page3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
    }
}

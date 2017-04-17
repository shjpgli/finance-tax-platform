package com.abc12366.core.service;

import com.abc12366.core.model.GitHubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-24 11:29 AM
 * @since 1.0.0
 */
@Service
public class GitHubLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLookupService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public Future<GitHubUser> findUser(String user) throws InterruptedException {
        LOGGER.info(user);
        String url = String.format("https://api.github.com/users/%s", user);
        GitHubUser results = restTemplate.getForObject(url, GitHubUser.class);
        // 模拟网络延时
        Thread.sleep(1000L);
        return new AsyncResult<>(results);
    }
}

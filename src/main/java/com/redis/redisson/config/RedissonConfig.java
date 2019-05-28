package com.redis.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * @author liuboren
 * @Title:
 * @Description:
 * @date 2019/5/27 19:50
 */
@Configuration
public class RedissonConfig {

    private final ResourceLoader resourceLoader;

    public RedissonConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Bean
    public RedissonClient redisson(){
        Config config = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:RedissonConfig.yml");
             config = Config.fromYAML(resource.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}

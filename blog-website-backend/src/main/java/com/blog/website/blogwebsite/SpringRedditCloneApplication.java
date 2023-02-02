package com.blog.website.blogwebsite;

import com.blog.website.blogwebsite.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SpringRedditCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedditCloneApplication.class, args);
    }

}

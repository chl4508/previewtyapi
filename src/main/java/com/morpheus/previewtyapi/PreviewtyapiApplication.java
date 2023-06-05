package com.morpheus.previewtyapi;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = { MongoDataAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class PreviewtyapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreviewtyapiApplication.class, args);
        System.out.println("\n\n\n");
        System.out.println("                          ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("                          ┃                       Strat                       ┃");
        System.out.println("                          ┃                     PreViewtyApi                  ┃");
        System.out.println("                          ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println("\n\n\n");
    }

}

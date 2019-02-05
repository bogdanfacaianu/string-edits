package com.stringedits.testrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.string.edits")
@ComponentScan("com.stringedits.testrunner")
public class StringEditsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StringEditsApplication.class, args);
    }
}

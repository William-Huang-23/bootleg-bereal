package com.william.bootleg_bereal.controller;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TestPhotoController {
//    @Autowired
//    private final Environment environment;
//
//    public TestPhotoController(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Order(1)
//    @Test
//    public void test () {
//        System.out.println(environment.getProperty("api.apiKeyHeaderName"));
//    }
}

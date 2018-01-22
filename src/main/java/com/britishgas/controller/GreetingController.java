package com.britishgas.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.britishgas.model.Greeting;

/**
 * Created by Kundan Sharma 
 */
@RestController
@RequestMapping("/")
public class GreetingController {

    @RequestMapping(value = "/greetings", method = RequestMethod.GET)
    public Greeting helloWorld() {
        return Greeting.builder().greetingText("Hello World").build();
    }

    @RequestMapping(value = "/internal-greetings", method = RequestMethod.GET)
    public Greeting helloManagment() {
        return Greeting.builder().greetingText("Hello Management").build();
    }

}
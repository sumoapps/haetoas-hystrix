package com.example.hateoashystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/greeting")
    public Resource<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        Resource<Greeting> greetingResource = new Resource<>(greeting);
        greetingResource.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return greetingResource;
    }

    @HystrixCommand(commandKey = "command")
    @RequestMapping("/greeting-hystrix")
    public Resource<Greeting> greetingWithHistrix(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        Resource<Greeting> greetingResource = new Resource<>(greeting);
        greetingResource.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return greetingResource;
    }
}
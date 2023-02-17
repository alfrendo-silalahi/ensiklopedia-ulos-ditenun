package com.ensiklopediaulos.ditenun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping(path = "/example")
    public String getHello() {
        return "Hello world!";
    }

}

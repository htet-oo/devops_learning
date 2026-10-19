
package com.example.myspringbootapp.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspringbootapp.dto.HomeResponse;



@RestController
public class HomeController {

    @GetMapping("/")
    public HomeResponse home() throws UnknownHostException {

        String hostname = InetAddress.getLocalHost().getHostName();

        return new HomeResponse(
            "Hello From Spring Boot!",
            "my-springboot-app",
            hostname
        );
    }
}

package com.example.rtsp_to_hls.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomeController {

    @GetMapping
    public String getHome() {
        return "index.html";
    }
}

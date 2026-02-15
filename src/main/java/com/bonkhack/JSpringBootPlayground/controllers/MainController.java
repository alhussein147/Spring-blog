package com.bonkhack.JSpringBootPlayground.controllers;

import com.bonkhack.JSpringBootPlayground.models.Post;
import com.bonkhack.JSpringBootPlayground.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class MainController {
    @GetMapping
    public String getMain() {
        return "Hey there!";
    }

}

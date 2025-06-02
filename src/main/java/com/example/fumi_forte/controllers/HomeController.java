package com.example.fumi_forte.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "private home";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Gerente')")
    public String admin(){
        return "admin";
    }
    
}



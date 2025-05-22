package com.ecommerce.ecommerce.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.ecommerce.services.EcommerceConfigService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/config")
@AllArgsConstructor
public class EcommerceConfigController {

    private final EcommerceConfigService ecommerceConfigService;

    
    @PostMapping("/color-nav")
    public ResponseEntity<String> cambiarColorNav(@RequestParam String color) {
        this.ecommerceConfigService.cambiarColorNav(color);
        return new ResponseEntity<>("Color del nav cambiado", HttpStatus.OK);
    }

    
    @PostMapping("/color-fondo")
    public ResponseEntity<String> cambiarColorFondo(@RequestParam String color) {
        this.ecommerceConfigService.cambiarColorFondo(color);
        return new ResponseEntity<>("Color de fondo cambiado", HttpStatus.OK);
    }

}

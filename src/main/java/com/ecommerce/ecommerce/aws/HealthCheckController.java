package com.ecommerce.ecommerce.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    @GetMapping("")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok().body("OK");
    }
}

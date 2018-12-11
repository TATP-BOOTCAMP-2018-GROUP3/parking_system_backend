package com.oocl.parking.controllers;


import com.oocl.parking.models.LoginRequest;
import com.oocl.parking.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path="/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        if (request == null || request.getAccountName() == null || request.getPassword() == null){
            return ResponseEntity.badRequest().build();
        }
        String token = authService.login(request);
        if (token == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("{" + "\"token\": \"" + token + "\"}");
    }
}
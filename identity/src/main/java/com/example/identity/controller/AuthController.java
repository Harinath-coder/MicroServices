package com.example.identity.controller;


import com.example.identity.config.CustomUserDetails;
import com.example.identity.dto.AuthRequest;
import com.example.identity.dto.CredentialsDto;
import com.example.identity.entity.UserCredential;
import com.example.identity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    //private CustomUserDetails customUserDetails;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                // System.out.println(customUserDetails.getAuthorities());
                return new ResponseEntity<>(service.generateToken(authRequest.getUsername()), HttpStatus.OK);
                // return service.generateToken();

            }
//            else {
//                // throw new RuntimeException("invalid access");
//            }
        }catch (Exception e){
           System.out.println(e.getMessage());
        }
        Map<String,Object> res = new HashMap<>();
        res.put("status", false);
        res.put("message","Invalid username or password");
        return new ResponseEntity<Map<String, Object>>(res,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUserCredentials(@RequestBody CredentialsDto credentialsDto){
        String message =  service.saveUserCredentials(credentialsDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/forgotPassword")
    public Map<String, Object> forgotPassword(@RequestParam String username){
        return service.forgotPassword(username);
    }
}
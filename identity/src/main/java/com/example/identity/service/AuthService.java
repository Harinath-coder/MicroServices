package com.example.identity.service;

import com.example.identity.config.CustomUserDetails;
import com.example.identity.config.CustomUserDetailsService;
import com.example.identity.dto.CredentialsDto;
import com.example.identity.dto.ResponseDto;
import com.example.identity.entity.UserCredential;
import com.example.identity.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public Map<String, Object> generateToken(String username) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getAuthorities().toString());
      //  return jwtService.generateToken(username);
        String token =  jwtService.generateToken(userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "token generation success.");
        ResponseDto responseDto = new ResponseDto();
        responseDto.setToken(token);
        responseDto.setUsername(userDetails.getUsername());
        //int size = userDetails.getAuthorities().size();
        responseDto.setRole(userDetails.getAuthorities().toString());
        response.put("data",responseDto);

        return response;
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public String saveUserCredentials(CredentialsDto credentialsDto) {
        UserCredential credential = new UserCredential();
        credential.setPassword(passwordEncoder.encode(credentialsDto.getPassword()));
        credential.setEmpId(credentialsDto.getName());
        credential.setRoles(credentialsDto.getRoles());
        repository.save(credential);
        return "user credentials are saved";
    }

    public Map<String, Object> forgotPassword(String username) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Map<String, Object> response = new HashMap<>();
        //System.out.println(userDetails.getUsername() + "forgot password call");
        if(userDetails.getUsername() == null){
           response.put("status", false);
           response.put("message", "Invalid username");
        }else{
            response.put("status", true);
            response.put("message", "reset password process initialized");
            response.put("data", generateToken(username));
        }
        return response;
    }
}

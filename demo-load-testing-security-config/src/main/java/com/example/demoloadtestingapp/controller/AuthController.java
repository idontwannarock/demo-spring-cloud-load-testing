package com.example.demoloadtestingapp.controller;

import com.example.demoloadtestingapp.security.JwtUtil;
import com.example.demoloadtestingapp.security.SecurityConstants;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("auth")
@RestController
public class AuthController {

    @ApiOperation(value = "Login", notes = "Exchange for JWT with username and password.\nOnly for testing purpose.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam(defaultValue = "user") String username, @RequestParam(defaultValue = "userPass") String password) {
        Map<String, String> responseBody = Maps.newHashMap();
        responseBody.put("jwt", SecurityConstants.PREFIX + JwtUtil.createToken(username, SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        return ResponseEntity.ok(responseBody);
    }
}

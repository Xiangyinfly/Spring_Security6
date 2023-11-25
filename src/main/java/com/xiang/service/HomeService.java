package com.xiang.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    @PreAuthorize("hasRole('ADMIN')")
    public String sayHello() {
        return "hello";
    }
}

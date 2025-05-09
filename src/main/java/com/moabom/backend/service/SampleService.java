package com.moabom.backend.service;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public String getSampleMessage() {
        return "Hello from Sample Service!";
    }
}
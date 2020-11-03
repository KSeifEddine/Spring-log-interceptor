package com.kse.spring.log.controller;

import com.kse.spring.log.dto.HelloLogDto;
import com.kse.spring.log.service.HelloLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloLogController {

    @Autowired
    private final HelloLogService helloLogService;

    @GetMapping("get-hello")
    public HelloLogDto getHello() {
        return helloLogService.getHelloLog();
    }

    @PostMapping("get-hello")
    public HelloLogDto postHelloLog(@RequestBody HelloLogDto helloLogDtoRequest) {
        return helloLogService.potHelloLog(helloLogDtoRequest);
    }
}
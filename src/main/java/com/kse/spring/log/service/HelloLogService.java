package com.kse.spring.log.service;

import com.kse.spring.log.dto.HelloLogDto;
import org.springframework.stereotype.Service;

@Service
public class HelloLogService {

    public HelloLogDto getHelloLog() {
        HelloLogDto helloLogDto = new HelloLogDto();
        helloLogDto.setMessage("Get empty HelloLog");
        return helloLogDto;
    }

    public HelloLogDto potHelloLog(HelloLogDto helloLogDtoRequest) {
        HelloLogDto helloLogDtoResponse = new HelloLogDto();
        helloLogDtoResponse.setMessage("HelloLog posted with message : " + helloLogDtoRequest.getMessage());
        return helloLogDtoResponse;
    }
}
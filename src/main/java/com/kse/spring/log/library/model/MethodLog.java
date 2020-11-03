package com.kse.spring.log.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class MethodLog {

    private String name;
    private Map<Class, Object> params;
    private Object result;
}
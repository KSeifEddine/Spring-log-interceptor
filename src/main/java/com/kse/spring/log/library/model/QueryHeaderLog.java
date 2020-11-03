package com.kse.spring.log.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class QueryHeaderLog {

    private Map<String, String> attributes;
}
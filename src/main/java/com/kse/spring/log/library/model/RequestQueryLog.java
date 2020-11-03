package com.kse.spring.log.library.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RequestQueryLog  extends QueryLog{

    private String method;
    private String url;
    private String sessionId;
    private Map<String, String> params;
    private ClientLog client;
}
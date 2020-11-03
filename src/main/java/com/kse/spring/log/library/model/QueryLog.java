package com.kse.spring.log.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class QueryLog {

    private QueryHeaderLog header;
    private QueryBodyLog body;
}
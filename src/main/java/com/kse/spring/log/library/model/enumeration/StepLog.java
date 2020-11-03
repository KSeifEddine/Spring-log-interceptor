package com.kse.spring.log.library.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StepLog {

    INPUT("input"),
    OUTPUT("output");

    private String stepLogName;

    StepLog(String stepLogName) {
        this.stepLogName = stepLogName;
    }

    @JsonValue
    public String getStepLogName() {
        return stepLogName;
    }
}
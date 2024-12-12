package com.purchase.cart.enums;
public enum ErrorCode {
    INVALID_ORDER("ERR_001"),
    ORDER_CREATION_FAILED("ERR_002"),
    INTERNAL_ERROR("ERR_003");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

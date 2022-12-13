package com.geekbrains.market.winter7.api;


// для отправки ответа в виде токена от back-end на front

public class StringResponse {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }
}

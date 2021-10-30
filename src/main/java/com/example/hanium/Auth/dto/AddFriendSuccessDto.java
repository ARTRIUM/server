package com.example.hanium.Auth.dto;

import lombok.Data;

@Data
public class AddFriendSuccessDto {
    private boolean success;
    private String name;

    public AddFriendSuccessDto(boolean b, String s) {
        this.success = b;
        this.name = s;
    }
}

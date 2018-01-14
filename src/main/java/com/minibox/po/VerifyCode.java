package com.minibox.po;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyCode {
    private String phoneNumber;
    private String verifyCode;

    public VerifyCode(){}
}

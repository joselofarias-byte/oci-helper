package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * @ClassName EnableEnum
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-03-06 16:14
 **/
@Getter
public enum EnableEnum {

    ON("true", "Activado"),
    OFF("false", "Desactivado"),
    ;

    private String code;
    private String desc;

    EnableEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

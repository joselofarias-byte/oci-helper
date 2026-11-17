package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.enums
 * @className: SysCfgTypeEnum
 * @author: Yohann
 * @date: 2024/11/30 17:29
 */
@Getter
public enum SysCfgTypeEnum {

    /**
     * 配置类型
     */
    SYS_INIT_CFG("Y001", "Ajustes generales"),
    SYS_MFA_CFG("Y002", "Ajustes de MFA"),
    SYS_INFO("Y003", "Información del sistema"),

    ;

    SysCfgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;
}

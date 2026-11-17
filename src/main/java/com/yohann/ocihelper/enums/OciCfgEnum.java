package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * <p>
 * OciCfgEnum
 * </p >
 *
 * @author yohann
 * @since 2024/11/8 12:12
 */
@Getter
public enum OciCfgEnum {

    /**
     * OCI配置
     */
    OCI_CFG_USER_ID("user", "ID de usuario"),
    OCI_CFG_TENANT_ID("tenancy", "ID de cuenta OCI"),
    OCI_CFG_REGION("region", "Región"),
    OCI_CFG_FINGERPRINT("fingerprint", "Huella"),
    OCI_CFG_KEY_FILE("key_file", "Ruta de clave privada"),
    ;

    private String type;
    private String desc;

    OciCfgEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

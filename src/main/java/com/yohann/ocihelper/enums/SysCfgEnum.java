package com.yohann.ocihelper.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.enums
 * @className: SysCfgEnum
 * @author: Yohann
 * @date: 2024/11/30 17:29
 */
@Getter
public enum SysCfgEnum {

    /**
     * 系统配置项
     */
    SYS_TG_BOT_TOKEN("Y101", "Token del bot de Telegram", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_TG_CHAT_ID("Y102", "ID personal de Telegram", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_DING_BOT_TOKEN("Y103", "Token del bot de DingTalk", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_DING_BOT_SECRET("Y104", "Secreto del bot de DingTalk", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_MFA_SECRET("Y105", "MFA", SysCfgTypeEnum.SYS_MFA_CFG),
    ENABLE_DAILY_BROADCAST("Y107", "Activar informe diario", SysCfgTypeEnum.SYS_INIT_CFG),
    DAILY_BROADCAST_CRON("Y108", "Cron del informe diario", SysCfgTypeEnum.SYS_INIT_CFG),
    ENABLED_VERSION_UPDATE_NOTIFICATIONS("Y109", "Avisos de nuevas versiones", SysCfgTypeEnum.SYS_INIT_CFG),
    SILICONFLOW_AI_API("Y110", "API de SiliconFlow", SysCfgTypeEnum.SYS_INIT_CFG),
    BOOT_BROADCAST_TOKEN("Y111", "Token de difusión de creación", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_VNC("Y112", "URL de conexión VNC", SysCfgTypeEnum.SYS_INIT_CFG),
    GOOGLE_ONE_CLICK_LOGIN("Y113", "Configuración de acceso con Google", SysCfgTypeEnum.SYS_INIT_CFG),
    SYS_PROXY("Y114", "Proxy global", SysCfgTypeEnum.SYS_INIT_CFG),

    SYS_INFO_VERSION("Y106", "Versión del sistema", SysCfgTypeEnum.SYS_INFO),


    ;

    SysCfgEnum(String code, String desc, SysCfgTypeEnum type) {
        this.code = code;
        this.desc = desc;
        this.type = type;
    }

    private String code;
    private String desc;
    private SysCfgTypeEnum type;


    public static List<SysCfgEnum> getCodeListByType(SysCfgTypeEnum type) {
        return Arrays.stream(values())
                .filter(x -> x.getType() == type)
                .collect(Collectors.toList());
    }
}

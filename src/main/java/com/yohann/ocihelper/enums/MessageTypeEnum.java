package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * <p>
 * MessageTypeEnum
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/11/8 12:12
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 消息通知类型
     */
    MSG_TYPE_TELEGRAM("TG", "Notificaciones de Telegram"),
    MSG_TYPE_DING_DING("DING", "Notificaciones de DingTalk"),
    ;

    private String type;
    private String desc;

    MessageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

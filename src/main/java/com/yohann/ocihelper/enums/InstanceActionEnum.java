package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * <p>
 * InstanceActionEnum
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/11/26 11:22
 */
@Getter
public enum InstanceActionEnum {
    /**
     * 实例操作
     */
    ACTION_STOP("STOP", "Detener instancia"),
    ACTION_START("START", "Iniciar instancia"),
    ACTION_RESET("RESET", "Reiniciar instancia"),

    ;

    private String action;
    private String desc;

    InstanceActionEnum(String action, String desc) {
        this.action = action;
        this.desc = desc;
    }

    public static InstanceActionEnum getActionEnum(String action) {
        for (InstanceActionEnum actionEnum : InstanceActionEnum.values()) {
            if (action.equals(actionEnum.getAction())) {
                return actionEnum;
            }
        }
        return null;
    }
}

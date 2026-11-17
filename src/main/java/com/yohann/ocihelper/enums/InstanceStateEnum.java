package com.yohann.ocihelper.enums;

import lombok.Getter;

/**
 * <p>
 * InstanceStateEnum
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/11/8 13:08
 */
@Getter
public enum InstanceStateEnum {

    LIFECYCLE_STATE_RUNNING("RUNNING","En ejecución"),
    LIFECYCLE_STATE_TERMINATED("TERMINATED","Terminada")
    ;

    private String state;
    private String desc;

    InstanceStateEnum(String state, String desc) {
        this.state = state;
        this.desc = desc;
    }
}

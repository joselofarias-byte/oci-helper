package com.yohann.ocihelper.bean.params.sys;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * <p>
 * LoginParams
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/11/14 12:04
 */
@Data
public class LoginParams {

    @NotBlank(message = "El usuario es obligatorio")
    private String account;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    private Integer mfaCode;
}

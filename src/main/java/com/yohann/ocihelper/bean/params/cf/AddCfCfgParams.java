package com.yohann.ocihelper.bean.params.cf;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @ClassName AddCfCfgParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-03-20 16:57
 **/
@Data
public class AddCfCfgParams {

    @NotBlank(message = "El dominio es obligatorio")
    private String domain;
    @NotBlank(message = "El ID de zona es obligatorio")
    private String zoneId;
    @NotBlank(message = "El token API es obligatorio")
    private String apiToken;
}

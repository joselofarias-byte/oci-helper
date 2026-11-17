package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @ClassName StartVncParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-06-04 11:02
 **/
@Data
public class StartVncParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;
    private String compartmentId;
    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
}

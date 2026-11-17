package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * <p>
 * GetInstanceCfgInfoParams
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/12/18 18:02
 */
@Data
public class GetInstanceCfgInfoParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
}

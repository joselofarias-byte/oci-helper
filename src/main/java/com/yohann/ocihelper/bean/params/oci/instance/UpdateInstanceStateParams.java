package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params
 * @className: UpdateInstanceStateParams
 * @author: Yohann
 * @date: 2024/11/28 21:28
 */
@Data
public class UpdateInstanceStateParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;

    @NotBlank(message = "La operación de instancia es obligatoria")
    private String action;

}

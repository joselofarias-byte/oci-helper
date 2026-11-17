package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params
 * @className: TerminateInstanceParams
 * @author: Yohann
 * @date: 2024/11/28 21:48
 */
@Data
public class TerminateInstanceParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;

    @NotNull(message = "Debes indicar si se conserva el volumen de arranque")
    private Integer preserveBootVolume;

    @NotBlank(message = "El código de verificación es obligatorio")
    private String captcha;
}

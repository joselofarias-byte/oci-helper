package com.yohann.ocihelper.bean.params.oci.cfg;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params
 * @className: SendCaptchaParams
 * @author: Yohann
 * @date: 2024/11/28 21:55
 */
@Data
public class SendCaptchaParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
}

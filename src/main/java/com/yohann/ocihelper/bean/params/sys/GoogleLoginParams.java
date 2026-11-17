package com.yohann.ocihelper.bean.params.sys;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.sys
 * @className: GoogleLoginParams
 * @author: Yohann
 * @date: 2026/01/02
 */
@Data
public class GoogleLoginParams {

    @NotBlank(message = "La credencial de Google es obligatoria")
    private String credential;
}

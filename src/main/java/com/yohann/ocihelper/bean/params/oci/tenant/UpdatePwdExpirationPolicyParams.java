package com.yohann.ocihelper.bean.params.oci.tenant;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName UpdatePwdExpirationPolicyParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-09-19 17:05
 **/
@Data
public class UpdatePwdExpirationPolicyParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String cfgId;
    private Integer passwordExpiresAfter;
}

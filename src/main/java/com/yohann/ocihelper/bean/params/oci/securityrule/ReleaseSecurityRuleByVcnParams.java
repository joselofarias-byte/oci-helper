package com.yohann.ocihelper.bean.params.oci.securityrule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>
 * ReleaseSecurityRuleByVcnParams - release all ports for a specific VCN
 * </p>
 *
 * @author yohann
 */
@Data
public class ReleaseSecurityRuleByVcnParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de VCN es obligatorio")
    private String vcnId;
}

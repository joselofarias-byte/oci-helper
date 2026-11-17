package com.yohann.ocihelper.bean.params.oci.securityrule;

import com.oracle.bmc.core.model.IcmpOptions;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @ClassName AddIngressSecurityRuleParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-03-05 15:45
 **/
@Data
public class AddIngressSecurityRuleParams {

    @NotBlank(message = "El ID de configuración API es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de VCN es obligatorio")
    private String vcnId;
    private IngressInfo inboundRule;

    @Data
    public static class IngressInfo{
        private Boolean isStateless;
        private String sourceType;
        private String source;
        private String protocol;
        private IcmpOptions icmpOptions;
        private String sourcePort;
        private String destinationPort;
        private String description;
    }
}

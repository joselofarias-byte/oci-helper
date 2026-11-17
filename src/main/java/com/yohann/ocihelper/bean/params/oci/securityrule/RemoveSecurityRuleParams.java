package com.yohann.ocihelper.bean.params.oci.securityrule;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName RemoveSecurityRuleParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-03-05 16:06
 **/
@Data
public class RemoveSecurityRuleParams {

    @NotBlank(message = "El ID de configuración API es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de VCN es obligatorio")
    private String vcnId;
    private Integer type;
    private List<String> ruleIds;
}

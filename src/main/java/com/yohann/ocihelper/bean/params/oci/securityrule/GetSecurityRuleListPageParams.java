package com.yohann.ocihelper.bean.params.oci.securityrule;

import com.yohann.ocihelper.bean.params.BasicPageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.oci.securityrule
 * @className: GetSecurityRuleListPageParams
 * @author: Yohann
 * @date: 2025/3/1 16:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetSecurityRuleListPageParams extends BasicPageParams {

    @NotBlank(message = "El ID de configuración API es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de VCN es obligatorio")
    private String vcnId;
    private Integer type;
    private boolean cleanReLaunch;
}

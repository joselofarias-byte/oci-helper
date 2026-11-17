package com.yohann.ocihelper.bean.params.oci.instance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName CreateNetworkLoadBalancerParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-08-21 09:41
 **/
@Data
public class CreateNetworkLoadBalancerParams {

    @NotBlank(message = "El ID de configuración OCI es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
    @NotNull(message = "El puerto SSH es obligatorio")
    private Integer sshPort;
}

package com.yohann.ocihelper.bean.params.oci.instance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName Close500MParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-08-25 10:08
 **/
@Data
public class Close500MParams {

    @NotBlank(message = "El ID de configuración OCI es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
    @NotNull(message = "Debes indicar si se conserva el balanceador de carga")
    private Boolean retainBl;
    @NotNull(message = "Debes indicar si se conserva la puerta de enlace NAT")
    private Boolean retainNatGw;
}

package com.yohann.ocihelper.bean.params.oci.instance;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName UpdateShapeParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-09-15 09:54
 **/
@Data
public class UpdateShapeParams {

    @NotBlank(message = "El ID de configuración OCI es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;
    @NotBlank(message = "El tipo de instancia es obligatorio, por ejemplo: VM.Standard.A1.Flex")
    private String shape;
}

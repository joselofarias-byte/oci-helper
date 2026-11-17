package com.yohann.ocihelper.bean.params.oci.instance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.oci.instance
 * @className: AutoRescueParams
 * @author: Yohann
 * @date: 2025/7/19 14:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AutoRescueParams extends GetInstanceCfgInfoParams {

    @NotBlank(message = "El nombre de instancia es obligatorio")
    private String name;

    @NotNull(message = "Debes indicar si se conserva la copia del volumen")
    private Boolean keepBackupVolume = true;
}

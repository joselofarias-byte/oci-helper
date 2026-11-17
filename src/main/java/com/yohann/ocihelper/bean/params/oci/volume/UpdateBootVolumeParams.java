package com.yohann.ocihelper.bean.params.oci.volume;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.oci
 * @className: UpdateBootVolumeParams
 * @author: Yohann
 * @date: 2025/1/4 19:27
 */
@Data
public class UpdateBootVolumeParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;
    @NotBlank(message = "El ID del volumen de arranque es obligatorio")
    private String bootVolumeId;
    @NotBlank(message = "El tamaño del volumen de arranque es obligatorio")
    private String bootVolumeSize;
    @NotBlank(message = "Las VPU del volumen de arranque son obligatorias")
    private String bootVolumeVpu;
}

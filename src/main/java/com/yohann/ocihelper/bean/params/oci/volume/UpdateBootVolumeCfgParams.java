package com.yohann.ocihelper.bean.params.oci.volume;

import com.yohann.ocihelper.bean.params.oci.instance.GetInstanceCfgInfoParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * <p>
 * UpdateBootVolumeCfgParams
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/12/18 18:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateBootVolumeCfgParams extends GetInstanceCfgInfoParams {

    @NotBlank(message = "El tamaño del volumen de arranque es obligatorio")
    private String bootVolumeSize;
    @NotBlank(message = "Las VPU del volumen de arranque son obligatorias")
    private String bootVolumeVpu;
}

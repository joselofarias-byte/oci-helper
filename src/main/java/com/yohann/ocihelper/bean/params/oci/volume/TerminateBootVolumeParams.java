package com.yohann.ocihelper.bean.params.oci.volume;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * TerminateBootVolumeParams
 * </p >
 *
 * @author yuhui.fan
 * @since 2025/1/3 19:04
 */
@Data
public class TerminateBootVolumeParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;
    @NotEmpty(message = "El ID del volumen de arranque es obligatorio")
    private List<String> bootVolumeIds;
}

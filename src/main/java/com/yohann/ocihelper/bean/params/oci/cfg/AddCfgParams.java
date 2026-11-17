package com.yohann.ocihelper.bean.params.oci.cfg;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * AddCfgParams
 * </p >
 *
 * @author yohann
 * @since 2024/11/13 14:30
 */
@Data
public class AddCfgParams {

    @NotBlank(message = "El nombre de configuración es obligatorio")
    private String username;

    @NotBlank(message = "La configuración es obligatoria")
    private String ociCfgStr;

    @NotNull(message = "La clave privada es obligatoria")
    private MultipartFile file;
}

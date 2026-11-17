package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * CreateInstanceParams
 * </p >
 *
 * @author yohann
 * @since 2024/11/13 19:26
 */
@Data
public class CreateInstanceParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String userId;
    @NotBlank(message = "La CPU es obligatoria")
    private String ocpus;
    @NotBlank(message = "La memoria es obligatoria")
    private String memory;
    @NotNull(message = "El espacio en disco es obligatorio")
    private Integer disk;
    @NotBlank(message = "La arquitectura es obligatoria")
    private String architecture;
    @NotNull(message = "El intervalo es obligatorio")
    private Integer interval;
    @NotNull(message = "La cantidad de instancias es obligatoria")
    private Integer createNumbers;
    @NotBlank(message = "El sistema operativo es obligatorio")
    private String operationSystem;
    @NotBlank(message = "La contraseña de root es obligatoria")
    private String rootPassword;

    private boolean joinChannelBroadcast = false;

}

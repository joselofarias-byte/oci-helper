package com.yohann.ocihelper.bean.params.oci.instance;

import jakarta.validation.Valid;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params
 * @className: CreateInstanceBatchParams
 * @author: Yohann
 * @date: 2024/11/16 0:04
 */
@Data
public class CreateInstanceBatchParams {

    @NotEmpty(message = "La lista de ID de configuración es obligatoria")
    private List<String> userIds;
    @Valid
    private InstanceInfo instanceInfo;

    @Data
    public static class InstanceInfo {
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
    }
}

package com.yohann.ocihelper.bean.params.oci.task;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Parameters for updating an existing create-instance task.
 * The service will stop the current task and re-submit it with the new settings.
 */
@Data
public class UpdateCreateTaskParams {

    /** ID of the task to update */
    @NotBlank(message = "El ID de tarea es obligatorio")
    private String taskId;

    /** CPU cores */
    @NotBlank(message = "La CPU es obligatoria")
    private String ocpus;

    /** Memory in GB */
    @NotBlank(message = "La memoria es obligatoria")
    private String memory;

    /** Boot-volume size in GB */
    @NotNull(message = "El espacio en disco es obligatorio")
    private Integer disk;

    /** Instance shape / architecture */
    @NotBlank(message = "La arquitectura es obligatoria")
    private String architecture;

    /** Polling interval in seconds */
    @NotNull(message = "El intervalo es obligatorio")
    private Integer interval;

    /** Number of instances to create */
    @NotNull(message = "La cantidad de instancias es obligatoria")
    private Integer createNumbers;

    /** OS image keyword */
    @NotBlank(message = "El sistema operativo es obligatorio")
    private String operationSystem;

    /** root password set via freeform tag */
    private String rootPassword;
}

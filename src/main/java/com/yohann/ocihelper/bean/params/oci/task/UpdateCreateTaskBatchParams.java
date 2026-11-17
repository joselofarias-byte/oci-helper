package com.yohann.ocihelper.bean.params.oci.task;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Parameters for batch-updating existing create-instance tasks.
 * The service will stop each current task and re-submit it with the new settings,
 * staggering restarts by 5 seconds each to avoid API bursts.
 */
@Data
public class UpdateCreateTaskBatchParams {

    /** IDs of the tasks to update */
    @NotEmpty(message = "La lista de ID de tareas es obligatoria")
    private List<String> taskIds;

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

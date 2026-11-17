package com.yohann.ocihelper.bean.params.oci.task;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * PauseCreateParams - batch pause or resume create tasks
 * </p>
 *
 * @author yohann
 */
@Data
public class PauseCreateParams {

    @NotEmpty(message = "La lista de ID es obligatoria")
    private List<String> idList;
}

package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * <p>
 * UpdateInstanceCfgParams
 * </p >
 *
 * @author yuhui.fan
 * @since 2024/12/18 18:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateInstanceCfgParams extends GetInstanceCfgInfoParams {

    @NotBlank(message = "La CPU es obligatoria")
    private String ocpus;
    @NotBlank(message = "La memoria es obligatoria")
    private String memory;
}

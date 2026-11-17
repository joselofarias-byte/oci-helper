package com.yohann.ocihelper.bean.params.oci.traffic;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.oci.traffic
 * @className: GetTrafficDataParams
 * @author: Yohann
 * @date: 2025/3/7 20:37
 */
@Data
public class GetTrafficDataParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;
    @NotNull(message = "La hora de inicio es obligatoria")
    private Date beginTime;
    @NotNull(message = "La hora de fin es obligatoria")
    private Date endTime;
    @NotBlank(message = "La región es obligatoria")
    private String region;
    @NotBlank(message = "La consulta de entrada es obligatoria")
    private String inQuery;
    @NotBlank(message = "La consulta de salida es obligatoria")
    private String outQuery;
    @NotBlank(message = "El espacio de nombres es obligatorio")
    private String namespace;
}

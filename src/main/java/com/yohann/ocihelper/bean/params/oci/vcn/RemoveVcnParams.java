package com.yohann.ocihelper.bean.params.oci.vcn;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params.oci.vcn
 * @className: RemoveVcnParams
 * @author: Yohann
 * @date: 2025/3/3 22:59
 */
@Data
public class RemoveVcnParams {

    @NotEmpty(message = "La lista de ID de VCN es obligatoria")
    private List<String> vcnIds;
    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;
}

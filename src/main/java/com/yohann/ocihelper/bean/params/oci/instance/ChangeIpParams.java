package com.yohann.ocihelper.bean.params.oci.instance;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.bean.params
 * @className: ChangeIpParams
 * @author: Yohann
 * @date: 2024/11/14 0:03
 */
@Data
public class ChangeIpParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    @NotBlank(message = "El ID de instancia es obligatorio")
    private String instanceId;

    private List<String> cidrList;

    @NotBlank(message = "El ID de VNIC es obligatorio")
    private String vnicId;

    @NotNull(message = "Debes indicar si se actualiza el DNS de Cloudflare")
    private boolean changeCfDns;
    private String domainPrefix;
    private String selectedDomainCfgId;
    private boolean enableProxy;
    private Integer ttl;
    private String remark;
}

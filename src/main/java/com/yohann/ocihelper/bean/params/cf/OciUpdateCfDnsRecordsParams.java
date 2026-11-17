package com.yohann.ocihelper.bean.params.cf;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @ClassName OciUpdateCfDnsRecordsParams
 * @Description:
 * @Author: Yohann_Fan
 * @CreateTime: 2025-03-21 16:09
 **/
@Data
public class OciUpdateCfDnsRecordsParams {

    @NotBlank(message = "El ID de configuración es obligatorio")
    private String cfCfgId;
    @NotBlank(message = "El ID es obligatorio")
    private String id;
//    @NotBlank(message = "域名前缀不能为空")
    private String prefix;
    @NotBlank(message = "El tipo es obligatorio")
    private String type;
    @NotBlank(message = "La dirección IP es obligatoria")
    private String ipAddress;
//    @NotNull(message = "是否代理不能为空")
    private boolean proxied;
//    @NotNull(message = "ttl不能为空")
//    @Min(value = 60)
    private Integer ttl;
    private String comment;
}

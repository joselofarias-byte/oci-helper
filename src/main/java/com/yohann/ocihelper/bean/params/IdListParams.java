package com.yohann.ocihelper.bean.params;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * IdListParams
 * </p >
 *
 * @author yohann
 * @since 2024/11/13 17:00
 */
@Data
public class IdListParams {

    @NotEmpty(message = "El ID es obligatorio")
    private List<String> idList;
}

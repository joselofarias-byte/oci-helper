package com.yohann.ocihelper.bean.params.oci.cost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Parameters for querying OCI Cost Analysis (Usage API).
 *
 * @author Yohann
 */
@Data
public class GetCostAnalysisParams {

    /** OCI config ID */
    @NotBlank(message = "El ID de configuración es obligatorio")
    private String ociCfgId;

    /**
     * Report type, matching OCI built-in report names:
     * COST_BY_SERVICE / COST_BY_SERVICE_AND_DESCRIPTION /
     * COST_BY_SERVICE_AND_SKU / COST_BY_SERVICE_AND_TAG /
     * COST_BY_COMPARTMENT / MONTHLY_COST
     */
    @NotBlank(message = "El tipo de informe es obligatorio")
    private String reportType;

    /** Start date (inclusive), format: yyyy-MM-dd */
    @NotBlank(message = "La fecha de inicio es obligatoria")
    private String startDate;

    /** End date (inclusive), format: yyyy-MM-dd */
    @NotBlank(message = "La fecha de fin es obligatoria")
    private String endDate;

    /**
     * Granularity: DAILY or MONTHLY
     */
    @NotBlank(message = "La agrupación es obligatoria")
    private String granularity;

    /**
     * Query filter: COST or USAGE
     */
    private String queryType;
}

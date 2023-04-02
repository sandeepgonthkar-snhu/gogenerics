package org.drugs.projects.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drug {
    private int id;

    private String drug_id;

    private String brand_name;

    private String generic_name;

    private String manufacturer_name;

    private String description;

    private String product_type;

    private String substance_name;

    private String warnings;

    private String drug_interactions;

    private String precautions;

    private String do_not_use;

    private String pregnancy_or_breastfeeding;

    private String dosage_and_administration;
}

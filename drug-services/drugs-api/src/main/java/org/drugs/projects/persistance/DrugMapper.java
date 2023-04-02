package org.drugs.projects.persistance;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.drugs.projects.model.Drug;

import java.util.List;

public interface DrugMapper {

    @Select("SELECT id, drug_id, brand_name, generic_name, manufacturer_name, description, product_type, " +
            "substance_name, warnings, drug_interactions, precautions, do_not_use, pregnancy_or_breast_feeding, " +
            "dosage_and_administration FROM drugstore.Drugs WHERE brand_name like #{brand_name}")
    List<Drug> findDrugByName(@Param("brand_name") String brand_name);

    @Select("SELECT id, drug_id, brand_name, generic_name, manufacturer_name, description, product_type, " +
            "substance_name, warnings, drug_interactions, precautions, do_not_use, pregnancy_or_breast_feeding, " +
            "dosage_and_administration FROM drugstore.Drugs WHERE id = #{id}")
    Drug findById(@Param("id") int id);
}

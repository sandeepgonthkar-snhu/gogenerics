package org.drugs.projects.persistance;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.drugs.projects.model.Drug;

import java.util.List;

@Slf4j
public class DrugDao {

    private DrugMapper mapper;

    @Inject
    public DrugDao(DrugMapper mapper) {
        this.mapper = mapper;
    }

    public List<Drug> findByBrandName(String brand_name) {
        brand_name = brand_name.endsWith("%") ? brand_name : brand_name + "%";
        return mapper.findDrugByName(brand_name);
    }

    public Drug findById(int id) {
        return mapper.findById(id);
    }

}

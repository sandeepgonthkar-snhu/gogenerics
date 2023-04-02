package org.drugs.projects.services;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.drugs.projects.model.Drug;
import org.drugs.projects.persistance.DrugDao;

import java.util.List;

@Slf4j
public class DrugService {

    private DrugDao dao;

    @Inject
    public DrugService(DrugDao dao) {
        this.dao = dao;
    }

    public List<Drug> findByBrandName(String brand_name) {
        if(Strings.isNullOrEmpty(brand_name)) {
            throw new IllegalArgumentException("Brand_name cannot be empty");
        }
        return dao.findByBrandName(brand_name);
    }

    public Drug findById(int id) {
        return dao.findById(id);
    }
}

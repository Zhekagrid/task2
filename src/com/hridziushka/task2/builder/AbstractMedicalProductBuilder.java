package com.hridziushka.task2.builder;

import com.hridziushka.task2.entity.AbstractMedicalProduct;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMedicalProductBuilder {
    protected List<AbstractMedicalProduct> medProducts;

    public AbstractMedicalProductBuilder() {
        medProducts = new ArrayList<>();
    }

    public List<AbstractMedicalProduct> getMedProducts() {
        return medProducts;
    }

    public abstract void buildMedproductList(String xmlFilePath);
}

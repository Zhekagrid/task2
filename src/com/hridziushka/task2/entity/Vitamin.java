package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Vitamin extends AbstractMedicalProduct {
    static Logger logger = LogManager.getLogger();
    private List<VitaminType> vitaminTypeList;

    public Vitamin(String name, List<String> analogs, Map<VersionType, List<PharmManufacturer>> versionListMap, List<VitaminType> vitaminTypeList) {
        super(name, analogs, versionListMap);
        this.vitaminTypeList = vitaminTypeList;
        logger.log(Level.INFO, "Vitamin was created");
    }

    public Vitamin() {
        logger.log(Level.INFO, "Vitamin was created");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vitamin{");
        sb.append(super.toString());
        sb.append("vitaminTypeList=").append(vitaminTypeList);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vitamin)) return false;
        if (!super.equals(o)) return false;

        Vitamin vitamin = (Vitamin) o;

        return getVitaminTypeList() != null ? getVitaminTypeList().equals(vitamin.getVitaminTypeList()) : vitamin.getVitaminTypeList() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getVitaminTypeList() != null ? getVitaminTypeList().hashCode() : 0);
        return result;
    }

    public List<VitaminType> getVitaminTypeList() {
        return vitaminTypeList;
    }

    public void setVitaminTypeList(List<VitaminType> vitaminTypeList) {
        this.vitaminTypeList = vitaminTypeList;
    }
}

package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Antibiotic extends AbstractMedicalProduct {
    static Logger logger = LogManager.getLogger();
    private boolean needRecipe;

    public Antibiotic() {
        logger.log(Level.INFO, "Antibiotic was created");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Antibiotic{");
        sb.append(super.toString());
        sb.append("needRecipe=").append(needRecipe);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Antibiotic)) return false;
        if (!super.equals(o)) return false;

        Antibiotic that = (Antibiotic) o;

        return isNeedRecipe() == that.isNeedRecipe();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isNeedRecipe() ? 1 : 0);
        return result;
    }

    public boolean isNeedRecipe() {
        return needRecipe;
    }

    public void setNeedRecipe(boolean needRecipe) {
        this.needRecipe = needRecipe;
    }
}

package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PainKiller extends AbstractMedicalProduct {
    static Logger logger = LogManager.getLogger();
    private PainKillerType painCillerType;

    public PainKiller() {
        logger.log(Level.INFO, "PainCiller was created");
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PainKiller{");
        sb.append(super.toString());
        sb.append("painKillerType=").append(painCillerType);

        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PainKiller)) return false;
        if (!super.equals(o)) return false;

        PainKiller that = (PainKiller) o;

        return getPainKillerType() == that.getPainKillerType();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPainKillerType() != null ? getPainKillerType().hashCode() : 0);
        return result;
    }

    public PainKillerType getPainKillerType() {
        return painCillerType;
    }

    public void setPainKillerType(PainKillerType painCillerType) {
        this.painCillerType = painCillerType;
    }
}

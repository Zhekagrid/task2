package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dosage {
    static Logger logger = LogManager.getLogger();
    private double dose;
    private IntakeFrequencyType intakeFequency;


    public Dosage(double dose, IntakeFrequencyType intakeFequency) {
        this.dose = dose;
        this.intakeFequency = intakeFequency;
        logger.log(Level.INFO, "Dosage was created");
    }

    public Dosage() {
        logger.log(Level.INFO, "Dosage was created");

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dosage{");
        sb.append("dose=").append(dose);
        sb.append(", intakeFequency=").append(intakeFequency);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dosage)) return false;

        Dosage dosage = (Dosage) o;

        if (Double.compare(dosage.getDose(), getDose()) != 0) return false;
        return getIntakeFequency() == dosage.getIntakeFequency();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getDose());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getIntakeFequency() != null ? getIntakeFequency().hashCode() : 0);
        return result;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public IntakeFrequencyType getIntakeFequency() {
        return intakeFequency;
    }

    public void setIntakeFequency(IntakeFrequencyType intakeFequency) {
        this.intakeFequency = intakeFequency;
    }
}

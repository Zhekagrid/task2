package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PharmManufacturer {
    static Logger logger = LogManager.getLogger();
    private String name;
    private Certificate certificate;
    private Dosage dosage;
    private Box box;

    public PharmManufacturer(String name, Certificate certificate, Dosage dosage, Box box) {
        this.name = name;
        this.certificate = certificate;
        this.dosage = dosage;
        this.box = box;
        logger.log(Level.INFO, "PharmManufacture was created");
    }

    public PharmManufacturer() {
        logger.log(Level.INFO, "PharmManufacture was created");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PharmManufacturer{");
        sb.append("name=").append(name);
        sb.append(", certificate=").append(certificate);
        sb.append(", dosage=").append(dosage);
        sb.append(", box=").append(box);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PharmManufacturer)) return false;

        PharmManufacturer that = (PharmManufacturer) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCertificate() != null ? !getCertificate().equals(that.getCertificate()) : that.getCertificate() != null)
            return false;
        if (getDosage() != null ? !getDosage().equals(that.getDosage()) : that.getDosage() != null) return false;
        return getBox() != null ? getBox().equals(that.getBox()) : that.getBox() == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (getCertificate() != null ? getCertificate().hashCode() : 0);
        result = 31 * result + (getDosage() != null ? getDosage().hashCode() : 0);
        result = 31 * result + (getBox() != null ? getBox().hashCode() : 0);
        return result;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}

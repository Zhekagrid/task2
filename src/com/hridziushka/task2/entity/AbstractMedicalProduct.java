package com.hridziushka.task2.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMedicalProduct {
    private String id;
    private String name;
    private List<String> analogs;
    private Map<VersionType, List<PharmManufacturer>> versionListMap;

    public AbstractMedicalProduct(String name, List<String> analogs, Map<VersionType, List<PharmManufacturer>> versionListMap) {
        this.name = name;
        this.analogs = analogs;
        this.versionListMap = versionListMap;

    }

    public AbstractMedicalProduct() {
        versionListMap = new HashMap<>();
        analogs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAnalogs() {
        return analogs;
    }

    public void setAnalogs(List<String> analogs) {
        this.analogs = analogs;
    }

    public Map<VersionType, List<PharmManufacturer>> getVersionListMap() {
        return new HashMap<>(versionListMap);
    }

    public void setVersionListMap(Map<VersionType, List<PharmManufacturer>> versionListMap) {
        this.versionListMap = versionListMap;
    }

    public List<PharmManufacturer> put(VersionType key, List<PharmManufacturer> value) {
        return versionListMap.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMedicalProduct)) return false;

        AbstractMedicalProduct that = (AbstractMedicalProduct) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getAnalogs() != null ? !getAnalogs().equals(that.getAnalogs()) : that.getAnalogs() != null) return false;
        return getVersionListMap() != null ? getVersionListMap().equals(that.getVersionListMap()) : that.getVersionListMap() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAnalogs() != null ? getAnalogs().hashCode() : 0);
        result = 31 * result + (getVersionListMap() != null ? getVersionListMap().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AbstractMedicalProduct{");
        sb.append("name='").append(name).append('\'');
        sb.append("id='").append(id).append('\'');
        sb.append(", analogs=").append(analogs);
        sb.append(", versionListMap=").append(versionListMap);
        sb.append('}');
        return sb.toString();
    }
}

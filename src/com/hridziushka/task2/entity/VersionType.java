package com.hridziushka.task2.entity;

public enum VersionType {
    TABLETS,
    CAPSULES,
    GEL,
    POWDER;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";


    public static VersionType valueOfXmlTag(String str) {
        return VersionType.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {

        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }
}

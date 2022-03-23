package com.hridziushka.task2.entity;

public enum VitaminType {
    A,
    B,
    C,
    D,
    E,
    B12,
    B2,
    D2;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";

    public static VitaminType valueOfXmlTag(String str) {
        return VitaminType.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {

        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }


}

package com.hridziushka.task2.entity;

public enum BoxType {
    GLASS,
    CARTON,
    DEFAULT_BOX;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";

    public static BoxType valueOfXmlTag(String str) {
        return BoxType.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {
        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }
}

package com.hridziushka.task2.entity;

public enum PainKillerType {
    HEAD_PAIN_KILLER,
    STOMACH_PAIN_KILLER,
    MUSCLES_PAIN_KILLER;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";

    public static PainKillerType valueOfXmlTag(String str) {
        return PainKillerType.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {

        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }

}

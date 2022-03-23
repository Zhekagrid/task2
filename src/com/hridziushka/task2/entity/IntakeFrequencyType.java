package com.hridziushka.task2.entity;

public enum IntakeFrequencyType {
    ONCE_PER_DAY,
    TWICE_AFTER_MEALS;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";

    public static IntakeFrequencyType valueOfXmlTag(String str) {
        return IntakeFrequencyType.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {

        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }
}

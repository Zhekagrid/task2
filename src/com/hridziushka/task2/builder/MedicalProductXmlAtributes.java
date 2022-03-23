package com.hridziushka.task2.builder;

public enum MedicalProductXmlAtributes {
    ID,
    NAME,
    VERSION,
    BOX_TYPE,
    DOSE,
    INTAKE_TYPE,
    NUMBER,
    RECORDING_AGENCY;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";


    public static MedicalProductXmlAtributes valueOfXmlTag(String str) {
        return MedicalProductXmlAtributes.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {

        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }


}
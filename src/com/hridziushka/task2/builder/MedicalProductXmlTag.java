package com.hridziushka.task2.builder;

public enum MedicalProductXmlTag {
    MEDICINS,
    ANTIBIOTIC,
    VITAMIN,
    PAIN_KILLER,
    VERSIONS,
    VERSION,
    PHARM_FACTORIES,
    PHARM_FACTORY,
    BOX,
    CERTIFICATE,
    DOSAGE,
    ANALOGS,
    ANALOG,
    COUNT,
    PRICE,
    FROM,
    TO,
    NEED_RECIPE,
    VITAMINE_TYPE,
    PAIN_KILLER_TYPE;
    private static final String UNDERLINING = "_";
    private static final String HUPHEN = "-";

    public static MedicalProductXmlTag valueOfXmlTag(String str) {
        return MedicalProductXmlTag.valueOf(str.toUpperCase().replace(HUPHEN, UNDERLINING));
    }

    @Override
    public String toString() {
        return this.name().toLowerCase().replace(UNDERLINING, HUPHEN);
    }


}

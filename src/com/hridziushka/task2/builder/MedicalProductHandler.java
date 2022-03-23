package com.hridziushka.task2.builder;

import com.hridziushka.task2.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MedicalProductHandler extends DefaultHandler {
    static Logger logger = LogManager.getLogger();

    private static final String VITAMIN_ELEMENT = MedicalProductXmlTag.VITAMIN.toString();
    private static final String PAIN_CILLER_ELEMENT = MedicalProductXmlTag.PAIN_KILLER.toString();
    private static final String ANTIBIOTIC_ELEMENT = MedicalProductXmlTag.ANTIBIOTIC.toString();
    private static final String BOX_ELEMENT = MedicalProductXmlTag.BOX.toString();
    private static final String DOSAGE_ELEMENT = MedicalProductXmlTag.DOSAGE.toString();
    private static final String CERTIFICATE_ELEMENT = MedicalProductXmlTag.CERTIFICATE.toString();
    private static final String PHARM_MANUFACURER_ELEMENT = MedicalProductXmlTag.PHARM_FACTORY.toString();
    private static final String VERSION_ELEMENT = MedicalProductXmlTag.VERSION.toString();
    private static final String PHARM_MANUFACTORIES_ELEMENT = MedicalProductXmlTag.PHARM_FACTORIES.toString();
    private static final String ANALOGS_ELEMENT = MedicalProductXmlTag.ANALOGS.toString();
    private static final String ANALOG_ELEMENT = MedicalProductXmlTag.ANALOG.toString();

    private static final String DATE_SEPARATOR = "-";
    private static final String VITAMIN_SEPARATOR = " ";


    private final EnumSet<MedicalProductXmlTag> textTag;
    private final List<AbstractMedicalProduct> medicalProductList;
    private AbstractMedicalProduct currentMedicalProduct;
    private Box currentBox;
    private Dosage currentDosage;
    private Certificate currentCertificate;
    private PharmManufacturer currentPharmManufacturer;
    private MedicalProductXmlTag currentTag;
    private VersionType currentVersion;
    private List<PharmManufacturer> pharmManufacturerList;
    private List<String> analogList;

    public MedicalProductHandler() {
        medicalProductList = new ArrayList<>();
        textTag = EnumSet.range(MedicalProductXmlTag.COUNT, MedicalProductXmlTag.PAIN_KILLER_TYPE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (VITAMIN_ELEMENT.equals(qName)) {
            currentMedicalProduct = new Vitamin();
        }
        if (ANTIBIOTIC_ELEMENT.equals(qName)) {
            currentMedicalProduct = new Antibiotic();
        }
        if (PAIN_CILLER_ELEMENT.equals(qName)) {
            currentMedicalProduct = new PainKiller();
        }
        if (PAIN_CILLER_ELEMENT.equals(qName) || ANTIBIOTIC_ELEMENT.equals(qName) || VITAMIN_ELEMENT.equals(qName)) {
            String name = attributes.getValue(MedicalProductXmlAtributes.NAME.toString());
            String id = attributes.getValue(MedicalProductXmlAtributes.ID.toString());
            currentMedicalProduct.setId(id);
            currentMedicalProduct.setName(name);
        }
        if (ANALOGS_ELEMENT.equals(qName)) {
            analogList = new ArrayList<>();
        }
        if (ANALOG_ELEMENT.equals(qName)) {
            analogList.add(attributes.getValue(MedicalProductXmlAtributes.NAME.toString()));
        }
        if (BOX_ELEMENT.equals(qName)) {
            currentBox = new Box();
            if (attributes.getLength() > 0) {
                String boxType = attributes.getValue(MedicalProductXmlAtributes.BOX_TYPE.toString());

                currentBox.setBoxType(BoxType.valueOfXmlTag(boxType));


            } else {
                currentBox.setBoxType(BoxType.DEFAULT_BOX);
            }
        }

        if (CERTIFICATE_ELEMENT.equals(qName)) {
            currentCertificate = new Certificate();
            String number = attributes.getValue(MedicalProductXmlAtributes.NUMBER.toString());
            String recordingAgency = attributes.getValue(MedicalProductXmlAtributes.RECORDING_AGENCY.toString());
            currentCertificate.setRecordingAgency(recordingAgency);
            currentCertificate.setNumber(number);
        }
        if (DOSAGE_ELEMENT.equals(qName)) {
            currentDosage = new Dosage();

            String dose = attributes.getValue(MedicalProductXmlAtributes.DOSE.toString());
            String intakeFrequence = attributes.getValue(MedicalProductXmlAtributes.INTAKE_TYPE.toString());
            currentDosage.setDose(Double.parseDouble(dose));
            currentDosage.setIntakeFequency(IntakeFrequencyType.valueOfXmlTag(intakeFrequence));
        }
        if (PHARM_MANUFACURER_ELEMENT.equals(qName)) {
            currentPharmManufacturer = new PharmManufacturer();
            String pharmName = attributes.getValue(MedicalProductXmlAtributes.NAME.toString());
            currentPharmManufacturer.setName(pharmName);
        }
        if (VERSION_ELEMENT.equals(qName)) {
            String version = attributes.getValue(MedicalProductXmlAtributes.VERSION.toString());
            currentVersion = VersionType.valueOfXmlTag(version);
        }
        if (PHARM_MANUFACTORIES_ELEMENT.equals(qName)) {
            pharmManufacturerList = new ArrayList<>();
        }

        MedicalProductXmlTag tmpTag = MedicalProductXmlTag.valueOfXmlTag(qName);
        if (textTag.contains(tmpTag)) {
            currentTag = tmpTag;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (VITAMIN_ELEMENT.equals(qName) || ANTIBIOTIC_ELEMENT.equals(qName) || PAIN_CILLER_ELEMENT.equals(qName)) {
            medicalProductList.add(currentMedicalProduct);
        }
        if (ANALOGS_ELEMENT.equals(qName)) {
            currentMedicalProduct.setAnalogs(analogList);
        }
        if (PHARM_MANUFACURER_ELEMENT.equals(qName)) {
            currentPharmManufacturer.setBox(currentBox);
            currentPharmManufacturer.setCertificate(currentCertificate);
            currentPharmManufacturer.setDosage(currentDosage);
            pharmManufacturerList.add(currentPharmManufacturer);
        }

        if (PHARM_MANUFACTORIES_ELEMENT.equals(qName)) {
            currentMedicalProduct.put(currentVersion, pharmManufacturerList);
        }
    }

    public List<AbstractMedicalProduct> getMedicalProductList() {
        return medicalProductList;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentTag != null) {
            switch (currentTag) {
                case COUNT -> currentBox.setCount(Integer.parseInt(data));
                case PRICE -> currentBox.setPrice(Double.parseDouble(data));
                case FROM -> {
                    LocalDate date = parseDate(data);
                    currentCertificate.setFrom(date);
                }
                case TO -> {

                    LocalDate date = parseDate(data);
                    currentCertificate.setTo(date);
                }
                case NEED_RECIPE -> {
                    Antibiotic antibiotic = (Antibiotic) currentMedicalProduct;
                    antibiotic.setNeedRecipe(Boolean.parseBoolean(data));

                }
                case VITAMINE_TYPE -> {
                    Vitamin vitamin = (Vitamin) currentMedicalProduct;
                    List<VitaminType> vitaminList = Arrays.stream(data.split(VITAMIN_SEPARATOR)).filter(x -> !x.isBlank()).map(VitaminType::valueOfXmlTag).toList();

                    vitamin.setVitaminTypeList(vitaminList);

                }
                case PAIN_KILLER_TYPE -> {
                    PainKiller painKiller = (PainKiller) currentMedicalProduct;
                    PainKillerType painKillerType = PainKillerType.valueOfXmlTag(data);
                    painKiller.setPainKillerType(painKillerType);

                }
                default -> {
                    logger.log(Level.ERROR, "enum not constant present" + currentTag.getDeclaringClass() + currentTag.name());
                    throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
                }
            }
            currentTag = null;
        }

    }

    private LocalDate parseDate(String data) {
        String[] dateArray = data.split(DATE_SEPARATOR);
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDate.of(year, month, day);
    }

}

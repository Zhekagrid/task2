package com.hridziushka.task2.builder;

import com.hridziushka.task2.entity.*;
import com.hridziushka.task2.validator.XmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaxMedicalProductBuilder extends AbstractMedicalProductBuilder {
    static Logger logger = LogManager.getLogger();
    private static final String VITAMIN_SEPARATOR = " ";
    private static final String DATE_SEPARATOR = "-";
    private static final String XSD_SXEMA_PATH = "resources//sxema.xsd";

    private final XMLInputFactory inputFactory;


    public StaxMedicalProductBuilder() {
        inputFactory = XMLInputFactory.newInstance();

    }

    @Override
    public void buildMedproductList(String xmlFilePath) {
        XmlValidator validator = XmlValidator.getInstance();
        if (validator.validateXmlFile(xmlFilePath, XSD_SXEMA_PATH)) {

            logger.log(Level.INFO, "Start parsing xml with stax");
            XMLStreamReader reader;
            String name;
            try (FileInputStream inputStream = new FileInputStream(xmlFilePath)) {
                reader = inputFactory.createXMLStreamReader(inputStream);

                while (reader.hasNext()) {
                    int type = reader.next();
                    if (type == XMLStreamConstants.START_ELEMENT) {
                        name = reader.getLocalName();
                        if (name.equals(MedicalProductXmlTag.PAIN_KILLER.toString()) || name.equals(MedicalProductXmlTag.ANTIBIOTIC.toString()) || name.equals(MedicalProductXmlTag.VITAMIN.toString())) {
                            AbstractMedicalProduct medicalProduct = buildMedProduct(reader);
                            medProducts.add(medicalProduct);
                        }
                    }
                }
                logger.log(Level.INFO, "End parsing xml with stax");
            } catch (XMLStreamException | FileNotFoundException e) {
                logger.log(Level.ERROR, "XMLStreamException or FileNotFoundException while read xml", e);
            } catch (IOException e) {
                logger.log(Level.ERROR, "IOexception while read xml", e);

            }
        }
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private AbstractMedicalProduct buildMedProduct(XMLStreamReader reader) throws XMLStreamException {

        AbstractMedicalProduct medicalProduct;
        String medProductType = reader.getLocalName();
        MedicalProductXmlTag currentTag = MedicalProductXmlTag.valueOfXmlTag(medProductType);
        logger.log(Level.INFO, "Start building concrete med product with stax " + medProductType);
        switch (currentTag) {
            case ANTIBIOTIC -> medicalProduct = new Antibiotic();
            case VITAMIN -> medicalProduct = new Vitamin();
            case PAIN_KILLER -> medicalProduct = new PainKiller();
            default -> {
                logger.log(Level.ERROR, "enum not constant present" + currentTag.getDeclaringClass() + currentTag.name());

                throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
            }

        }
        String productName = reader.getAttributeValue(null, MedicalProductXmlAtributes.NAME.toString());
        String id = reader.getAttributeValue(null, MedicalProductXmlAtributes.ID.toString());

        medicalProduct.setId(id);
        medicalProduct.setName(productName);
        String name;
        VersionType curVersion = null;
        List<PharmManufacturer> manufacturerList = null;
        List<String> analogList = null;

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (MedicalProductXmlTag.valueOfXmlTag(name)) {
                        case ANALOGS -> analogList = new ArrayList<>();
                        case VERSION -> {
                            String currentVersionStr = reader.getAttributeValue(null, MedicalProductXmlAtributes.VERSION.toString());
                            curVersion = VersionType.valueOfXmlTag(currentVersionStr);
                            manufacturerList = buildPharmManufacturList(reader);

                        }
                        case NEED_RECIPE -> {
                            String data = getXMLText(reader);
                            Antibiotic antibiotic = (Antibiotic) medicalProduct;
                            antibiotic.setNeedRecipe(Boolean.parseBoolean(data));

                        }
                        case VITAMINE_TYPE -> {
                            String data = getXMLText(reader);
                            Vitamin vitamin = (Vitamin) medicalProduct;
                            List<VitaminType> vitaminList = Arrays.stream(data.split(VITAMIN_SEPARATOR)).filter(x -> !x.isBlank()).map(VitaminType::valueOfXmlTag).toList();
                            vitamin.setVitaminTypeList(vitaminList);

                        }
                        case PAIN_KILLER_TYPE -> {
                            String data = getXMLText(reader);
                            PainKiller painKiller = (PainKiller) medicalProduct;
                            PainKillerType painKillerType = PainKillerType.valueOfXmlTag(data);
                            painKiller.setPainKillerType(painKillerType);

                        }
                        case ANALOG -> {
                            String analogName = reader.getAttributeValue(null, MedicalProductXmlAtributes.NAME.toString());
                            analogList.add(analogName);
                        }


                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();

                    if (MedicalProductXmlTag.VERSION.toString().equals(name)) {
                        medicalProduct.put(curVersion, manufacturerList);
                    }
                    if (name.equals(MedicalProductXmlTag.PAIN_KILLER.toString()) || name.equals(MedicalProductXmlTag.ANTIBIOTIC.toString()) || name.equals(MedicalProductXmlTag.VITAMIN.toString())) {
                        logger.log(Level.INFO, "End building concrete med product with stax " + medProductType);

                        return medicalProduct;
                    }
                    if (name.equals(MedicalProductXmlTag.ANALOGS.toString())) {

                        medicalProduct.setAnalogs(analogList);
                    }

                }
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag <medicins>");

        throw new XMLStreamException("Unknown element in tag <medicins>");
    }

    private List<PharmManufacturer> buildPharmManufacturList(XMLStreamReader reader) throws XMLStreamException {
        logger.log(Level.INFO, "Start building pharmManufactur list  with stax ");

        List<PharmManufacturer> manufacturerList = new ArrayList<>();

        String name;
        Box box = new Box();
        Certificate certificate = new Certificate();
        Dosage dosage = new Dosage();
        PharmManufacturer pharmManufacturer = null;

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (MedicalProductXmlTag.valueOfXmlTag(name)) {
                        case BOX -> box = buildBox(reader);
                        case CERTIFICATE -> certificate = buildCertificate(reader);
                        case DOSAGE -> dosage = buildDosage(reader);
                        case PHARM_FACTORY -> {
                            pharmManufacturer = new PharmManufacturer();
                            String pharmName = reader.getAttributeValue(null, MedicalProductXmlAtributes.NAME.toString());
                            pharmManufacturer.setName(pharmName);
                        }

                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {

                    name = reader.getLocalName();

                    if (MedicalProductXmlTag.PHARM_FACTORIES.toString().equals(name)) {
                        logger.log(Level.INFO, "End building pharmManufactur list  with stax ");
                        return manufacturerList;
                    }
                    if (MedicalProductXmlTag.PHARM_FACTORY.toString().equals(name)) {
                        pharmManufacturer.setDosage(dosage);
                        pharmManufacturer.setCertificate(certificate);
                        pharmManufacturer.setBox(box);
                        manufacturerList.add(pharmManufacturer);

                    }
                }
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag <version>");

        throw new XMLStreamException("Unknown element in tag <version>");
    }

    private Box buildBox(XMLStreamReader reader) throws XMLStreamException {
        logger.log(Level.INFO, "Start building box  with stax ");
        Box box = new Box();
        String boxType = reader.getAttributeValue(null, MedicalProductXmlAtributes.BOX_TYPE.toString());
        if (boxType == null) {
            box.setBoxType(BoxType.DEFAULT_BOX);
        } else {
            box.setBoxType(BoxType.valueOfXmlTag(boxType));
        }

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (MedicalProductXmlTag.valueOfXmlTag(name)) {

                        case COUNT -> {
                            String data = getXMLText(reader);
                            box.setCount(Integer.parseInt(data));
                        }
                        case PRICE -> {
                            String data = getXMLText(reader);
                            box.setPrice(Double.parseDouble(data));
                        }


                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (MedicalProductXmlTag.BOX.toString().equals(name)) {
                        logger.log(Level.INFO, "End building box  with stax ");
                        return box;
                    }
                }
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag <box>");

        throw new XMLStreamException("Unknown element in tag <box>");
    }

    private Certificate buildCertificate(XMLStreamReader reader) throws XMLStreamException {
        logger.log(Level.INFO, "Start building certificate  with stax ");
        Certificate certificate = new Certificate();
        String number = reader.getAttributeValue(null, MedicalProductXmlAtributes.NUMBER.toString());
        String recordingAgency = reader.getAttributeValue(null, MedicalProductXmlAtributes.RECORDING_AGENCY.toString());
        certificate.setNumber(number);
        certificate.setRecordingAgency(recordingAgency);

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (MedicalProductXmlTag.valueOfXmlTag(name)) {

                        case FROM -> {
                            String data = getXMLText(reader);
                            LocalDate date = parseDate(data);
                            certificate.setFrom(date);
                        }
                        case TO -> {
                            String data = getXMLText(reader);
                            LocalDate date = parseDate(data);
                            certificate.setTo(date);
                        }


                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (MedicalProductXmlTag.CERTIFICATE.toString().equals(name)) {
                        logger.log(Level.INFO, "End building certificate  with stax ");
                        return certificate;
                    }
                }
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag <certificate>");
        throw new XMLStreamException("Unknown element in tag <certificate>");
    }

    private LocalDate parseDate(String data) {
        String[] dateArray = data.split(DATE_SEPARATOR);
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDate.of(year, month, day);
    }

    private Dosage buildDosage(XMLStreamReader reader) {
        logger.log(Level.INFO, "Start building dosage  with stax ");
        Dosage dosage = new Dosage();
        String dose = reader.getAttributeValue(null, MedicalProductXmlAtributes.DOSE.toString());
        String intakeFrequency = reader.getAttributeValue(null, MedicalProductXmlAtributes.INTAKE_TYPE.toString());

        dosage.setIntakeFequency(IntakeFrequencyType.valueOfXmlTag(intakeFrequency));
        dosage.setDose(Double.parseDouble(dose));
        logger.log(Level.INFO, "End building dosage  with stax ");
        return dosage;

    }
}

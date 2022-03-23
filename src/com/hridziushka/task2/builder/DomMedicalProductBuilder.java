package com.hridziushka.task2.builder;

import com.hridziushka.task2.entity.*;
import com.hridziushka.task2.exception.CustomException;
import com.hridziushka.task2.validator.XmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DomMedicalProductBuilder extends AbstractMedicalProductBuilder {
    static Logger logger = LogManager.getLogger();
    private static final String VITAMIN_SEPARATOR = " ";
    private static final String DATE_SEPARATOR = "-";
    private static final String XSD_SXEMA_PATH = "resources//sxema.xsd";

    private final DocumentBuilder docBuilder;

    public DomMedicalProductBuilder() throws CustomException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Exception while creating DocumentBuilder", e);
            throw new CustomException("Exception while creating DocumentBuilder", e);
        }

    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }

    @Override
    public void buildMedproductList(String xmlFilePath) {
        XmlValidator validator = XmlValidator.getInstance();
        if (validator.validateXmlFile(xmlFilePath, XSD_SXEMA_PATH)) {

            Document doc;
            try {
                logger.log(Level.INFO, "Start parsing Xml with Dom");
                doc = docBuilder.parse(xmlFilePath);
                Element root = doc.getDocumentElement();
                NodeList medProductList = root.getElementsByTagName(MedicalProductXmlTag.ANTIBIOTIC.toString());
                for (int i = 0; i < medProductList.getLength(); i++) {
                    Element medProductElemnt = (Element) medProductList.item(i);
                    AbstractMedicalProduct medicalProduct = buildMedicalProduct(medProductElemnt);
                    medProducts.add(medicalProduct);

                }
                medProductList = root.getElementsByTagName(MedicalProductXmlTag.PAIN_KILLER.toString());
                for (int i = 0; i < medProductList.getLength(); i++) {
                    Element medProductElemnt = (Element) medProductList.item(i);
                    AbstractMedicalProduct medicalProduct = buildMedicalProduct(medProductElemnt);
                    medProducts.add(medicalProduct);

                }
                medProductList = root.getElementsByTagName(MedicalProductXmlTag.VITAMIN.toString());
                for (int i = 0; i < medProductList.getLength(); i++) {
                    Element medProductElemnt = (Element) medProductList.item(i);
                    AbstractMedicalProduct medicalProduct = buildMedicalProduct(medProductElemnt);
                    medProducts.add(medicalProduct);

                }
                logger.log(Level.INFO, "End parsing Xml with Dom");
            } catch (IOException | SAXException e) {
                logger.log(Level.ERROR, "Exceptions while parsing xml", e);
            }
        }
    }

    private AbstractMedicalProduct buildMedicalProduct(Element medProductElement) {

        AbstractMedicalProduct medicalProduct;
        String medProductType = medProductElement.getTagName();
        MedicalProductXmlTag currentTag = MedicalProductXmlTag.valueOfXmlTag(medProductType);
        logger.log(Level.INFO, "Start builing concrete medical product with Dom: " + medProductType);
        switch (currentTag) {
            case ANTIBIOTIC -> {
                medicalProduct = new Antibiotic();
                String needRecipe = getElementTextContent(medProductElement, MedicalProductXmlTag.NEED_RECIPE.toString());
                Antibiotic tmp = (Antibiotic) medicalProduct;
                tmp.setNeedRecipe(Boolean.parseBoolean(needRecipe));
            }
            case VITAMIN -> {
                medicalProduct = new Vitamin();
                String data = getElementTextContent(medProductElement, MedicalProductXmlTag.VITAMINE_TYPE.toString());
                Vitamin vitamin = (Vitamin) medicalProduct;
                List<VitaminType> vitaminList = Arrays.stream(data.split(VITAMIN_SEPARATOR)).filter(x -> !x.isBlank()).map(VitaminType::valueOfXmlTag).toList();
                vitamin.setVitaminTypeList(vitaminList);
            }
            case PAIN_KILLER -> {
                medicalProduct = new PainKiller();
                PainKiller painKiller = (PainKiller) medicalProduct;
                String painKillerType = getElementTextContent(medProductElement, MedicalProductXmlTag.PAIN_KILLER_TYPE.toString());
                painKiller.setPainKillerType(PainKillerType.valueOfXmlTag(painKillerType));
            }
            default -> {
                logger.log(Level.ERROR, "enum not constant present" + currentTag.getDeclaringClass() + currentTag.name());
                throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
            }

        }

        String name = medProductElement.getAttribute(MedicalProductXmlAtributes.NAME.toString());
        String id = medProductElement.getAttribute(MedicalProductXmlAtributes.ID.toString());
        medicalProduct.setName(name);
        medicalProduct.setId(id);
        List<String> analogList = new ArrayList<>();
        NodeList analogs = medProductElement.getElementsByTagName(MedicalProductXmlTag.ANALOG.toString());
        for (int i = 0; i < analogs.getLength(); i++) {
            Element analog = (Element) analogs.item(i);
            String analogName = analog.getAttribute(MedicalProductXmlAtributes.NAME.toString());
            analogList.add(analogName);
        }
        medicalProduct.setAnalogs(analogList);

        NodeList versions = medProductElement.getElementsByTagName(MedicalProductXmlTag.VERSION.toString());

        for (int i = 0; i < versions.getLength(); i++) {
            Element version = (Element) versions.item(i);
            String currentVersionStr = version.getAttribute(MedicalProductXmlAtributes.VERSION.toString());
            VersionType curVersion = VersionType.valueOfXmlTag(currentVersionStr);
            NodeList pharmFactories = version.getElementsByTagName(MedicalProductXmlTag.PHARM_FACTORY.toString());
            List<PharmManufacturer> manufacturerList = new ArrayList<>();
            for (int j = 0; j < pharmFactories.getLength(); j++) {
                Element pharmFactory = (Element) pharmFactories.item(j);
                Element boxElement = (Element) pharmFactory.getElementsByTagName(MedicalProductXmlTag.BOX.toString()).item(0);
                Element dosageElement = (Element) pharmFactory.getElementsByTagName(MedicalProductXmlTag.DOSAGE.toString()).item(0);
                Element certificateElement = (Element) pharmFactory.getElementsByTagName(MedicalProductXmlTag.CERTIFICATE.toString()).item(0);
                String pharmName = pharmFactory.getAttribute(MedicalProductXmlAtributes.NAME.toString());
                Box box = buildBox(boxElement);
                Certificate certificate = buildCertificate(certificateElement);
                Dosage dosage = buildDosage(dosageElement);

                manufacturerList.add(new PharmManufacturer(pharmName, certificate, dosage, box));

            }
            medicalProduct.put(curVersion, manufacturerList);

        }
        logger.log(Level.INFO, "End builing concrete medical product with Dom: " + medProductType);

        return medicalProduct;
    }

    private Box buildBox(Element boxElement) {
        logger.log(Level.INFO, "Start builing box with Dom");

        Box box = new Box();
        String price = getElementTextContent(boxElement, MedicalProductXmlTag.PRICE.toString());
        String count = getElementTextContent(boxElement, MedicalProductXmlTag.COUNT.toString());
        String boxType = boxElement.getAttribute(MedicalProductXmlAtributes.BOX_TYPE.toString());
        if (boxType.isEmpty()) {
            box.setBoxType(BoxType.DEFAULT_BOX);
        } else {
            box.setBoxType(BoxType.valueOfXmlTag(boxType));
        }
        box.setPrice(Double.parseDouble(price));
        box.setCount(Integer.parseInt(count));
        logger.log(Level.INFO, "End builing box with Dom");
        return box;
    }

    private Certificate buildCertificate(Element certificateElement) {
        logger.log(Level.INFO, "Start builing certificate with Dom");
        Certificate certificate = new Certificate();
        String number = certificateElement.getAttribute(MedicalProductXmlAtributes.NUMBER.toString());
        String recordingAgency = certificateElement.getAttribute(MedicalProductXmlAtributes.RECORDING_AGENCY.toString());
        String dataFrom = getElementTextContent(certificateElement, MedicalProductXmlTag.FROM.toString());

        LocalDate dateFrom = parseDate(dataFrom);

        String dataTo = getElementTextContent(certificateElement, MedicalProductXmlTag.TO.toString());

        LocalDate dateTo = parseDate(dataTo);

        certificate.setTo(dateTo);
        certificate.setFrom(dateFrom);
        certificate.setNumber(number);
        certificate.setRecordingAgency(recordingAgency);
        logger.log(Level.INFO, "End builing certificate with Dom");
        return certificate;
    }

    private LocalDate parseDate(String data) {
        String[] dateArray = data.split(DATE_SEPARATOR);
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDate.of(year, month, day);
    }

    private Dosage buildDosage(Element dosageElement) {
        logger.log(Level.INFO, "Start builing dosage with Dom");
        Dosage dosage = new Dosage();
        String dose = dosageElement.getAttribute(MedicalProductXmlAtributes.DOSE.toString());
        String intakeFrequency = dosageElement.getAttribute(MedicalProductXmlAtributes.INTAKE_TYPE.toString());

        dosage.setIntakeFequency(IntakeFrequencyType.valueOfXmlTag(intakeFrequency));
        dosage.setDose(Double.parseDouble(dose));
        logger.log(Level.INFO, "End builing dosage with Dom");
        return dosage;

    }
}

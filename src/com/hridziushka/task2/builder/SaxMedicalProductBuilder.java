package com.hridziushka.task2.builder;

import com.hridziushka.task2.exception.CustomException;
import com.hridziushka.task2.validator.XmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxMedicalProductBuilder extends AbstractMedicalProductBuilder {
    static Logger logger = LogManager.getLogger();
    private static final String XSD_SXEMA_PATH = "resources//sxema.xsd";

    private final MedicalProductHandler handler = new MedicalProductHandler();
    private final XMLReader reader;

    public SaxMedicalProductBuilder() throws CustomException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();

        } catch (ParserConfigurationException | SAXException e) {
            logger.log(Level.ERROR, "ParserConfigurationException or SAXException while creating reader", e);
            throw new CustomException("ParserConfigurationException or SAXException while creating reader", e);
        }

        reader.setContentHandler(handler);
        reader.setErrorHandler(new MedicalProductErrorHandler());
    }

    @Override
    public void buildMedproductList(String xmlFilePath) {
        XmlValidator validator = XmlValidator.getInstance();
        if (validator.validateXmlFile(xmlFilePath, XSD_SXEMA_PATH)) {
            try {
                logger.log(Level.INFO, "Start parsing xml with sax");
                reader.parse(xmlFilePath);
                medProducts = handler.getMedicalProductList();
                logger.log(Level.INFO, "End parsing xml with sax");
            } catch (IOException | SAXException e) {
                logger.log(Level.ERROR, "IOException or SAXException while xml was parsing", e);
            }


        }
    }
}

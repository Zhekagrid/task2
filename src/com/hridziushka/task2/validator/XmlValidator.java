package com.hridziushka.task2.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidator {
    static Logger logger = LogManager.getLogger();
    private static XmlValidator instance;

    private XmlValidator() {

    }

    public static XmlValidator getInstance() {
        if (instance == null) {
            instance = new XmlValidator();
        }
        return instance;
    }

    public boolean validateXmlFile(String xmlPath, String xsdPath) {

        try {
            File xmlFile = new File(xmlPath);
            File xsdFile = new File(xsdPath);
            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));

        } catch (IOException e) {
            logger.log(Level.ERROR, "IOexception, while validate xml file", e);

        } catch (SAXException e) {
            logger.log(Level.ERROR, "Incorrect xml", e);
            return false;
        }
        logger.log(Level.INFO, "Xml is correct");
        return true;

    }
}

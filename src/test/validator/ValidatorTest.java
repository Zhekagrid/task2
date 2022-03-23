package test.validator;

import com.hridziushka.task2.validator.XmlValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ValidatorTest {
    private static final String XSD_SXEMA_PATH = "resources//testResources//sxema.xsd";
    private static final String XML_CORRECT_PATH = "resources//testResources//correctXml.xml";
    private static final String XML_INCORRECT_PATH_1 = "resources//testResources//incorrectXml_1.xml";
    private static final String XML_INCORRECT_PATH_2 = "resources//testResources//incorrectXml_2.xml";
    private XmlValidator validator;

    @BeforeClass
    public void setUp() {
        validator = XmlValidator.getInstance();
    }

    @AfterClass
    public void tierDown() {
        validator = null;
    }

    @Test
    public void correctXmlValidateTest() {
        boolean actual = validator.validateXmlFile(XML_CORRECT_PATH, XSD_SXEMA_PATH);

        assertTrue(actual);
    }

    @DataProvider(name = "fileProvider")
    public Object[][] creatData() {
        return new Object[][]{
                {false, XML_INCORRECT_PATH_1},
                {false, XML_INCORRECT_PATH_2},

        };
    }

    @Test(dataProvider = "fileProvider")
    public void incorrectXmlValidateTest(boolean expected, String filePath) {
        boolean actual = validator.validateXmlFile(filePath, XSD_SXEMA_PATH);

        assertEquals(actual, expected);
    }
}

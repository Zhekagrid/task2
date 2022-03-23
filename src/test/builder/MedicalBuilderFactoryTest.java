package test.builder;

import com.hridziushka.task2.builder.*;
import com.hridziushka.task2.exception.CustomException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MedicalBuilderFactoryTest {
    @DataProvider(name = "buildrTypeProvider")
    public Object[][] creatData() {
        return new Object[][]{
                {SaxMedicalProductBuilder.class, "SAX"},
                {DomMedicalProductBuilder.class, "DOM"},
                {StaxMedicalProductBuilder.class, "STAX"}
        };
    }

    @Test(dataProvider = "buildrTypeProvider")
    public void testCreateBuilder(Class<? extends AbstractMedicalProductBuilder> expectedClass, String builderType) throws CustomException {
        MedicalBuilderFactory factory = MedicalBuilderFactory.getInstance();
        AbstractMedicalProductBuilder actualBuilder = factory.createBuilder(BuilderType.valueOf(builderType));
        Class<? extends AbstractMedicalProductBuilder> actualClass = actualBuilder.getClass();
        assertEquals(actualClass, expectedClass);
    }
}

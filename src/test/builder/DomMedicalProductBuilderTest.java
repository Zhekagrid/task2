package test.builder;

import com.hridziushka.task2.builder.AbstractMedicalProductBuilder;
import com.hridziushka.task2.builder.DomMedicalProductBuilder;
import com.hridziushka.task2.entity.*;
import com.hridziushka.task2.exception.CustomException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DomMedicalProductBuilderTest {
    private static final String XML_CORRECT_PATH = "resources//testResources//correctXml.xml";
    List<AbstractMedicalProduct> expected;

    @BeforeClass
    public void setUp() {
        expected = new ArrayList<>();

        AbstractMedicalProduct antibiotic = new Antibiotic();
        AbstractMedicalProduct vitamin = new Vitamin();
        AbstractMedicalProduct painCiller = new PainKiller();
        antibiotic.setName("Augmentin");
        antibiotic.setId("AB1");
        List<String> analogs = new ArrayList<>();
        analogs.add("Supracs");
        analogs.add("Ciprolet");
        antibiotic.setAnalogs(new ArrayList<>(analogs));
        List<PharmManufacturer> pharmManufacturerList = new ArrayList<>();

        Box box = new Box(BoxType.CARTON, 25, 25.5);
        Dosage dosage = new Dosage(125, IntakeFrequencyType.ONCE_PER_DAY);
        Certificate certificate = new Certificate("AB1234", LocalDate.of(2020, 5, 15), LocalDate.of(2022, 5, 15), "Bayer");
        pharmManufacturerList.add(new PharmManufacturer("Sandoz", certificate, dosage, box));
        antibiotic.put(VersionType.POWDER, pharmManufacturerList.stream().toList());
        pharmManufacturerList.clear();


        pharmManufacturerList = new ArrayList<>();
        pharmManufacturerList.add(new PharmManufacturer("SmithKline Beecham", new Certificate("AB4321", LocalDate.of(2019, 8, 25), LocalDate.of(2025, 8, 19), "FDA"), new Dosage(375, IntakeFrequencyType.TWICE_AFTER_MEALS), new Box(BoxType.CARTON, 20, 35)));
        antibiotic.put(VersionType.CAPSULES, pharmManufacturerList.stream().toList());
        ((Antibiotic) antibiotic).setNeedRecipe(true);


        painCiller.setName("Nise");
        painCiller.setId("PC1");
        analogs = new ArrayList<>();
        analogs.add("Spasmalgon");
        analogs.add("Paraskofen");
        painCiller.setAnalogs(new ArrayList<>(analogs));
        pharmManufacturerList = new ArrayList<>();


        pharmManufacturerList.add(new PharmManufacturer("Dr. Reddy´s Laboratories Ltd", new Certificate("AD4235", LocalDate.of(2010, 1, 8), LocalDate.of(2025, 9, 30), "Bayer"), new Dosage(25, IntakeFrequencyType.TWICE_AFTER_MEALS), new Box(BoxType.CARTON, 15, 100)));

        pharmManufacturerList.add(new PharmManufacturer("Ipca labarotory", new Certificate("NS2051", LocalDate.of(2022, 1, 20), LocalDate.of(2030, 5, 30), "GmbH"), new Dosage(100, IntakeFrequencyType.TWICE_AFTER_MEALS), new Box(BoxType.DEFAULT_BOX, 10, 15)));
        painCiller.put(VersionType.TABLETS, pharmManufacturerList.stream().toList()
        );
        ((PainKiller) painCiller).setPainKillerType(PainKillerType.HEAD_PAIN_KILLER);

        vitamin.setName("Complex for children");
        vitamin.setId("VT1");
        analogs = new ArrayList<>();
        vitamin.setAnalogs(new ArrayList<>(analogs));

        pharmManufacturerList = new ArrayList<>();


        pharmManufacturerList.add(new PharmManufacturer("Doppelherz", new Certificate("AS1234", LocalDate.of(2015, 3, 25), LocalDate.of(2027, 8, 25), "bayer"), new Dosage(250, IntakeFrequencyType.ONCE_PER_DAY), new Box(BoxType.CARTON, 7, 17.5)));
        vitamin.put(VersionType.TABLETS, pharmManufacturerList.stream().toList()
        );
        pharmManufacturerList.clear();
        pharmManufacturerList.add(new PharmManufacturer("Doppelherz", new Certificate("AS1743", LocalDate.of(2015, 3, 25), LocalDate.of(2027, 8, 25), "bayer"), new Dosage(150, IntakeFrequencyType.TWICE_AFTER_MEALS), new Box(BoxType.GLASS, 5, 25)));
        vitamin.put(VersionType.POWDER, pharmManufacturerList.stream().toList()
        );
        List<VitaminType> vitaminList = new ArrayList<>();
        vitaminList.add(VitaminType.A);
        vitaminList.add(VitaminType.B);
        vitaminList.add(VitaminType.C);
        vitaminList.add(VitaminType.D2);

        ((Vitamin) vitamin).setVitaminTypeList(vitaminList);

        expected.add(antibiotic);
        expected.add(painCiller);
        expected.add(vitamin);
    }

    @AfterClass
    public void tierDown() {
        expected = null;
    }

    @Test
    public void test() throws CustomException {

        AbstractMedicalProductBuilder builderDom = new DomMedicalProductBuilder();
        builderDom.buildMedproductList(XML_CORRECT_PATH);
        List<AbstractMedicalProduct> actual = builderDom.getMedProducts();

        assertEquals(actual, expected);
    }
}

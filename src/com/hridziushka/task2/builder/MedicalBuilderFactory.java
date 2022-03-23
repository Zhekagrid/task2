package com.hridziushka.task2.builder;

import com.hridziushka.task2.exception.CustomException;

public class MedicalBuilderFactory {
    private static MedicalBuilderFactory instance;

    private MedicalBuilderFactory() {
    }

    public static MedicalBuilderFactory getInstance() {
        if (instance == null) {
            instance = new MedicalBuilderFactory();
        }
        return instance;
    }

    public AbstractMedicalProductBuilder createBuilder(BuilderType builderType) throws CustomException {
        AbstractMedicalProductBuilder medicalProductBuilder;
        switch (builderType) {
            case DOM -> medicalProductBuilder = new DomMedicalProductBuilder();
            case SAX -> medicalProductBuilder = new SaxMedicalProductBuilder();
            case STAX -> medicalProductBuilder = new StaxMedicalProductBuilder();
            default -> throw new EnumConstantNotPresentException(builderType.getDeclaringClass(), builderType.name());
        }
        return medicalProductBuilder;
    }
}

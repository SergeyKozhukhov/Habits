package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Unit тесты на {@link ConfidentialityConverter}
 **/
public class ConfidentialityConverterTest {

    /**
     * Конвертер Confidentiality модели между data и domain слоями
     */
    private ConfidentialityConverter confidentialityConverter;

    @Before
    public void setUp() {
        confidentialityConverter = new ConfidentialityConverter();
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр domain слоя
     */
    @Test
    public void convertTo() {
        // arrange
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        ConfidentialityData confidentialityDataInput = new ConfidentialityData(email, password);
        Confidentiality confidentialityExpectedOutput = new Confidentiality(email, password);

        // act
        Confidentiality confidentialityOutput = confidentialityConverter.convertTo(confidentialityDataInput);

        // assert
        assertThat(confidentialityOutput, is(confidentialityExpectedOutput));
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр data слоя
     */
    @Test
    public void convertFrom() {
        // arrange
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        Confidentiality confidentialityInput = new Confidentiality(email, password);
        ConfidentialityData confidentialityDataExpectedIOutput = new ConfidentialityData(email, password);

        // act
        ConfidentialityData confidentialityDataOutput = confidentialityConverter.convertFrom(confidentialityInput);

        // assert
        assertThat(confidentialityDataOutput, is(confidentialityDataExpectedIOutput)); // сравнение объектов на равенство
    }
}
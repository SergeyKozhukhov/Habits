package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habits.model.data.RegistrationData;
import ru.sergeykozhukhov.habits.model.domain.Registration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Unit тесты на {@link RegistrationConverter}
 **/
public class RegistrationConverterTest {

    /**
     * Конвертер Registration модели между data и domain слоями
     */
    private RegistrationConverter registrationConverter;

    @Before
    public void setUp() {
        registrationConverter = new RegistrationConverter();
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр domain слоя
     */
    @Test
    public void convertTo() {
        // arrange
        String firstname = "Иван";
        String lastname = "Иванов";
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        RegistrationData registrationDataInput = new RegistrationData(firstname, lastname, email, password);
        Registration registrationExtectedOutput = new Registration(firstname, lastname, email, password);

        // act
        Registration registrationOutput = registrationConverter.convertTo(registrationDataInput);

        // assert
        assertThat(registrationOutput, is(registrationExtectedOutput));
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр data слоя
     */
    @Test
    public void convertFrom() {
        // arrange
        String firstname = "Иван";
        String lastname = "Иванов";
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        Registration registrationInput = new Registration(firstname, lastname, email, password);
        RegistrationData registrationDataExpectedOutput = new RegistrationData(firstname, lastname, email, password);

        // act
        RegistrationData registrationDataOutput = registrationConverter.convertFrom(registrationInput);

        // assert
        assertThat(registrationDataOutput, is(registrationDataExpectedOutput));
    }
}
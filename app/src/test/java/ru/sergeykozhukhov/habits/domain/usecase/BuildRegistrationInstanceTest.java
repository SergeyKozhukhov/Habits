package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BuildRegistrationInstanceTest {

    private BuildRegistrationInstance buildRegistrationInstance;

    @Before
    public void setUp() throws Exception {
        buildRegistrationInstance = new BuildRegistrationInstance();
    }

    @Test
    public void buildSuccess() throws BuildException {
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        Registration registrationExpected = new Registration(firstname, lastname, email, password);

        Registration registrationOutput = buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);

        assertThat(registrationOutput, is(registrationExpected));
    }

    @Test
    public void buildErrorNull(){
        try {
            buildRegistrationInstance.build(null, null, null, null, null);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.null_data_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinFirstname(){
        String firstname = "fn";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.firstname_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxFirstname(){
        String firstname = "qwertyuiopasdfghjklz";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.firstname_max_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinLastname(){
        String firstname = "firstname";
        String lastname = "ln";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.lastname_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxLastname(){
        String firstname = "firstname";
        String lastname = "qwertyuiopasdfghjklz";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.lastname_max_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinEmail(){
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email";
        String password = "password";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.email_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinPassword(){
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "word";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.password_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxPassword(){
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "qwertyuiopasdfghjklzxcvbnmqwerty";
        String passwordConfirmation = "password";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.password_max_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorPasswordConfirmation(){
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "word";

        try {
            buildRegistrationInstance.build(firstname,lastname,email,password,passwordConfirmation);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.password_confirmation_build_instance_exception, is(e.getMessageRes()));
        }
    }
}
package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class BuildConfidentialityInstanceTest {

    private BuildConfidentialityInstance buildConfidentialityInstance;

    @Before
    public void setUp() {
        buildConfidentialityInstance = new BuildConfidentialityInstance();
    }

    @Test
    public void buildSuccess() throws BuildException {

        String email = "email@gmail.com";
        String password = "password";
        Confidentiality confidentialityExpected = new Confidentiality(email, password);

        Confidentiality confidentialityOutput = buildConfidentialityInstance.build(email, password);
        assertThat(confidentialityOutput, is(confidentialityExpected));
    }

    @Test
    public void buildErrorNull(){

        try {
            buildConfidentialityInstance.build(null, null);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.null_data_build_instance_exception, is (e.getMessageRes()));
        }
    }


    @Test
    public void buildErrorMinEmail(){

        String email = "email";
        String password = "password";

        try {
            buildConfidentialityInstance.build(email, password);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.email_min_build_instance_exception, is (e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinPassword(){

        String email = "email@gmail.com";
        String password = "word";

        try {
            buildConfidentialityInstance.build(email, password);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.password_min_build_instance_exception, is (e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxPassword(){

        String email = "email@gmail.com";
        String password = "qwertyuiopasdfghjklzxcvbnmqwerty";

        try {
            buildConfidentialityInstance.build(email, password);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.password_max_build_instance_exception, is (e.getMessageRes()));
        }
    }
}
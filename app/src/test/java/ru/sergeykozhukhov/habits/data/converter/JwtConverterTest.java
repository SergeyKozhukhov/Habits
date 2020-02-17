package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JwtConverterTest {

    private JwtConverter jwtConverter;

    @Before
    public void setUp() throws Exception {
        jwtConverter = new JwtConverter();
    }

    @Test
    public void convertTo() {

        // arrange

        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ.-xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM";

        JwtData jwtDataInput = new JwtData(jwt);
        Jwt jwtExpectedOutput = new Jwt(jwt);

        // act

        Jwt jwtOutput = jwtConverter.convertTo(jwtDataInput);

        // assert

        assertThat(jwtOutput, is(jwtExpectedOutput));
    }

    @Test
    public void convertFrom() {

        // arrange

        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ.-xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM";

        Jwt jwtInput = new Jwt(jwt);
        JwtData jwtDataExpectedOutput = new JwtData(jwt);

        // act

        JwtData jwtDataOutput = jwtConverter.convertFrom(jwtInput);

        // assert

        assertThat(jwtDataOutput, is(jwtDataExpectedOutput));

    }
}
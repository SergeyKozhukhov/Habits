package ru.sergeykozhukhov.habits.base.domain.IInreractor.builder;

import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public interface IBuildConfidentialityInstance {

    Confidentiality build(String email, String password) throws BuildException;

}

package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jeremymarshall on 31/12/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)

public @interface Parameter {
    //public String name();
    public String description();
}

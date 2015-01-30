package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface ClosureMethod {
    public String description();

    public Class delegate();
}



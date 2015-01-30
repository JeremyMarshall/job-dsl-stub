package org.jenkinsci.plugins.jobdsl.stub.model;

/**
 * Created by jeremymarshall on 29/01/2015.
 */
public class ClosureMethod extends Method {

    private java.lang.Class delegate;

    public ClosureMethod(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.ClosureMethod m, java.lang.reflect.Method rm, Class c) {

        super( rm, c);
        delegate = m.delegate();
        description = m.description();
    }
}

package org.jenkinsci.plugins.jobdsl.stub

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter

@Extension
public class TestClosure extends Closure {

    String testValue

    @Override
    public String getName() {
        return "testClosure";
    }

    @Override
    public String getDescription() {
        return "Test closure";
    }

    @Override
    public final boolean hasMethods() {
        return true;
    };

    @Method(description = "Method")
    public void testMethod(@Parameter(description = "test closure method") String value) {
        this.testValue = value
    }
}

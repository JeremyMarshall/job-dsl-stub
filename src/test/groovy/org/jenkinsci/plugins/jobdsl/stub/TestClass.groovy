package org.jenkinsci.plugins.jobdsl.stub

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.TestClosure
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import java.lang.String

@Extension
public class TestClass extends TestCategory {

    @Override
    public String getName() {
        return "testClass";
    }

    @Override
    public String getDescription() {
        return "test class";
    }

    @Override
    public int ordinal() {
        50
    }

    @Override
    public final boolean hasMethods() {
        return true
    }

    @Method(description = "test method with a closure", closureClass = TestClosure)
    public Object testMethodWithClosure(@Parameter(description = "test closure") Object closure) {
        TestClosure i = runClosure(closure, TestClosure)
        i
    }

    @Method(description = "test method with string param")
    public Object testMethodString (@Parameter(description = "name") String name) {
        new String(name)
    }

    @Method(description = "test method with string list")
    public Object testMethodListString (@Parameter(description = "names") List<String> name) {

        String s = ''
        name.each { s += "${it} "}
        new String(s)
    }

    @Method(description = "test method with string vaarg")
    public Object testMethodVaargsString (@Parameter(description = "names") String... name) {

        testMethodListString(Arrays.asList(name))
    }
}


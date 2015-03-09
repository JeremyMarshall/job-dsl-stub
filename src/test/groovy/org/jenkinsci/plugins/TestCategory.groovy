package org.jenkinsci.plugins.jobdsl.stub

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category

@Extension
public class TestCategory extends Category {

    @Override
    public String getName() { return "test";}

    @Override
    public String getDescription() { return "Test";}

    @Override
    public final String getCategory(){
        return TestCategory.class.getName();
    }

    @Override
    public int ordinal() {
        50
    }
}
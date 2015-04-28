package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension


@Extension
public class BuildWrapper extends Category {
    @Override
    public String getName() { return "buildWrappers";}

    @Override
    public String getDescription() { return "Classes for Build wrappers";}

    @Override
    public final String getCategory(){
        return 'buildWrappers';
    }

    @Override
    public int ordinal() {
        100
    }

}
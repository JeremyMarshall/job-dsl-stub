package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Step extends Category {
    @Override
    public String getName() { return "Step";}

    @Override
    public String getDescription() { return "Classes for Build steps";}

    @Override
    public final String getCategory(){
        return Step.class.getName();
    }
}


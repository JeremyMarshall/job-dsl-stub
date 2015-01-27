package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class SCM extends Category {

    @Override
    public String getName() { return "SCM";}

    @Override
    public String getDescription() { return "Classes for SCM stage";}

    @Override
    public final String getCategory(){
        return SCM.class.getName();
    }
}


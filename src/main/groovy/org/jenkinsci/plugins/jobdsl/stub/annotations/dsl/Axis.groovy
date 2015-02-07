package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Axis extends Category {

    @Override
    public String getName() { return "axes";}

    @Override
    public String getDescription() { return "Classes for Matrix Axes";}

    @Override
    public final String getCategory(){
        return Axis.class.getName();
    }

}


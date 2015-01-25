package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Closure extends Category {
    @Override
    public String getName() { return "Closure";}

    @Override
    public String getDescription() { return "Classes for Closures";}

    @Override
    public final String getCategory(){
        return Closure.class.getName();
    }

}


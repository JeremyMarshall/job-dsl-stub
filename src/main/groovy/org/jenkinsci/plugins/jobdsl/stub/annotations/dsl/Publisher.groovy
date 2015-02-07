package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Publisher extends Category {

    @Override
    public String getName() { return "publishers";}

    @Override
    public String getDescription() { return "Classes for Publish steps";}

    @Override
    public final String getCategory(){
        return Publisher.class.getName();
    }

}


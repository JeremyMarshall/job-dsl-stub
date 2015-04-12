package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Project extends Category {
    @Override
    public String getName() { return "project";}

    @Override
    public String getDescription() { return "Top level project";}

    @Override
    public final String getCategory(){
        return 'project';
    }

    @Override
    public int ordinal() {
        1
    }

}


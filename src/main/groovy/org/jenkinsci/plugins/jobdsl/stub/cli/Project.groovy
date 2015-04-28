package org.jenkinsci.plugins.jobdsl.stub.cli

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Project extends Category {
    @Override
    public String getName() { return "project"}

    @Override
    public String getDescription() { return "project"}

    @Override
    public final String getCategory(){
        return 'project';
    }

    @Override
    public int ordinal() {
        1
    }
}


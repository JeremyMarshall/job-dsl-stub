package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.Extension;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Step extends Base {
    public final CategoryEnum getCategory(){ return CategoryEnum.STEP;}

    @Override
    public String getName() { return "Step";}

    @Override
    public String getDescription() { return "Classes for Build steps";}

}


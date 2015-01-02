package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import jenkins.model.Jenkins;
import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Axis extends Base {

    public final CategoryEnum getCategory(){ return CategoryEnum.AXIS;}

    @Override
    public String getName() { return "Axis";}

    @Override
    public String getDescription() { return "Classes for Matrix Axes";}

}


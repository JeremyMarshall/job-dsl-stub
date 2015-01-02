package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class SCM extends Base {

    public final CategoryEnum getCategory(){ return CategoryEnum.SCM;}

    @Override
    public String getName() { return "SCM";}

    @Override
    public String getDescription() { return "Classes for SCM stage";}

}


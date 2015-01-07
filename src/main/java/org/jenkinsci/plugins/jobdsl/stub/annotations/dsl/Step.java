package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Step extends Category {
    public final CategoryEnum getCategory(){ return CategoryEnum.STEP;}

    @Override
    public String getName() { return "Step";}

    @Override
    public String getDescription() { return "Classes for Build steps";}

}


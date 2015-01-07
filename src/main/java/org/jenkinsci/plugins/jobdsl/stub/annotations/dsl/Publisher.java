package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Publisher extends Category {

    public final CategoryEnum getCategory(){ return CategoryEnum.PUBLISHER;}

    @Override
    public String getName() { return "Publisher";}

    @Override
    public String getDescription() { return "Classes for Publish steps";}

}


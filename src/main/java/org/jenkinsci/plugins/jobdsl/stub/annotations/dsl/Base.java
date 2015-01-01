package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 30/12/2014.
 */
public abstract class Base implements ExtensionPoint {

    protected CategoryEnum category;

    public CategoryEnum getCategory() {
        return category;
    }

    //Don't use this one its just the base in case I want to add some generic functionality
    //use the descendant like Step, Axis

    public static ExtensionList<Base> all() {
        return Jenkins.getInstance().getExtensionList(Base.class);
    }
}


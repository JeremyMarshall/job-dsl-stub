package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 30/12/2014.
 */
public abstract class Category implements ExtensionPoint {

    public abstract CategoryEnum getCategory();
    public abstract String getName();
    public abstract String getDescription();

    //Use this if you have a new category (like step, etc)
    //use the descendant like Step, Axis for any concrete plugins

    public static ExtensionList<Category> all() {
        return Jenkins.getInstance().getExtensionList(Category.class);
    }
}


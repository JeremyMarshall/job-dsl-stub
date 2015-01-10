package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;

/**
 * Created by jeremymarshall on 30/12/2014.
 */
public abstract class Category implements ExtensionPoint {

    public abstract String getName();
    public abstract String getDescription();

    public abstract String getCategory();

    //override this in classes which present methods
    public boolean hasMethods(){
        return false;
    };

    //Use this if you have a new category (like step, etc)
    //use the descendant like Step, Axis for any concrete plugins

    public static ExtensionList<Category> all() {
        return Jenkins.getInstance().getExtensionList(Category.class);
    }
}


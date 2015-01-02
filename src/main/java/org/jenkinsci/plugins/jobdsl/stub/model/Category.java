package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Category extends AbstractDescribableImpl<Category> implements Comparable, Describable<Category> {

    private CategoryEnum type;
    private List<Class> classes;
    private String name;
    private String description;

    public Category(Base b) {
        type = b.getCategory();
        name = b.getName();
        description = b.getDescription();

        classes = new ArrayList<Class>();
    }

    public Base add(Base a) {

        java.lang.Class c = a.getClass();
        classes.add(new Class(a, this));
        return a;
    }

    public void update(Base b) {
        name = b.getName();
        description = b.getDescription();
    }

    public String getType(){
        return type.toString();
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public List<Class> getClasses() {
            return classes;
    }


    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Category> {
        @Override public String getDisplayName() {
            return "DSL Category";
        }
    }
}

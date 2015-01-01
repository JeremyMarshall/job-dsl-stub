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
    private List<Base> classes;

    public Category(CategoryEnum c) {
        type = c;
        classes = new ArrayList<Base>();
    }

    public Base add(Base a) {
        classes.add(a);
        return a;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Category> {
        @Override public String getDisplayName() {
            return "DSL Extension";
        }
    }
}

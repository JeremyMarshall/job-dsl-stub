package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

import java.util.Map;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Class extends AbstractDescribableImpl<Class> implements Comparable, Describable<Class> {

    private Category extension;
    private Map<Method, java.lang.reflect.Method> methods;
    private String name;
    private String description;
    private String sourcePlugin;

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Class> {
        @Override public String getDisplayName() {
            return "DSL Class";
        }
    }
}

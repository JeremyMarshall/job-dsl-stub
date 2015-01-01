package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

import java.util.List;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Method extends AbstractDescribableImpl<Method> implements Comparable, Describable<Method> {

    private Class aClass;
    private String name;
    private String description;
    private List<Parameter> parameters;

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Method> {
        @Override public String getDisplayName() {
            return "DSL Method";
        }
    }
}

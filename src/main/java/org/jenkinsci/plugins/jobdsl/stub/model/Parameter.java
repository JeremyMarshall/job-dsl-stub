package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Parameter extends AbstractDescribableImpl<Parameter> implements Comparable, Describable<Parameter> {

    private Method method;
    private String type;
    private String name;
    private java.lang.Class parameter;
    private String description;

    public Parameter(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter p, java.lang.Class rp, Method m) {
        name = p.name();
        description = p.description();
        type = rp.getName();
        parameter = rp;

        method = m;
    }

    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Parameter> {
        @Override public String getDisplayName() {
            return "DSL Parameter";
        }
    }
}

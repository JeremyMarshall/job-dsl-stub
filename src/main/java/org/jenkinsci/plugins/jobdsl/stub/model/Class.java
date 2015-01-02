package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Base;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Class extends AbstractDescribableImpl<Class> implements Comparable, Describable<Class> {

    private Category category;
    private java.lang.Class clazz;
    private List<Method> methods;
    private String name;
    private String description;
    private String sourcePlugin;

    public Class(Base b, Category c) {
        clazz = b.getClass();
        category = c;
        name = b.getName();
        description = b.getDescription();

        methods = new ArrayList<Method>();

        for (java.lang.reflect.Method m : clazz.getMethods()) {
            Annotation annotation = m.getAnnotation(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method.class);

            if(annotation instanceof org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method){
                org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method myAnnotation = (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method) annotation;

                methods.add(new Method(myAnnotation, m, this));
            }
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public List<Method> getMethods() { return methods; }

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

package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Method extends AbstractDescribableImpl<Method> implements Comparable, Describable<Method> {

    private Class aClass;
    private String name;
    private String description;
    private java.lang.reflect.Method method;
    private List<Parameter> parameters;

    public Method(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method m, java.lang.reflect.Method rm, Class c) {
        aClass = c;
        name = m.name();
        description = m.description();
        method = rm;

        parameters = new ArrayList<Parameter>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        java.lang.Class[] parameterTypes = method.getParameterTypes();

        int i=0;
        for(Annotation[] annotations : parameterAnnotations){
            java.lang.Class parameterType = parameterTypes[i++];

            for(Annotation annotation : annotations){
                if(annotation instanceof org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter){
                    org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter myAnnotation = (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter) annotation;

                    parameters.add(new Parameter(myAnnotation, parameterType, this));
                }
            }
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

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

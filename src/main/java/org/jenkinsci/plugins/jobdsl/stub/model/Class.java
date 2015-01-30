package org.jenkinsci.plugins.jobdsl.stub.model;


import hudson.Extension;
import hudson.model.Descriptor;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Class implements Comparable{

    private org.jenkinsci.plugins.jobdsl.stub.model.Category category;
    private java.lang.Class clazz;
    private List<Method> methods;
    private List<ClosureMethod> closureMethods;
    private String name;
    private String description;
    private String sourcePlugin;
    private Object instance;

    public Class(Category b, org.jenkinsci.plugins.jobdsl.stub.model.Category c) {
        clazz = b.getClass();
        category = c;
        name = b.getName();
        description = b.getDescription();

        methods = new ArrayList<Method>();
        closureMethods = new ArrayList<ClosureMethod>();

        for (java.lang.reflect.Method m : clazz.getMethods()) {
            Annotation annotation = m.getAnnotation(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method.class);

            if(annotation instanceof org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method){
                org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method myAnnotation = (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method) annotation;

                methods.add(new Method(myAnnotation, m, this));
            }
        }
        for (java.lang.reflect.Method m : clazz.getMethods()) {
            Annotation annotation = m.getAnnotation(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.ClosureMethod.class);

            if(annotation instanceof org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.ClosureMethod){
                org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.ClosureMethod myAnnotation = (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.ClosureMethod) annotation;

                closureMethods.add(new ClosureMethod(myAnnotation, m, this));
            }
        }
    }

    public Object getInstance(boolean createNew) throws InstantiationException, IllegalAccessException {
        if(instance == null || createNew) {
            instance = clazz.newInstance();
        }
        return instance;
    }

    public String getName(){
        return name;
    }

    public java.lang.Class getClazz() {
        return clazz;
    }

    public String getDescription(){
        return description;
    }

    public List<Method> getMethods() { return methods; }

    public List<ClosureMethod> getClosureMethods() { return closureMethods; }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

}

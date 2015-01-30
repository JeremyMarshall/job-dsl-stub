package org.jenkinsci.plugins.jobdsl.stub.model;


import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import hudson.Extension;
import hudson.model.Descriptor;

import java.util.*;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Category implements Comparable{

    private List<Class> classes;
    private Map<String, List<Method>> methods;
    private Map<String, List<ClosureMethod>> closureMethods;
    private String name;
    private String description;

    public Category(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category b) {
        name = b.getName();
        description = b.getDescription();

        classes = new ArrayList<Class>();
        methods = new HashMap<String, List<Method>>();
        closureMethods = new HashMap<String, List<ClosureMethod>>();
    }

    public org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category add(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category a) {

        Class c = new Class(a, this);
        classes.add(new Class(a, this));

        for( Method m: c.getMethods()) {
            if(!methods.containsKey(m.getName())){
                methods.put(m.getName(), new ArrayList<Method>());
            }
            methods.get(m.getName()).add(m);
        }

        for( ClosureMethod m: c.getClosureMethods()) {
            if(!closureMethods.containsKey(m.getName())){
                closureMethods.put(m.getName(), new ArrayList<ClosureMethod>());
            }
            closureMethods.get(m.getName()).add(m);
        }
        return a;
    }

    public void update(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category b) {
        name = b.getName();
        description = b.getDescription();
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

    public List<ClosureMethod> getClosureMethods( String name) {
        //if (methods.containsKey(name))
        if (closureMethods.containsKey(name)) {
            return closureMethods.get(name);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Method> getMethods( String name) {
        //if (methods.containsKey(name))
        if (methods.containsKey(name)) {
            return methods.get(name);
        } else {
            return Collections.emptyList();
        }
    }

    public Method getMethod( String name, List<Object> params) {
        return getMethod(name, params.toArray(new Object[params.size()]));
    }

    public Method getMethod( String name, Object... params) {
        java.lang.Class subclass;
        java.lang.Class superclass;

        List<java.lang.Class> paramTypes = new ArrayList<java.lang.Class>();
        for(Object o: params) {

            subclass = o.getClass();
            superclass = subclass.getSuperclass();
            while (superclass != null) {
                String className = superclass.getName();
                System.out.println(className);
                subclass = superclass;
                superclass = subclass.getSuperclass();
            }

            if (o instanceof groovy.lang.Closure[]){
                paramTypes.add(groovy.lang.Closure.class);
            } else {
                paramTypes.add(o.getClass());
            }
        }

        for (Method m: getMethods(name)){
            if (m.matchesParameters(paramTypes)) {
                return m;
            }
        }

        for (Method m: getClosureMethods(name)){
            if (m.matchesParameters(paramTypes)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

}

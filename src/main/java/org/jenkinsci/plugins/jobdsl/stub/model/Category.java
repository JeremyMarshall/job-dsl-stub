package org.jenkinsci.plugins.jobdsl.stub.model;


import groovy.lang.Closure;
import hudson.Extension;
import hudson.model.Descriptor;

import java.util.*;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Category implements Comparable{

    private List<Class> classes;
    private Map<String, List<Method>> methods;
    private String name;
    private String description;

    public Category(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category b) {
        name = b.getName();
        description = b.getDescription();

        classes = new ArrayList<Class>();
        methods = new HashMap<String, List<Method>>();
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

        List<java.lang.Class> paramTypes = new ArrayList<java.lang.Class>();
        for(Object o: params) {

            if (o instanceof Closure){
                paramTypes.add(Closure.class);
            } else {
                paramTypes.add(o.getClass());
            }
        }

        for (Method m: getMethods(name)){
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

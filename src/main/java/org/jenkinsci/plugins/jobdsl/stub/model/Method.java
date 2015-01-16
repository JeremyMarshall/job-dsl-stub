package org.jenkinsci.plugins.jobdsl.stub.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Method implements Comparable {

    private Class aClass;
    private String name;
    private String description;
    private java.lang.reflect.Method method;
    private List<Parameter> parameters;

    public Method(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method m, java.lang.reflect.Method rm, Class c) {
        aClass = c;
        name = rm.getName();
        description = m.description();
        method = rm;

        parameters = new ArrayList<Parameter>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        java.lang.Class[] parameterTypes = method.getParameterTypes();
        Type[] gpType = method.getGenericParameterTypes();

        int i=0;
        for(Annotation[] annotations : parameterAnnotations){
            java.lang.Class parameterType = parameterTypes[i];
            Type gpt = gpType[i++];

            for(Annotation annotation : annotations){
                if(annotation instanceof org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter){
                    org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter myAnnotation = (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter) annotation;

                    parameters.add(new Parameter(myAnnotation, parameterType, gpt, this, i == parameterTypes.length));
                }
            }
        }
    }

    public Class getMyClass() {
        return aClass;
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

    public Object execute(List<Object> params) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object r = execute(params.toArray(new Object[params.size()]));

        return r;
    }

    public Object execute( Object... params) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object o = aClass.getInstance(false);

        Object rejiggedParams[] = new Object[parameters.size()];

        if (method.isVarArgs()) {
            //rejig varargs parms to put the vararg ones into an array (not a list)
            for( int i = 0; i < parameters.size() - 1; i++) {
                rejiggedParams[i] = params[i];
            }

            int j = parameters.size() - 1;
            int k = 0;

            //the vaarg is the size of the actual parameters - the number of required parameters + 1
            //the +1 is the last required param is the vaarg

            //what about if the vaarg is not sent at all?
            Object va = parameters.get(parameters.size() - 1).getInstance(params.length - parameters.size() + 1);

            for( int i = parameters.size() - 1; i < params.length; i++) {
                Array.set(va, k++, (String)params[i]);
            }
            rejiggedParams[parameters.size() - 1] = va;
        } else {
            rejiggedParams = params;
        }

        Object r =  method.invoke(o, rejiggedParams);
        //Object r =  method.invoke(o, params[0], params[1]);

        return r;
    }

    public boolean matchesParameters( java.lang.Class... paramTypes) {
        return matchesParameters(Arrays.asList(paramTypes));
    }

    public boolean matchesParameters( List<java.lang.Class> parameterTypes){

        int i = 0;

        for( Parameter p: parameters) {
            //this will eat all parameterTypes if its a vararg and they match - which is the last parameter
            if (! p.matchParameter(parameterTypes, i)) {
                //fail straight away if a parameter doesn't match
                return false;
            }
            i++;
        }
        return parameterTypes.size() == i || method.isVarArgs();
    }
    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

}

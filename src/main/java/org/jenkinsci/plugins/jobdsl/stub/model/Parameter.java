package org.jenkinsci.plugins.jobdsl.stub.model;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Parameter  implements Comparable {

    private Method method;
    private String type;
    private String genericType;
    private java.lang.Class genericClass;
    private boolean isArray;
    //private String name;
    private java.lang.Class parameter;
    private java.lang.reflect.Type parameterArgType;
    private String description;
    private boolean isVaArg;

    public Parameter(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter p, java.lang.Class rp, Type gpt, Method m, boolean isLast) {
        //name = p.getName();
        //description = p.description();
        type = rp.getName();
        //genericType = gpt.toString();
        parameter = rp;

        method = m;

        if(gpt instanceof ParameterizedType){
            ParameterizedType aType = (ParameterizedType) gpt;
            Type[] parameterArgTypes = aType.getActualTypeArguments();
            for(Type pat : parameterArgTypes){
                //should only be one for our purposes
                //so no Map<String, String> type things here
                parameterArgType = pat;
                genericClass = pat.getClass();
                genericType = parameterArgType.toString();
            }
            description = type + "<" + genericType + "> " + p.description();
        } else {
            genericClass = rp.getComponentType();

            if( rp.isArray()) {
                genericType = rp.getComponentType().getName();
                isArray = true;
                if(isLast) {
                    description = genericType + "... " + p.description();
                    isVaArg = true;
                } else {
                    description = genericType + "[] " + p.description();
                }
            } else {
                description = type + " " + p.description();
            }
        }
    }

    public String getType(){
        return type;
    }

    public String getGenericType(){
        return genericType;
    }

    //public String getName(){
    //    return name;
    //}

    public String getDescription(){
        return description;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    public Object getInstance(int size) {//throws InstantiationException, IllegalAccessException {
        return Array.newInstance(genericClass, size);
    }

    public boolean matchParameter(List<java.lang.Class> parameterTypes, int currentParam) {

        boolean ret = false;

        //we may not have been passed enough parameters..
        if(currentParam < parameterTypes.size()) {
            ret = parameter.isAssignableFrom(parameterTypes.get(currentParam));
        } else {
            if (isVaArg) {
                ret = true;
            }
        }

        //look through remaining params to see if they fit if the last param a vararg and not yet matched
        if (!ret && isVaArg) {
            for (int j = currentParam; j < parameterTypes.size(); j++) {
                ret = genericType == parameterTypes.get(j).getName();

                //stop looking if we don't match
                if (!ret) {
                    break;
                }
            }
        }
        return ret;
    }
}

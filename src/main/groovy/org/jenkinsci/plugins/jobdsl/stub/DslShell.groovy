package org.jenkinsci.plugins.jobdsl.stub

import hudson.util.XStream2
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Step
import org.jenkinsci.plugins.jobdsl.stub.model.Method
import org.jenkinsci.plugins.jobdsl.stub.model.Category

/**
 * Created by jeremymarshall on 21/03/2015.
 */
abstract class DslShell extends Script implements GroovyInterceptable {

    static Factory factory = new Factory()
    Category category
    List returns = new ArrayList()

    @Override
    Object invokeMethod(String name, Object args){
        if(!category) { //top level eg steps, publishers and so on
            category = factory.getCategory(name)

            if(category) {
                groovy.lang.Closure closure = args[0]
                closure.delegate = category
                returns << closure()
                category = null //clear this out for the next entry

                returns
            }else{
                super.invokeMethod(name, args)
            }
        } else if( category ) { //else a method within
            Method m = category.getMethod(name, *args)
            Object o

            if (m instanceof Method) {
                o = m.execute(*args)
                o
            } else {
                super.invokeMethod(name, args)
            }
        } else { //some other stuff
            super.invokeMethod(name, args)
        }
    }
}
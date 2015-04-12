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
    Object invokeMethod(String name, Object args) {

        Category save

        if (!category) { //top level eg steps, publishers and so on
            category = factory.getCategory(name)

            if (category) {
                groovy.lang.Closure closure = args[0]
                closure.delegate = category
                //returns << closure()
                closure()
                category = null //clear this out for the next entry
                null
                //returns
            } else {
                super.invokeMethod(name, args)
                null
            }
        } else if (category) { //else a method/closure within
            Method m = category.getMethod(name, *args)
            Object o

            save = category
            category = factory.getCategory(name) ?: category

            if (m instanceof Method) {
                returns << m.execute(*args)

                category = save //put it back

            //} else { //maybe a nested stub job{ step{ myStep{...
            //    if (category != save && category) {
            //        groovy.lang.Closure closure = args[0]
            //        closure.delegate = category
            //        returns << closure()
            //        category = save //put this back for the next entry

            //        null
            } else {
                super.invokeMethod(name, args)
                null
            }
            //}
        } else { //some other stuff
            super.invokeMethod(name, args)
            null
        }
        null
    }
}
package org.jenkinsci.plugins.jobdsl.stub.cli

import hudson.util.XStream2
import org.jenkinsci.plugins.jobdsl.stub.Factory
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Publisher
import org.jenkinsci.plugins.jobdsl.stub.model.Method
import org.jenkinsci.plugins.jobdsl.stub.model.Category

/**
 * Created by jeremymarshall on 21/03/2015.
 */
abstract class DslShell extends Script implements GroovyInterceptable {

    static Factory factory = new Factory()
    Category category
    CliClosure returns = new CliClosure('top')
    CliClosure curr

    @Override
    Object invokeMethod(String name, Object args) {

        Category save

        if (!category) { //top level eg steps, publishers and so on
            category = factory.getCategory(name)

            if (category) {
                groovy.lang.Closure closure = args[0]
                closure.delegate = category

                //curr = new CliClosure(name)
                curr = new CliClosure(category.description)
                returns.items << curr

                closure()
                //returns.items << closure()
                category = null //clear this out for the next entry
            } else {
                super.invokeMethod(name, args)
                //null
            }
        } else if (category) { //else a method/closure within
            Method m = category.getMethod(name, *args)

            save = category
            category = factory.getCategory(name) ?: category

            if (m instanceof Method) {

                CliClosure save2 = curr

                if(m.isProxyClass()){
                    curr = new CliClosure(name)
                    save2.items << curr
                    m.execute(*args)
                    curr = save2 //put it back
                    category = save //put it back
                } else {
                    curr.items << m.execute(*args)
                }
                //CliClosure ret = curr

                //return ret
            } else {
                super.invokeMethod(name, args)
            }
        } else { //some other stuff
            super.invokeMethod(name, args)
        }
    }
}
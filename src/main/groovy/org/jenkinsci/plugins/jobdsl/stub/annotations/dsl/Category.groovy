package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.ExtensionList
import hudson.ExtensionPoint
import hudson.util.XStream2
import jenkins.model.Jenkins
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass

/**
 * Created by jeremymarshall on 30/12/2014.
 */
abstract class Category implements ExtensionPoint {

    abstract String getName()
    abstract String getDescription()
    abstract String getCategory()
    abstract int ordinal()
    Object returns = []

    //override this in classes which present methods
    boolean hasMethods() {
        false
    }

    static ExtensionList<Category> all() {
        Jenkins.instance.getExtensionList(Category)
    }

    boolean xstreamAlias(XStream2 xstream) {
        //items are fine the way they come out - scm items are not
        false
    }

    protected final Object runClosure(Object closure, Class closureClass)
            throws DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException{

        if (closureClass == null) {
            throw new DslNoClosureClass()
        }

        Object closureDelegate = closureClass.newInstance()

        if (closureDelegate == null) {
            throw new DslClosureUnsupported()
        }

        closure.setDelegate(closureDelegate)
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST)
        closure()

        returns << closureDelegate
        closureDelegate
    }
}

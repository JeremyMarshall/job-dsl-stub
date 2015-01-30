package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported;
/**
 * Created by jeremymarshall on 30/12/2014.
 */
public abstract class Category implements ExtensionPoint {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getCategory();

    //override this in classes which present methods
    public boolean hasMethods(){
        return false;
    };

    //Use this if you have a new category (like step, etc)
    //use the descendant like Step, Axis for any concrete plugins

    public static ExtensionList<Category> all() {
        return Jenkins.getInstance().getExtensionList(Category.class);
    }

    //do not use as the closure cannot be cast to Closure
    //from job-dsl. Not sure why that is
    /*
    protected final Object runClosure(Object closure)
            throws DslClosureUnsupported, IllegalAccessException, InstantiationException{

        Object closureDelegate = getClosureDelegate();

        if (closureDelegate == null) {
            throw new DslClosureUnsupported();
        }

        groovy.lang.Closure c = (groovy.lang.Closure) closure;

        c.setDelegate(closureDelegate);
        c.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        c.call();
        return closureDelegate;
    }
    */
}


package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported;
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass;

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

    public static ExtensionList<Category> all() {
        return Jenkins.getInstance().getExtensionList(Category.class);
    }

    protected final Object runClosure(Object closure, java.lang.Class closureClass)
            throws DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException{

        if (closureClass == null) {
            throw new DslNoClosureClass();
        }

        Object closureDelegate = closureClass.newInstance();

        if (closureDelegate == null) {
            throw new DslClosureUnsupported();
        }

        closure.setDelegate(closureDelegate);
        closure.setResolveStrategy(groovy.lang.Closure.DELEGATE_FIRST);
        closure();
        return closureDelegate;
    }

}


package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension
import hudson.model.Describable
import hudson.scm.SCM
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass
import org.jenkinsci.plugins.jobdsl.stub.NoClosure
import org.jenkinsci.plugins.jobdsl.stub.NoProxy

/**
 * Created by jeremymarshall on 6/04/15.
 */
@Extension
class Job extends Project {//implements GroovyInterceptable{
    @Override
    public String getName(){
        return "freestyle job";
    }

    @Override
    public String getDescription(){
        return "Create a freestyle job";
    }

    @Override
    public int ordinal() {
        10
    }

    @Override
    public final boolean hasMethods(){
        return true;
    }

    //@Override
    //Object invokeMethod(String name, Object args){
    //    super.invokeMethod(name, args)
    //}

    @Method(description="Add build steps", closureClass=NoClosure, proxyClass=NoProxy)
    public Object steps(@Parameter(description="Closure for steps") Object closure){
        //throws DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException {

        //org.jenkinsci.plugins.jobdsl.stub.model.Category category = factory.getCategory('steps')

        //if(category) {
        //    closure.delegate = category
        //    closure()
        //}

       Object i = runClosure(closure, Step.class);
       // Describable<SCM> ret = new FSSCM(i.getPath(), i.getLocalPath(), i.getClearWorkspace(), i.copyHidden, i.getFilterEnabled(), i.getFilterType(), i.getWildcards());
       return i;
    }

}

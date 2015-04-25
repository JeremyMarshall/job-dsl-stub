package org.jenkinsci.plugins.jobdsl.stub.cli

import hudson.Extension
import hudson.util.XStream2
import org.jenkinsci.plugins.jobdsl.stub.NoClosure
import org.jenkinsci.plugins.jobdsl.stub.NoProxy
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Publisher
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Scm
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Builder
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.BuildWrapper

/**
 * Created by jeremymarshall on 6/04/15.
 */
@Extension
class MatrixProjectInst extends MatrixProject {//implements GroovyInterceptable{
    @Override
    public String getName(){
        return "matrix-project instance";
    }

    @Override
    public String getDescription(){
        return "Create a matrix-project";
    }

    @Override
    public int ordinal() {
        10
    }

    @Override
    public final boolean hasMethods(){
        return true;
    }

    @Method(description="Add build wrappers", closureClass=NoClosure, proxyClass=BuildWrapper)
    public Object buildWrappers(@Parameter(description="Closure for build wrappers") Object closure){

        CliClosure i = runProxy(closure, BuildWrapper, 'buildWrappers');
        return i;
    }

    @Method(description="Add an scm", closureClass=NoClosure, proxyClass=Scm)
    public Object scm(@Parameter(description="Closure for scm") Object closure){

        CliClosure i = runProxy(closure, Scm, 'scm');
        i.setIsSingle(true)
        return i;
    }

    @Method(description="Add axes", closureClass=NoClosure, proxyClass=Axis)
    public Object axes(@Parameter(description="Closure for scm") Object closure){

        CliClosure i = runProxy(closure, Axis, 'axes');
        return i;
    }

    @Method(description="Add build steps", closureClass=NoClosure, proxyClass=Builder)
    public Object builders(@Parameter(description="Closure for steps") Object closure){

        CliClosure i = runProxy(closure, Builder, 'builders');
        return i;
    }

    @Method(description="Add publisher steps", closureClass=NoClosure, proxyClass=Publisher)
    public Object publishers(@Parameter(description="Closure for publishers") Object closure){

        CliClosure i = runProxy(closure, Publisher, 'publishers');
        return i;
    }

    @Method(description="Set the job name", closureClass = NoClosure, proxyClass = NoProxy)
    public Object jobName(@Parameter(description="name") String theName){
        //CliClosure i = new CliClosure('name')
        //i.items << theName
        //return i
        stringAttribute('name', theName)
    }

    @Method(description="Set the job description", closureClass = NoClosure, proxyClass = NoProxy)
    public Object jobDescription(@Parameter(description="description") String theDescription){
        //CliClosure i = new CliClosure('desc')
        //i.items << theDescription
        //return i
        stringAttribute('description', theDescription)
    }

    @Override
    public boolean xstreamAlias(XStream2 xstream) {
        //items are fine the way they come out
        //xstream.alias("project", CliClosure.class);

        //xstream.addImplicitCollection(CliClosure.class, 'items')

        xstream.registerConverter(new CliClosureConverter(xstream.getMapper(), xstream.getReflectionProvider()));
        return true;
    }
}

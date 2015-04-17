package org.jenkinsci.plugins.jobdsl.stub.cli

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.NoClosure
import org.jenkinsci.plugins.jobdsl.stub.NoProxy
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Publisher
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Scm
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Step

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

    @Method(description="Add an scm", closureClass=NoClosure, proxyClass=Scm)
    public Object scm(@Parameter(description="Closure for scm") Object closure){

        CliClosure i = runProxy(closure, Scm, 'scm');
        return i;
    }

    @Method(description="Add axes", closureClass=NoClosure, proxyClass=Axis)
    public Object axes(@Parameter(description="Closure for scm") Object closure){

        CliClosure i = runProxy(closure, Axis, 'axes');
        return i;
    }

    @Method(description="Add build steps", closureClass=NoClosure, proxyClass=Step)
    public Object steps(@Parameter(description="Closure for steps") Object closure){

        CliClosure i = runProxy(closure, Step, 'steps');
        return i;
    }

    @Method(description="Add publisher steps", closureClass=NoClosure, proxyClass=Publisher)
    public Object publishers(@Parameter(description="Closure for steps") Object closure){

        CliClosure i = runProxy(closure, Publisher, 'publishers');
        return i;
    }

    @Method(description="Set the job name", closureClass = NoClosure, proxyClass = NoProxy)
    public Object jobName(@Parameter(description="name") String theName){
        CliClosure i = new CliClosure('name')
        i.items << theName
        return i
    }

    @Method(description="Set the job description", closureClass = NoClosure, proxyClass = NoProxy)
    public Object jobDescription(@Parameter(description="description") String theDescription){
        CliClosure i = new CliClosure('desc')
        i.items << theDescription
        return i
    }
}

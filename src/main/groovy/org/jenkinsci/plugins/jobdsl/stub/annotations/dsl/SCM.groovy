package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import com.thoughtworks.xstream.XStream;
import hudson.Extension;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Scm extends Category {

    @Override
    public String getName() { return "scm";}

    @Override
    public String getDescription() { return "Classes for SCM stage";}

    @Override
    public final String getCategory(){
        return Scm.class.getName();
    }

    @Override
    public int ordinal() {
        10
    }

}


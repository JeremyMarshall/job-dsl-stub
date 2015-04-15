package org.jenkinsci.plugins.jobdsl.stub.cli;

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Project extends Category {
    @Override
    public String getName() { return "project";}

    @Override
    public String getDescription() { return "Top level project";}

    @Override
    public final String getCategory(){
        return 'project';
    }

    @Override
    public int ordinal() {
        1
    }
    protected final Object runProxy(Object closure, java.lang.Class closureClass, String section)
            throws DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException {

        def ret = new CliClosure(section)

        //ret.items << this.runClosure(closure, closureClass)
        this.runClosure(closure, closureClass)

        ret
    }
}


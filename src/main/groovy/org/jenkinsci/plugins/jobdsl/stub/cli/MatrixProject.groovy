package org.jenkinsci.plugins.jobdsl.stub.cli;

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class MatrixProject extends Category {
    @Override
    public String getName() { return "matrixProject";}

    @Override
    public String getDescription() { return "matrix-project";}

    @Override
    public final String getCategory(){
        return 'matrixProject';
    }

    @Override
    public int ordinal() {
        1
    }
}


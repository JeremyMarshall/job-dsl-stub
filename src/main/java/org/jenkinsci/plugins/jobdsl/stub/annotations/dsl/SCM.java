package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class SCM extends Base {

    public SCM() {
        category = CategoryEnum.SCM;
    }
}


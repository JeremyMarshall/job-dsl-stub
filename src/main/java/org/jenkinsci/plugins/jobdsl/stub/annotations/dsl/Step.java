package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl;

import hudson.ExtensionList;
import hudson.Extension;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.CategoryEnum;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
public class Step extends Base {
    public Step() {
        category = CategoryEnum.STEP;
    }
}


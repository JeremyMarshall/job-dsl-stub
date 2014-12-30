package org.jenkinsci.plugins.jobdsl.stub.helpers;

import hudson.ExtensionList;
import jenkins.model.Jenkins;

/**
 * Created by jeremymarshall on 31/12/2014.
 */
public class Step extends Base{

    //there must be a crazy way to do this once rather than in each extension point
    public static ExtensionList<Step> all() {
        return Jenkins.getInstance().getExtensionList(Step.class);
    }
}


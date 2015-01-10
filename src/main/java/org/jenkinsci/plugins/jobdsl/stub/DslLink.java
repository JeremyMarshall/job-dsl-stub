package org.jenkinsci.plugins.jobdsl.stub;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.ManagementLink;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.Factory;

@Extension
public class DslLink extends ManagementLink implements Describable<DslLink> {
    private Factory factory = new Factory();

    @Override
    public String getDisplayName() {
        return "Jenkins DSL";
    }

    @Override
    public String getDescription() {
        return "Stubs for Job-DSL";
    }

    @Override
    public String getIconFileName() {
        return "aaa.png";
    }

    @Override
    public String getUrlName() {
        return "dsl";
    }

    public Factory getFactory() {
        return factory;
    }

    @SuppressWarnings("unchecked")
    public Descriptor<DslLink> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends Descriptor<DslLink> {
        public String getDisplayName() {
            return "DSL Link";
        }
    }
}

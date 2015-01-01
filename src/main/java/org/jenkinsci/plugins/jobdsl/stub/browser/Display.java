package org.jenkinsci.plugins.jobdsl.stub.browser;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.ManagementLink;
import hudson.model.Saveable;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.jenkinsci.plugins.jobdsl.stub.Factory;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

@Extension
public class Display extends ManagementLink implements Describable<Display>, StaplerProxy {

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
    public Object getTarget() {
        Jenkins.getInstance().checkPermission(Jenkins.ADMINISTER);
        return this;
    }

    @Override
    public String getUrlName() {
        return "dsl";
    }

    public Factory getFactory(){
        return factory;
    }

    @SuppressWarnings("unchecked")
    public Descriptor<Display> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

}

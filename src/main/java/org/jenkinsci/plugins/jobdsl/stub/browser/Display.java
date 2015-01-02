package org.jenkinsci.plugins.jobdsl.stub.browser;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.ManagementLink;
import hudson.model.Saveable;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.jenkinsci.plugins.jobdsl.stub.Factory;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Extension
public class Display extends ManagementLink implements Describable<Display>, StaplerProxy {
//public class Display extends ManagementLink implements  StaplerProxy {

    private Factory factory = new Factory();
    private String test = "instance";
    private List<String> test2 = Arrays.asList("a", "b", "c");

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

    public String getTest() {
        return test;
    }

    public List<String> getTest2() {
        return test2;
    }
    @SuppressWarnings("unchecked")
    public Descriptor<Display> getDescriptor() {
       return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

/*
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends Descriptor<Display> {

        private String test = "test";

        public DescriptorImpl() {
            test = "TEST";
        }

        public String getDisplayName() {
            return "DSL Display Screen";
        }

        public String getTest() {
            return test;
        }
    }
*/
}

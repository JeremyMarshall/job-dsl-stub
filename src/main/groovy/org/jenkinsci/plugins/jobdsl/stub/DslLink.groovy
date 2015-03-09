package org.jenkinsci.plugins.jobdsl.stub

import com.thoughtworks.xstream.XStream;
import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.ManagementLink;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.jobdsl.stub.Factory
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category
import org.jenkinsci.plugins.jobdsl.stub.model.Class
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Step;

@Extension
public class DslLink extends ManagementLink implements Describable<DslLink> {
    private Factory factory = new Factory();
    private String dslInterface

    DslLink() {
        buildDslInterface()
    }

    String buildDslInterface() {
        List<String> builder = []

        factory.categoriesAsList.each { cat ->

            //this is probably fairly bad
            if (cat.name == 'closure') {
                return
            }

            builder << "//${cat.description}"
            builder << "${cat.name} {"

            cat.classes.sort().each { c ->
                builder << "\t// ${Jenkins.instance.pluginManager.whichPlugin(c.clazz).toString()}"
                builder += classDisplay(c, 0)
            }
            builder << '}'
            builder << ''
        }
        dslInterface = builder.join("\n")
    }

    private final List<String> classDisplay(org.jenkinsci.plugins.jobdsl.stub.model.Class c, int indent) {

        List<String> builder = []
        indent++
        String indentStr = '\t'.multiply(indent)
        String indentStrParm = '\t'.multiply(indent + 1)

        builder << "$indentStr// ${c.clazz}"
        builder << ''

        c.methods.sort().each { m ->
            builder << "$indentStr// ${m.description}"

            if(m.closureClass == NoClosure ) {
                if (m.parameters.size() > 0) {
                    builder << "$indentStr${m.name}"
                    m.parameters.each { p ->
                        builder << "$indentStrParm${p.description}" + (p == m.parameters.last() ? '' : ',')
                    }
                } else {
                    builder << "$indentStr${m.name}()"
                }
            } else {
                builder << "$indentStr${m.name} {"
                org.jenkinsci.plugins.jobdsl.stub.model.Category cl2 = factory.getCategory(Closure)
                org.jenkinsci.plugins.jobdsl.stub.model.Class cl3 = cl2.classes.find { it.clazz == m.closureClass }
                builder += classDisplay( cl3, indent + 1)
                builder << "$indentStr}"
            }
            builder << ''
        }
        builder
    }

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

    public String getDslInterface() {
        return dslInterface
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
        String displayName = "DSL Link";
    }
}

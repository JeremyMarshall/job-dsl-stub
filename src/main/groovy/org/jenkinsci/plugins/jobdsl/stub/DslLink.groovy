package org.jenkinsci.plugins.jobdsl.stub

import hudson.Extension
import hudson.model.Describable
import hudson.model.Descriptor
import hudson.model.ManagementLink
import jenkins.model.Jenkins
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure

@Extension
class DslLink extends ManagementLink implements Describable<DslLink> {
    private final Factory factory = new Factory()
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
        dslInterface = builder.join('\n')
    }

    final List<String> classDisplay(org.jenkinsci.plugins.jobdsl.stub.model.Class c, int indent) {

        List<String> builder = []
        indent++
        String indentStr = '\t' * indent
        String indentStrParm = '\t' * (indent + 1)

        if ( !c ) {
            return builder
        }

        builder << "$indentStr// ${c.clazz}"
        builder << ''

        c.methods.sort().each { m ->
            builder << "$indentStr// ${m.description}"

            if ( m.isProxyClass() ) {
                builder << "$indentStr${m.name} {"

                Category p = m.getProxyClass().newInstance()

                Category cl2 = factory.getCategory(p.name)
                //org.jenkinsci.plugins.jobdsl.stub.model.Class cl3 = cl2.classes.find { it.clazz == m.closureClass }
                cl2.classes.each { c2 ->
                    builder += classDisplay(c2, indent + 1)
                    builder << "$indentStr}"
                }
            } else if ( m.closureClass == NoClosure ) {
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

    Object invoke( Class clazz, Class cat, String name, Object... args ) {

        org.jenkinsci.plugins.jobdsl.stub.model.Method m = factory.getCategory(cat).getMethod(name, *args)
        Object o

        if (m instanceof org.jenkinsci.plugins.jobdsl.stub.model.Method) {
            o = m.execute(*args)
        } else {
            //we don't add this method so pass it up
            //probably to the Node class
            //which does similar invokeMethod things
            //as groovy unwinds the closures until it
            //gets a match for the method
            //and Node is very promiscuous with what it will use
            throw new MissingMethodException(name, clazz, *args)
        }

        o
    }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDisplayName() {
        'Jenkins DSL'
    }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() {
        'Stubs for Job-DSL'
    }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getIconFileName() {
        'aaa.png'
    }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getUrlName() {
        'dsl'
    }

    String getDslInterface() {
        dslInterface
    }

    Factory getFactory() {
        factory
    }

    @SuppressWarnings( 'unchecked' )
    Descriptor<DslLink> getDescriptor() {
        Jenkins.instance.getDescriptorOrDie(getClass())
    }

    @Extension
    static final class DescriptorImpl extends Descriptor<DslLink> {
        String displayName = 'DSL Link'
    }
}

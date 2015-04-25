package org.jenkinsci.plugins.jobdsl.stub.cli;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import hudson.PluginWrapper;
import jenkins.model.Jenkins;

/**
 * Created by jeremymarshall on 25/02/2015.
 */

public class CliClosureConverter extends AbstractReflectionConverter {

    public CliClosureConverter(Mapper mapper, ReflectionProvider reflectionProvider){
        super(mapper, reflectionProvider);
    }

    public boolean canConvert(Class clazz)
    {
        return CliClosure.class.equals(clazz);
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        CliClosure cl = obj

        cl.items.each{ inner ->
            if(inner instanceof CliClosure){
                if(inner.items.size() == 1) {
                    if (inner.items[0] instanceof String) { //String fields
                        writer.startNode(inner.name)
                        writer.setValue(inner.items[0])
                        writer.endNode()
                        return
                    } else if (inner.name == 'scm') {
                        writer.startNode(inner.name)
                        context.convertAnother(inner.items[0])
                        writer.endNode()
                        return
                    }
                }

                writer.startNode(inner.name)
                inner.items.each { inner2 ->
                    writer.startNode(inner2.class.name)
                    context.convertAnother(inner2)
                    writer.endNode()
                }
                writer.endNode()
            }
        }
    }

}


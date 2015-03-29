package org.jenkinsci.plugins.jobdsl.stub

import hudson.util.XStream2
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Step
import org.jenkinsci.plugins.jobdsl.stub.model.Method

/**
 * Created by jeremymarshall on 21/03/2015.
 */
abstract class DslShell extends Script {

    static invoke(Class clazz, Class cat, String name, Object... args) {
        Factory f = new Factory()

        Method m = f.getCategory(cat).getMethod(name, *args)
        Object o

        if (m instanceof Method) {
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

        XStream2 xstream = f.getXStream()
        String xml = xstream.toXML(o)

        new XmlParser().parseText(xml)
    }

}

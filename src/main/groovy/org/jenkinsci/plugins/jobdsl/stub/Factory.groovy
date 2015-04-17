package org.jenkinsci.plugins.jobdsl.stub

import hudson.util.XStream2
//import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category
import org.jenkinsci.plugins.jobdsl.stub.model.Category
import org.jenkinsci.plugins.jobdsl.stub.model.Class

/**
 * Created by jeremymarshall on 31/12/2014.
 */

class Factory  {

    private final Map<String, org.jenkinsci.plugins.jobdsl.stub.model.Category> categories
    private final List<org.jenkinsci.plugins.jobdsl.stub.model.Category> categoriesAsList
    private final XStream2 xstream

    Factory() {
        categories = new TreeMap<String, org.jenkinsci.plugins.jobdsl.stub.model.Category>()

        xstream = new XStream2()
        //scm classes need massaging as they have to appear
        //<scm class='blah'>...<scm>
        xstream.autodetectAnnotations(true)
        for (org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category a : org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category.all()) {
            add(a)
        }

        categoriesAsList = new ArrayList<org.jenkinsci.plugins.jobdsl.stub.model.Category>(categories.values())
        Arrays.sort(categoriesAsList)

        //maybe we need to massage the XML
        //such as scm links need to be <scm class="blah">
        //rather than <blah>...
        for ( Map.Entry<String,org.jenkinsci.plugins.jobdsl.stub.model.Category> entry : categories.entrySet()) {
            org.jenkinsci.plugins.jobdsl.stub.model.Category value = entry.value

            for ( Class c : value.classes ) {
                c.getInstance(false).xstreamAlias(xstream)
            }
        }
    }

    org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category add(org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category a) {

        if ( !categories.containsKey(a.category )) {
            categories.put(a.category, new org.jenkinsci.plugins.jobdsl.stub.model.Category(a))
        }

        //so the individual categories are extension points too so
        //they will appear in the list even when no actual plugins implement them
        //keep them out of the classes though!

        //if there are no methods it must be a category (step, scm, etc)
        //don't forget to get your concrete classes to set this to true
        if ( a.hasMethods()) {
            categories.get(a.category ).add(a)
        } else {
            categories.get(a.category ).update(a)
        }

        a
    }

    XStream2 getXStream() {
        xstream
    }

    Category getCategory( Class c) {
        categories.get(c.name)
    }

    Category getCategory( String c) {
        categories.get(c.toLowerCase())
    }

    List<Category> getCategoriesAsList() {
        categoriesAsList.sort(true)
    }

}

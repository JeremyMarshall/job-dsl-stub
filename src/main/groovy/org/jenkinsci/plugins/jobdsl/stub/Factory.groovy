package org.jenkinsci.plugins.jobdsl.stub

import com.thoughtworks.xstream.XStream
import hudson.util.XStream2
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category
import org.jenkinsci.plugins.jobdsl.stub.model.Class

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Factory  {

    private Map<String, org.jenkinsci.plugins.jobdsl.stub.model.Category> categories;
    private List<org.jenkinsci.plugins.jobdsl.stub.model.Category> categoriesAsList;
    private XStream2 xstream;

    public Factory() {
        categories = new TreeMap<String, org.jenkinsci.plugins.jobdsl.stub.model.Category>();

        xstream = new XStream2()
        //scm classes need massaging as they have to appear
        //<scm class='blah'>...<scm>
        xstream.autodetectAnnotations(true)
        for (Category a : Category.all()) {
            add(a);
        }

        buildCategoriesAsList()

        //maybe we need to massage the XML
        //such as scm links need to be <scm class="blah">
        //rather than <blah>...
        for(Map.Entry<String,org.jenkinsci.plugins.jobdsl.stub.model.Category> entry : categories.entrySet()) {
            String key = entry.getKey();
            org.jenkinsci.plugins.jobdsl.stub.model.Category value = entry.getValue();

            for(Class c : value.getClasses()) {
                c.getInstance(false).xstreamAlias(xstream);
            }
        }

    }

    public Category add(Category a) {

        if(!categories.containsKey(a.getCategory())) {
            categories.put(a.getCategory(), new org.jenkinsci.plugins.jobdsl.stub.model.Category(a));
        }

        //so the individual categories are extension points too so
        //they will appear in the list even when no actual plugins implement them
        //keep them out of the classes though!

        //if there are no methods it must be a category (step, scm, etc)
        //don't forget to get your concrete classes to set this to true
        if( a.hasMethods()) {
            categories.get(a.getCategory()).add(a);
        } else {
            categories.get(a.getCategory()).update(a);
        }

        return a;
    }

    public void buildCategoriesAsList() {
        categoriesAsList = new ArrayList<org.jenkinsci.plugins.jobdsl.stub.model.Category>(categories.values());
        Arrays.sort(categoriesAsList)
    }

    public XStream2 getXStream() {
        return xstream;
    }

    public org.jenkinsci.plugins.jobdsl.stub.model.Category getCategory( java.lang.Class c) {
        return categories.get(c.getName());
    }

    public List<org.jenkinsci.plugins.jobdsl.stub.model.Category> getCategoriesAsList(){
        return categoriesAsList.sort(true)
    }

}

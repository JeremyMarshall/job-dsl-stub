package org.jenkinsci.plugins.jobdsl.stub;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category;

import java.util.*;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Factory extends AbstractDescribableImpl<Factory> implements Describable<Factory> {

    private Map<CategoryEnum, org.jenkinsci.plugins.jobdsl.stub.model.Category> categories;
    private List<org.jenkinsci.plugins.jobdsl.stub.model.Category> categoriesAsList;

    List ba;

    public Factory() {
        categories = new TreeMap<CategoryEnum, org.jenkinsci.plugins.jobdsl.stub.model.Category>();

        for (Category a : Category.all()) {
            add(a);
        }

        categoriesAsList = new ArrayList<org.jenkinsci.plugins.jobdsl.stub.model.Category>(categories.values());
        Collections.sort(categoriesAsList);
    }

    public Category add(Category a) {

        if(!categories.containsKey(a.getCategory())) {
            categories.put(a.getCategory(), new org.jenkinsci.plugins.jobdsl.stub.model.Category(a));
        }

        //so the individual categories are extension points too so
        //they will appear in the list even when no actual plugins implement them
        //keep them out of the classes though!

        //should a different plugin implement a new category then we're in trouble

        if( ! a.getClass().getCanonicalName().contains("org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.")) {
            categories.get(a.getCategory()).add(a);
        } else {
            categories.get(a.getCategory()).update(a);
        }

        return a;
    }

    public org.jenkinsci.plugins.jobdsl.stub.model.Category getCategory( CategoryEnum e) {
        return categories.get(e);
    }

    public List<org.jenkinsci.plugins.jobdsl.stub.model.Category> getCategoriesAsList(){
        return categoriesAsList;
    }


    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }


    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends Descriptor<Factory> {

        public String getDisplayName() {
            return "DSL Factory";
        }
    }
}

package org.jenkinsci.plugins.jobdsl.stub;


import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;

import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Base;
import org.jenkinsci.plugins.jobdsl.stub.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Factory extends AbstractDescribableImpl<Factory> implements Describable<Factory> {

    private Map<CategoryEnum, Category> categories;
    private List<Category> categoriesAsList;

    List ba;

    public Factory() {
        categories = new TreeMap<CategoryEnum, Category>();

        for (Base a : Base.all()) {
            add(a);
        }

        categoriesAsList = new ArrayList<Category>(categories.values());
    }

    public Base add(Base a) {

        if(!categories.containsKey(a.getCategory())) {
            categories.put(a.getCategory(), new Category(a.getCategory()));
        }

        //so the individual categories are extension points too so
        //they will appear in the list even when no actual plugins implement them
        //keep them out of the classes though!

        if( ! a.getClass().getCanonicalName().contains("org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.")) {
            categories.get(a.getCategory()).add(a);
        }

        return a;
    }

    public List<Category> getCategoriesAsList(){
        return categoriesAsList;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Factory> {
        @Override public String getDisplayName() {
            return "DSL Factory";
        }
    }
}

package org.jenkinsci.plugins.jobdsl.stub;

import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category;

import java.util.*;

/**
 * Created by jeremymarshall on 31/12/2014.
 */

public class Factory  {

    private Map<String, org.jenkinsci.plugins.jobdsl.stub.model.Category> categories;
    private List<org.jenkinsci.plugins.jobdsl.stub.model.Category> categoriesAsList;

    //List ba;

    public Factory() {
        categories = new TreeMap<String, org.jenkinsci.plugins.jobdsl.stub.model.Category>();

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

        //if there are no methods it must be a category (step, scm, etc)
        //don't forget to get your concrete classes to set this to true
        if( a.hasMethods()) {
            categories.get(a.getCategory()).add(a);
        } else {
            categories.get(a.getCategory()).update(a);
        }

        return a;
    }

    public org.jenkinsci.plugins.jobdsl.stub.model.Category getCategory( Class c) {
        return categories.get(c.getName());
    }

    public List<org.jenkinsci.plugins.jobdsl.stub.model.Category> getCategoriesAsList(){
        return categoriesAsList;
    }

}

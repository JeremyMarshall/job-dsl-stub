package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
class Step extends Category {
    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getName() { 'steps' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() { 'Classes for Build steps' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    final String getCategory() { 'steps' }

    @Override
    int ordinal() { 100 }

}


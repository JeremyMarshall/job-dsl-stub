package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
class Closure extends Category {
    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getName() { 'closure' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() { 'Classes for Closures' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    final String getCategory() { Closure.name }

    @Override
    int ordinal() { 0 }
}


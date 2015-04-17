package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
class Publisher extends Category {

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getName() { 'publishers' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() { 'Classes for Publish steps' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    final String getCategory() { 'publishers' }

    @Override
    int ordinal() { 150 }
}

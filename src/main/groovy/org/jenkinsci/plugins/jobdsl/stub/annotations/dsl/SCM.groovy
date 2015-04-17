package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
class Scm extends Category {

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getName() { 'scm' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() { 'Classes for SCM stage' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    final String getCategory() { 'scm' }

    @Override
    int ordinal() { 10 }

}


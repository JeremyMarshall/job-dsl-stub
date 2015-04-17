package org.jenkinsci.plugins.jobdsl.stub.annotations.dsl

import hudson.Extension

/**
 * Created by jeremymarshall on 31/12/2014.
 */

@Extension
class Axis extends Category {

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getName() { 'axes' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getDescription() { 'Classes for Matrix Axes' }

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    final String getCategory() { 'axes' }

    @Override
    int ordinal() { 50 }
}


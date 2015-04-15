package org.jenkinsci.plugins.jobdsl.stub.cli

/**
 * Created by jeremymarshall on 13/04/15.
 */
class CliClosure {
    String name
    Object items = []

    CliClosure(String name) {
        this.name = name
    }
}

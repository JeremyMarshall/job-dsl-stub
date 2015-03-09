package org.jenkinsci.plugins.jobdsl.stub

import org.junit.Rule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Specification

class DslLinkSpec extends Specification {
    @Rule
    JenkinsRule rule = new JenkinsRule()

    def 'run'() {
        when:
        def d = new DslLink()

        d.factory.add(new TestCategory())
        d.factory.add(new TestClass())
        d.factory.add(new TestClosure())
        d.factory.buildCategoriesAsList()

        d.buildDslInterface()

        then:
        [
            'testMethodListString', 'testMethodString',
            'testMethodVaargsString', 'testMethodWithClosure',
            'testMethod'].each {
                assert d.dslInterface.contains(it)
        }

    }
}
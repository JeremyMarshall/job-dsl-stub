package org.jenkinsci.plugins.jobdsl.stub

//import hudson.Extension
//import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Category
//import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure
//import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
//import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import org.junit.Rule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Specification

class FactorySpec extends Specification {
    @Rule
    JenkinsRule rule = new JenkinsRule()

    def 'loaded'() {
        when:
        def f = new Factory()

        f.add(new TestCategory())
        f.add(new TestClass())
        f.add(new TestClosure())
        f.buildCategoriesAsList()

        then:
        assert f.categoriesAsList.size() == 6
    }

    def 'vaargs'() {
        when:
        def f = new Factory()

        f.add(new TestCategory())
        f.add(new TestClass())
        f.add(new TestClosure())
        f.buildCategoriesAsList()

        def ret = f.getCategory(TestCategory).getMethod('testMethodVaargsString', 'a', 'b', 'c').execute('a', 'b', 'c')

        then:

        assert ret instanceof String
        ret.trim().matches('a b c')
    }

    def 'params'() {
        when:
        def f = new Factory()

        f.add(new TestCategory())
        f.add(new TestClass())
        f.add(new TestClosure())
        f.buildCategoriesAsList()

        def ret = f.getCategory(TestCategory).getMethod('testMethodString', 'name').execute('name')

        then:

        assert ret instanceof String
        ret.matches('name')
    }

    def 'array'() {
        when:
        def f = new Factory()

        f.add(new TestCategory())
        f.add(new TestClass())
        f.add(new TestClosure())
        f.buildCategoriesAsList()

        def ret = f.getCategory(TestCategory).getMethod('testMethodListString', [['name']]).execute([['name']])

        then:

        assert ret instanceof String
        ret.trim().matches('name')
    }

    def 'closure'() {
        when:
        def f = new Factory()

        f.add(new TestCategory())
        f.add(new TestClass())
        f.add(new TestClosure())
        f.buildCategoriesAsList()
        def ret = f.getCategory(TestCategory).getMethod('testMethodWithClosure', {testMethod 'name'}).execute({testMethod 'name'})

        then:

        assert ret instanceof TestClosure
        ret.testValue.matches('name')
    }
}

package org.jenkinsci.plugins.jobdsl.stub;

/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import groovy.lang.GroovyShell;
import groovy.lang.Binding;
import groovy.lang.Script;
import hudson.cli.CLICommand;
import hudson.cli.util.ScriptLoader;
import hudson.model.AbstractProject;
import jenkins.model.Jenkins;
import hudson.model.Item;
import hudson.model.Run;
import hudson.remoting.Callable;
import hudson.AbortException;
import hudson.Extension;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Executes the specified groovy script.
 *
 * @author Kohsuke Kawaguchi
 */

/**
 * Modified a bit to support DSL stuff
 */
@Extension
public class DslStubCommand extends CLICommand {
    @Override
    public String getShortDescription() {
        return "dsl-stub";
    }

    @Argument(metaVar="SCRIPT",usage="Script to be executed. File, URL or '=' to represent stdin.")
    public String script;

    /**
     * Remaining arguments.
     */
    @Argument(index=1)
    public List<String> remaining = new ArrayList<String>();

    protected int run() throws Exception {
        // this allows the caller to manipulate the JVM state, so require the execute script privilege.
        Jenkins.getInstance().checkPermission(Jenkins.RUN_SCRIPTS);

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(DslShell.class.getName());

        Binding binding = new Binding();
        PrintWriter out = new PrintWriter(stdout,true);

        //binding.setProperty("out", out);

        //binding.setProperty("stdin",stdin);
        //binding.setProperty("stdout",stdout);
        binding.setProperty("stderr",stderr);
        binding.setProperty("channel",channel);
        String j = getClientEnvironmentVariable("JOB_NAME");
        if (j!=null) {
            Item job = Jenkins.getInstance().getItemByFullName(j);
            binding.setProperty("currentJob", job);
            String b = getClientEnvironmentVariable("BUILD_NUMBER");
            if (b!=null && job instanceof AbstractProject) {
                Run r = ((AbstractProject) job).getBuildByNumber(Integer.parseInt(b));
                binding.setProperty("currentBuild", r);
            }
        }

        GroovyShell groovy = new GroovyShell(Jenkins.getInstance().getPluginManager().uberClassLoader, binding, compilerConfiguration);
        DslShell script = (DslShell) groovy.parse(loadScript());

        //script.run("RemoteClass", remaining.toArray(new String[remaining.size()]));
        script.run();

        Factory f = new Factory();

        for(Object o: script.getReturns()) {
            //f.getXStream().toXML(o, stdout);
            f.getXStream().toXML(o, out);
            out.println();
        }

        return 0;
    }

    /**
     * Loads the script from the argument.
     */
    private String loadScript() throws CmdLineException, IOException, InterruptedException {
        if(script==null)
            throw new CmdLineException(null, "No script is specified");
        if (script.equals("="))
            return IOUtils.toString(stdin);

        return checkChannel().call(new ScriptLoader(script));
    }
}
/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.tools.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.paracamplus.ilp1.tools.ProgramCaller;

public class ProgramCallerTest {

	@Test
    public void testProgramCallerInexistentVerbose () {
        final String programName = "lasdljsdfousadfl lsjd";
        ProgramCaller pc = new ProgramCaller(programName);
        assertNotNull(pc);
        pc.setVerbose();
        pc.run();
        assertTrue(pc.getExitValue() != 0);
    }
	
	@Test
    public void testProgramCallerInexistent () {
        final String programName = "lasdljsdfousadfl lsjd";
        ProgramCaller pc = new ProgramCaller(programName);
        pc.run();
        final String result = pc.getStderr();
        System.err.println("Result: " + result); // DEBUG
        Pattern p = Pattern.compile(".*(not found|Cannot run).*");
        Matcher m = p.matcher(result);
        assertTrue(m.matches());
        assertTrue(pc.getExitValue() != 0);
    }

	@Test
    public void testProgramCallerEchoVerbose () {
        final ProgramCaller pc = new ProgramCaller("echo cou cou  cou");
        assertNotNull(pc);
        pc.setVerbose();
        pc.run();
        assertTrue(pc.getExitValue() == 0);
        final String result = pc.getStdout();
        System.err.println("Result: " + result); // DEBUG
        assertNotNull(result);
        assertTrue(result.length() > 0);
        assertEquals(result.trim(), "cou cou cou");
    }
	
	@Test
    public void testProgramCallerEcho () {
        final ProgramCaller pc = new ProgramCaller("echo cou cou  cou");
        assertNotNull(pc);
        pc.run();
        assertTrue(pc.getExitValue() == 0);
        final String result = pc.getStdout();
        System.err.println("Result: " + result); // DEBUG
        assertNotNull(result);
        assertTrue(result.length() > 0);
        assertEquals(result.trim(), "cou cou cou");
    }

	@Test
    public void testProgramCallerGccOnStdout () {
        final ProgramCaller pc = new ProgramCaller("gcc -v");
        assertNotNull(pc);
        pc.run();
        assertTrue(pc.getExitValue() == 0);
        final String result = pc.getStdout();
        System.err.println("Result: " + result); // DEBUG
        assertNotNull(result);
        assertTrue(result.length() == 0);
        assertEquals(result.trim(), "");
    }

    // MacOSX says clang instead of gcc:
    private String gccPattern = ".*(clang|cc).*";
    
    @Test
    public void testProgramCallerGccOnStderr () {
        final ProgramCaller pc = new ProgramCaller("gcc -c unexistent.c");
        assertNotNull(pc);
        pc.run();
        assertTrue(pc.getExitValue() != 0);
        final String errors = pc.getStderr();
        System.err.println("Errors: " + errors); // DEBUG
        assertNotNull(errors);
        assertTrue(errors.length() > 0);
        Pattern p = Pattern.compile(gccPattern, Pattern.DOTALL);
        Matcher m = p.matcher(errors);
        assertTrue(m.matches());
    }

    @Test
    public void testProgramCallerGccOnStderrVerbose () {
        final ProgramCaller pc = new ProgramCaller("gcc -c unexistent.c");
        assertNotNull(pc);
        pc.setVerbose();
        pc.run();
        assertTrue(pc.getExitValue() != 0);
        final String errors = pc.getStderr();
        System.err.println("Errors: " + errors); // DEBUG
        assertNotNull(errors);
        assertTrue(errors.length() > 0);
        Pattern p = Pattern.compile(gccPattern, Pattern.DOTALL);
        Matcher m = p.matcher(errors);
        assertTrue(m.matches());
    }

    @Test
    public void testProgramCallerVerbose () {
        final ProgramCaller pc = new ProgramCaller("echo See what happens");
        assertNotNull(pc);
        pc.setVerbose();
        pc.run();
        assertTrue(pc.getExitValue() == 0);
        final String result = pc.getStdout();
        System.err.println("Result: " + result); // DEBUG
        assertNotNull(result);
        assertTrue(result.length() > 0);
        Pattern p1 = Pattern.compile(".*See what happens.*");
        Matcher m1 = p1.matcher(result.trim());
        assertTrue(m1.matches());
        final String errors = pc.getStderr();
        System.err.println("Errors: " + errors); // DEBUG
        assertNotNull(errors);
        assertTrue(errors.length() == 0);
    }

    // testing multiple concurrent tasks

    @Test
    public void testMultipleProgramCallers () {
        final int max = 3;
        final ProgramCaller[] pcs = new ProgramCaller[max];
        for ( int i = max ; i>0 ; i-- ) {
            pcs[i-1] = new ProgramCaller("sleep 0." + i);
            pcs[i-1].run();
        }
        for ( int i = 0 ; i<max ; i++ ) {
            assertTrue(pcs[i].getExitValue() == 0);
        }
    }

}

// end of ProgramCallerTest.java

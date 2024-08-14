/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class ProgramCaller {

    public ProgramCaller(final String program) {
        this.program = program;
        this.stdout = new StringBuffer(1023);
        this.stderr = new StringBuffer(1023);
        this.running = false;
        this.verbose = 0;
    }

    private final String program;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private Process process;
    private final StringBuffer stdout;
    private final StringBuffer stderr;
    private CountDownLatch countDownLatch;
    private boolean running;
    private int verbose = 110;

    public void setVerbose() {
        this.verbose++;
    }

    private void verbalize(final String message) {
        this.verbalize(message, 0);
    }

    private void verbalize(final String message, final int level) {
        if (this.verbose > level) {
            System.err.println(message);
        }
    }

    public String getStdout() {
        return stdout.toString();
    }

    public String getStderr() {
        return stderr.toString();
    }

    public int getExitValue() {
        this.verbalize("-getExitValue-", 10);
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            this.verbalize("-getExitValue Exception-", 10);
            throw new RuntimeException();
        }
        return this.exitValue;
    }

    private transient int exitValue = 199;

    public void run() {
        synchronized (this) {
            if (running) {
                return;
            } else {
                running = true;
            }
        }
        
        verbalize("[Running: " + program + "...");
        try {
            this.countDownLatch = new CountDownLatch(2);
            this.process = RUNTIME.exec(program);
        } catch (Throwable e) {
            this.stderr.append(e.getMessage());
            this.countDownLatch.countDown();
            this.countDownLatch.countDown();
            verbalize("...not started]");
            return;
        }

        slurpStdOut();
        slurpStdErr();

        try {
            this.process.waitFor();
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            this.verbalize("!run Exception!", 10);
            throw new RuntimeException();
        }
        this.exitValue = this.process.exitValue();
        verbalize("...finished]");
    }
    
    private void slurpStdOut() {
        final Thread tstdout = new Thread () {
            @Override
            public void run () {
                try (final InputStream istdout = process.getInputStream();
                     final BufferedInputStream bstdout = 
                            new BufferedInputStream(istdout) ) {
                    final int size = 4096;
                    final byte[] buffer = new byte[size];
                    READ:
                        while ( true ) {
                            int count = 0;
                            try {
                                count = bstdout.read(buffer, 0, size);
                            } catch (IOException exc) {
                                continue READ;
                            }
                            if ( count > 0 ) {
                                final String s = new String(buffer, 0, count);
                                stdout.append(s);
                                ProgramCaller.this.verbalize("[stdout Reading: " + s + "]");
                            } else if ( count == -1 ) {
                                ProgramCaller.this.verbalize("[stdout Dried!]", 10);
                                ProgramCaller.this.countDownLatch.countDown();
                                return;
                            }
                        }
                } catch (IOException e) {
                    ProgramCaller.this.verbalize("[stdout problem!" + e + ']', 10);}
            }
        };
        tstdout.start();
    }

    private void slurpStdErr() {
        final Thread tstderr = new Thread () {
            @Override
            public void run () {
                try (final InputStream istderr = process.getErrorStream();
                     final BufferedInputStream bstderr = 
                              new BufferedInputStream(istderr) ) {
                    final int size = 4096;
                    final byte[] buffer = new byte[size];
                    READ:
                        while ( true ) {
                            int count = 0;
                            try {
                                count = bstderr.read(buffer, 0, size);
                            } catch (IOException exc) {
                                continue READ;
                            }
                            if ( count > 0 ) {
                                final String s = new String(buffer, 0, count);
                                stderr.append(s);
                                ProgramCaller.this.verbalize("[stderr Reading: " + s + "]");
                            } else if ( count == -1 ) {
                                ProgramCaller.this.verbalize("[stderr Dried!]", 10);
                                ProgramCaller.this.countDownLatch.countDown();
                                return;
                            }
                        }
                } catch (IOException e) {
                    ProgramCaller.this.verbalize("[stderr problem!" + e + ']', 10);
                }
            }
        };
        tstderr.start();
    }
}

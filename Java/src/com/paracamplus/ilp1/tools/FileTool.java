/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.tools;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTool {
    
    public static String slurpFile (final File file)
            throws IOException {
        final FileReader fr = new FileReader(file);
        return slurpFile(fr);
    }
    
    public static String slurpFile (final Reader fr)
    throws IOException {
        final StringBuffer sb = new StringBuffer();
        final BufferedReader br = new BufferedReader(fr);
        final char[] buffer = new char[4096];
        while ( true ) {
            int count = br.read(buffer);
            if (count < 0) {
                return sb.toString();
            }
            sb.append(buffer, 0, count);
        }
    }

    public static String slurpFile (final String filename)
    throws IOException {
        return FileTool.slurpFile(new File(filename));
    }
    
    public static void stuffFile (final File file, final String s)
    throws IOException {
        //final FileWriter fw = new FileWriter(file);
        //try {
        //    fw.write(s, 0, s.length());
        //} finally {
        //    fw.close();
        //}
        // New way since Java7:
        try (final FileWriter fw = new FileWriter(file)) {
            fw.write(s, 0, s.length());
        }
    }

    public static File changeSuffix(File file, String suffix) {
        String parent = file.getParent();
        String name = file.getName();
        String basename;
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex >= 0) {
            basename = name.substring(0, dotIndex);
        } else {
            basename = name;
        }
        String newName = parent + File.separator + basename + '.' + suffix;
        return new File(newName);
    }

    public static File[] getFileList(
    		String samplesDirName,
    		String pattern
    		) throws Exception {
        final Pattern p = Pattern.compile("^" + pattern + "$");
        final FilenameFilter ff = new FilenameFilter() {
            @Override
			public boolean accept (File dir, String name) {
                final Matcher m = p.matcher(name);
                return m.matches();
            }
        };
        File samplesDir = new File(samplesDirName);
        final File[] testFiles = samplesDir.listFiles(ff);
        if (testFiles == null) {
        	throw new IllegalArgumentException("Directory does not exist : " + samplesDirName);
        }
        assertNotNull(testFiles);
        
        if ( testFiles.length == 0 ) {
            final String msg = "Cannot find a single test like " + pattern + " in " + samplesDirName;
            throw new IllegalArgumentException(msg);
        }
        java.util.Arrays.sort(testFiles,
                (f1, f2) -> f1.getName().compareTo(f2.getName()));
		return testFiles;
    }

    public static File[] getFileList(
    		String[] samplesDirNames,
    		String pattern
    		) throws Exception {
    	List<File> files = new Vector<File>();
    	for (String d : samplesDirNames) {
    		for (File f : getFileList(d, pattern)) {
    			files.add(f);
    		}
    	}
    	return files.toArray(new File[0]);
    }

}

/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.tools;

import java.io.File;
import java.io.IOException;

public class InputFromFile implements Input {
    
    public InputFromFile (String fileName) throws IOException {
        file = new File(fileName);
        if ( ! file.exists() ) {
            throw new IOException("Absent file " + fileName);
        }
    }
    public InputFromFile (File file) {
        this.file = file;
    }
    private final File file;

    @Override
	public String getText() throws IOException {
        final String content = FileTool.slurpFile(file);
        return content;
    }
}

package com.paracamplus.ilp1.tools;

import java.io.IOException;

public class InputFromString implements Input {

	private String contents;
	
	public InputFromString(String s) {
		contents = s;
	}
	
	@Override
	public String getText() throws IOException {
		return contents;
	}

}

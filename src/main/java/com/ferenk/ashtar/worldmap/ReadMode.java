package com.ferenk.ashtar.worldmap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum ReadMode {
	START("^\\(\\:start\\s.*$", false),
	END("^\\(\\:end\\s.*$", false),
	HN("^\\(\\:hn.*$", true),
	GN("^\\(\\:gn.*$", true),
	ENDTAG("^\\)$", true),
	NA("", false);

	private final Pattern headerExpression;
	private final boolean skipLine;

	private ReadMode(String headerExpression, boolean skipLine) {
		this.headerExpression = Pattern.compile(headerExpression);
		this.skipLine = skipLine;
	}

	public static ReadMode forHeader(String header) {
		for (ReadMode mode : ReadMode.values()) {
			Matcher matcher = mode.headerExpression.matcher(header);
			if (matcher.matches()) {
				return mode;
			}
		}
		return NA;
	}

	public boolean isSkipLine() {
		return skipLine;
	}
}

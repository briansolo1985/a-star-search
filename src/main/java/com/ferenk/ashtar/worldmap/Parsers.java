package com.ferenk.ashtar.worldmap;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ferenk.ashtar.domain.Edge;
import com.ferenk.ashtar.domain.Node;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

class Parsers {

	private static final Map<ReadMode, ParseStrategy> parsers;

	static {
		parsers = new EnumMap<>(ReadMode.class);
		parsers.put(ReadMode.START, new StartParser());
		parsers.put(ReadMode.END, new EndParser());
		parsers.put(ReadMode.HN, new HnParser());
		parsers.put(ReadMode.GN, new GnParser());
	}

	private static abstract class AbstractParser implements ParseStrategy {
		private final Pattern dataPattern;

		public AbstractParser(String dataPatternExpression) {
			dataPattern = Pattern.compile(dataPatternExpression);
		}

		protected Matcher match(String text) {
			Matcher matcher = dataPattern.matcher(text);
			Preconditions.checkArgument(matcher.find(), "Mismatch %s-%s",
					dataPattern.pattern(), text);
			return matcher;
		}
	}

	private static class StartParser extends AbstractParser {
		public StartParser() {
			super("^.*\\s(\\p{L}+)\\)$");
		}

		@Override
		public void apply(String line, WorldMapBuilder worldMapBuilder) {
			Matcher matcher = match(line);
			worldMapBuilder.setStartNodeName(matcher.group(1));
		}
	}

	private static class EndParser extends AbstractParser {
		public EndParser() {
			super("^.*\\s(\\p{L}+)\\)$");
		}

		@Override
		public void apply(String line, WorldMapBuilder worldMapBuilder) {
			Matcher matcher = match(line);
			worldMapBuilder.setEndNodeName(matcher.group(1));
		}
	}

	private static class HnParser extends AbstractParser {
		public HnParser() {
			super("^\\s*\\((\\p{L}+)\\s(\\d+)\\)$");
		}

		@Override
		public void apply(String line, WorldMapBuilder worldMapBuilder) {
			Matcher matcher = match(line);

			worldMapBuilder.addNode(new Node(matcher.group(1),
					Ints.tryParse(matcher.group(2))));
		}
	}

	private static class GnParser extends AbstractParser {
		public GnParser() {
			super("^\\s*\\((\\p{L}+)\\s(\\p{L}+)\\s(\\d+)\\)$");
		}

		@Override
		public void apply(String line, WorldMapBuilder worldMapBuilder) {
			Matcher matcher = match(line);

			String nodeNameA = matcher.group(1);
			String nodeNameB = matcher.group(2);
			int distance = Ints.tryParse(matcher.group(3));

			worldMapBuilder.addEdge(
					new Edge(
							worldMapBuilder.getNodes().get(nodeNameA),
							worldMapBuilder.getNodes().get(nodeNameB),
							distance));
		}
	}

	public static ParseStrategy getStrategy(ReadMode readMode) {
		return parsers.get(readMode);
	}
}

interface ParseStrategy {
	public void apply(String line, WorldMapBuilder worldMapBuilder);
}

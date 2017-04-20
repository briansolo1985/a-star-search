package com.ferenk.ashtar.worldmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.ferenk.ashtar.domain.Edge;
import com.ferenk.ashtar.domain.Node;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

class WorldMapBuilder {

	private final Path source;

	private Map<String, Node> nodes = new HashMap<>();
	private Map<String, Edge> edges = new HashMap<>();

	private String startNodeName;
	private String endNodeName;

	private ReadMode readMode;

	public WorldMapBuilder(Path source) {
		this.source = source;

		this.readMode = null;
	}

	public Map<String, Node> getNodes() {
		return ImmutableMap.copyOf(nodes);
	}

	public void addNode(Node node) {
		Preconditions.checkArgument(
				nodes.put(node.getNodeName(), node) == null,
				"Duplicated node=%s", node);
	}

	public Map<String, Edge> getEdges() {
		return ImmutableMap.copyOf(edges);
	}

	public void addEdge(Edge edge) {
		Preconditions.checkArgument(
				edges.put(compositeKey(edge), edge) == null,
				"Duplicated edge=%s", edge);
	}

	public String getStartNodeName() {
		return startNodeName;
	}

	public void setStartNodeName(String startNodeName) {
		this.startNodeName = startNodeName;
	}

	public String getEndNodeName() {
		return endNodeName;
	}

	public void setEndNodeName(String endNodeName) {
		this.endNodeName = endNodeName;
	}

	public void build() {
		try (Stream<String> lines = Files.lines(source)) {
			lines.forEach(this::process);
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Read error", ioe);
		}
	}

	private void process(String line) {
		ReadMode nextMode = ReadMode.forHeader(line);
		if (!nextMode.isSkipLine()) {
			readMode = ReadMode.NA.equals(nextMode) ? readMode : nextMode;
			ParseStrategy strategy = Parsers.getStrategy(readMode);
			strategy.apply(line, this);
		} else {
			readMode = nextMode;
		}
	}

	private String compositeKey(Edge edge) {
		return String.format("%s#%s", edge.getNodeA().getNodeName(), edge
				.getNodeB().getNodeName());
	}
}

package com.ferenk.ashtar.worldmap;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ferenk.ashtar.domain.Edge;
import com.ferenk.ashtar.domain.Node;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class WorldMap {

	private LinkedList<Node> varosok;
	private LinkedList<Edge> utak;

	private String s_varos;
	private String e_varos;

	private Node start_varos;
	private Node end_varos;

	private final String startNodeName;
	private final String endNodeName;
	private final Map<String, Node> nodes;
	private final Map<String, Edge> edges;

	private WorldMap(WorldMapBuilder worldMapBuilder) {
		nodes = new HashMap<>(worldMapBuilder.getNodes());
		edges = new HashMap<>(worldMapBuilder.getEdges());
		startNodeName = worldMapBuilder.getStartNodeName();
		endNodeName = worldMapBuilder.getEndNodeName();
	}

	public static WorldMap build(Path source) {
		WorldMapBuilder worldMapBuilder = new WorldMapBuilder(source);
		worldMapBuilder.build();
		return new WorldMap(worldMapBuilder);
	}

	public Node getNode(String nodeName) {
		Preconditions.checkArgument(nodes.containsKey(nodeName),
				"Node not found %", nodeName);
		return nodes.get(nodeName);
	}

	public Node getStartNode() {
		return nodes.get(startNodeName);
	}

	public Node getEndNode() {
		return nodes.get(endNodeName);
	}

	public int getDistance(Node nodeA, Node nodeB) {
		Edge edge = edges.get(compositeKey(nodeA, nodeB));
		Preconditions.checkArgument(edge != null, "No edge between %s and %s",
				nodeA, nodeB);
		return edge.getDistance();
	}

	public List<Edge> getPathsFrom(Node node) {
		List<Edge> pathsFromNode = Lists.newLinkedList();
		for (String key : edges.keySet()) {
			if (node.getNodeName().compareTo(key.split("#")[0]) == 0) {
				pathsFromNode.add(edges.get(key));
			}
		}
		return pathsFromNode;
	}

	private String compositeKey(Node nodeA, Node nodeB) {
		return String.format("%s#%s", nodeA.getNodeName(), nodeB.getNodeName());
	}
}

package com.ferenk.ashtar.domain;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

public class Route implements Comparable<Route> {

	private final List<Node> nodes;

	private int previousStepCost;
	private int pathCost;

	public Route() {
		nodes = new LinkedList<>();
		previousStepCost = 0;
		pathCost = 0;
	}

	public Route(Node node, int distanceToNode) {
		this();
		add(node, distanceToNode);
	}

	public Route add(Node node, int distanceToNode) {
		nodes.add(node);
		previousStepCost = previousStepCost + distanceToNode;
		pathCost = previousStepCost + node.getHeuristicDistance();
		return this;
	}

	public Route add(Route route) {
		nodes.addAll(route.nodes);
		previousStepCost = previousStepCost + route.previousStepCost;
		pathCost = pathCost + route.pathCost;
		return this;
	}

	public Node lastNode() {
		return nodes.get(nodes.size() - 1);
	}

	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	public List<Node> asNodes() {
		return ImmutableList.copyOf(nodes);
	}

	public int previousStepCost() {
		return previousStepCost;
	}

	public int pathCost() {
		return pathCost;
	}

	@Override
	public int compareTo(Route route) {
		return Ints.compare(pathCost, route.pathCost);
	}

	@Override
	public String toString() {
		return "Route [nodes=" + nodes + ", pathCost=" + pathCost + "]";
	}
}

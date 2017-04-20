package com.ferenk.ashtar.domain;

public class Node {

	private String nodeName;
	private int heuristicDistance;

	public Node(String nodeName, int heuristic) {
		this.nodeName = nodeName;
		this.heuristicDistance = heuristic;
	}

	public String getNodeName() {
		return nodeName;
	}

	public int getHeuristicDistance() {
		return heuristicDistance;
	}

	@Override
	public String toString() {
		return "Node [nodeName=" + nodeName + ", heuristicDistance="
				+ heuristicDistance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		return true;
	}
}

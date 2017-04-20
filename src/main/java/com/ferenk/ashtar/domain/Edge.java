package com.ferenk.ashtar.domain;

public class Edge {

	private Node nodeA;
	private Node nodeB;
	private int distance;

	public Edge(Node nodeA, Node nodeB, int distance) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.distance = distance;
	}

	public Node getNodeA() {
		return nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeA == null) ? 0 : nodeA.hashCode());
		result = prime * result + ((nodeB == null) ? 0 : nodeB.hashCode());
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
		Edge other = (Edge) obj;
		if (nodeA == null) {
			if (other.nodeA != null)
				return false;
		} else if (!nodeA.equals(other.nodeA))
			return false;
		if (nodeB == null) {
			if (other.nodeB != null)
				return false;
		} else if (!nodeB.equals(other.nodeB))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Edge [nodeA=" + nodeA + ", nodeB=" + nodeB + ", distance="
				+ distance + "]";
	}

}

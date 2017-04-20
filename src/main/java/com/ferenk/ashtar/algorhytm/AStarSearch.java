package com.ferenk.ashtar.algorhytm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.ferenk.ashtar.domain.Edge;
import com.ferenk.ashtar.domain.Node;
import com.ferenk.ashtar.domain.Route;
import com.ferenk.ashtar.worldmap.WorldMap;
import com.google.common.collect.ImmutableList;

public class AStarSearch {

	private final List<String> log = new LinkedList<>();

	private final WorldMap worldMap;

	private List<Route> openList;
	private List<Route> closedList;

	public AStarSearch(WorldMap worldMap) {
		this.worldMap = worldMap;
		this.openList = new LinkedList<>();
		this.closedList = new LinkedList<>();
	}

	public Route search() {
		openList.clear();
		closedList.clear();

		Route route = new Route(worldMap.getStartNode(), 0);
		openList.add(route);

		while (openList.size() > 0) {
			logStep();

			Collections.sort(openList);
			Route shortestRoute = openList.remove(0);
			Node endNode = shortestRoute.lastNode();

			if (endNode.equals(worldMap.getEndNode())) {
				return shortestRoute;
			}
			closedList.add(shortestRoute);

			List<Edge> edgesToNeighbour = worldMap.getPathsFrom(endNode);
			for (Edge edgeToNeighbour : edgesToNeighbour) {
				Node neighbour = edgeToNeighbour.getNodeB();
				if (!shortestRoute.contains(neighbour)) {
					Route newRoute = new Route();
					newRoute.add(shortestRoute);
					newRoute.add(neighbour, edgeToNeighbour.getDistance());
					openList.add(newRoute);
				}
			}
		}

		throw new IllegalStateException("Could not find shortest path");
	}

	public List<String> getLog() {
		return ImmutableList.copyOf(log);
	}

	public List<Route> getOpenList() {
		Collections.sort(openList);
		return ImmutableList.copyOf(openList);
	}

	public List<Route> getClosedList() {
		Collections.sort(closedList);
		return ImmutableList.copyOf(closedList);
	}

	private void logStep() {
		log.add("OpenList\n");
		log.add(openList.toString());
		log.add("\nClosedList\n");
		log.add(closedList.toString());
		log.add("------------------------------");
	}
}

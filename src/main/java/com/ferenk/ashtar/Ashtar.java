package com.ferenk.ashtar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ferenk.ashtar.algorhytm.AStarSearch;
import com.ferenk.ashtar.domain.Route;
import com.ferenk.ashtar.worldmap.WorldMap;

public class Ashtar {

	private static final String INPUT_FILE_NAME = "input.txt";

	private final WorldMap worldMap;
	private final AStarSearch search;

	public Ashtar() {
		worldMap = WorldMap.build(getInputPath());
		search = new AStarSearch(worldMap);
	}

	private Path getInputPath() {
		try {
			return Paths.get(getClass().getResource(INPUT_FILE_NAME).toURI());
		} catch (URISyntaxException use) {
			throw new IllegalArgumentException("File not found", use);
		}
	}

	public void start() throws IOException {
		Route route = search.search();
	}

	public static void main(String[] args) throws IOException {
		new Ashtar().start();
	}

}

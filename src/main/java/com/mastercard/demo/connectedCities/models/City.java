package com.mastercard.demo.connectedCities.models;

import java.util.HashMap;
import java.util.Map;


public class City {
	private String name;
	private boolean visited;
	private Map<String, City> connections;

	
	public City( String name) {
		this.name = name;
		this.visited = false;
		connections = new HashMap<String, City>();
	}

	
	public String getName() {
		return this.name;
	}

	
	public void addConnection( String cityName,  City connection) {
		this.connections.put(cityName, connection);
	}


	public Map<String, City> getConnections() {
		return this.connections;
	}


	public boolean hasConnectionWith(String cityName) {
		if (connections != null && !connections.isEmpty()) {
			return connections.containsKey(cityName);
		}
		return false;
	}


	public boolean isVisited() {
		return this.visited;
	}

	
	public void visited() {
		this.visited = true;
	}

	


}

package com.mastercard.demo.connectedCities.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mastercard.demo.connectedCities.models.City;
import com.mastercard.demo.connectedCities.Controller.ConnectedCitiesContoller;
import com.mastercard.demo.connectedCities.constants.Constants;

/**
 * Builds Routes between cities.
 */
@Component
public class RoutesBuilder {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedCitiesContoller.class);

	/**
	 * Build Routes between cities.
	 * 
	 */
	public Map<String, City> buildRoutes(String origin, String destination, BufferedReader bufferedReader) {

		String line;
		final HashMap<String, City> citiesMap = new HashMap<String, City>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				String cities[] = line.trim().toLowerCase().replace(Constants.DELIMITER + " ", Constants.DELIMITER).split(Constants.DELIMITER);
				saveRoute(citiesMap, cities);
			}
		} catch (IOException e) {
		}
		return citiesMap;

	}

	
	/**
	 * Read Content from file
	 */
	public BufferedReader readFile() {
		
		/*
		 * try (Stream<String> stream =
		 * Files.lines(Paths.get("src/main/resources/city.txt"))) { } catch (IOException
		 * e) { LOGGER.error("No File found"); e.printStackTrace(); }
		 */
		

		ClassLoader classLoader = getClass().getClassLoader();
		FileReader reader;
		BufferedReader bufferedReader = null;

		URL resource = classLoader.getResource("city.txt");
		if (resource == null) {
			LOGGER.error("File doesn't exist");
		} else {
			File file = new File(resource.getFile());

			try {
				reader = new FileReader(file);

				bufferedReader = new BufferedReader(reader);
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return bufferedReader;

	}

	private void saveRoute(final Map<String, City> citiesMap, String[] cities) {
		// Process the request only if 2 cities are present per each line
		if (cities.length == 2) {

			List<City> citiesList = new ArrayList<>();
			City city;
			for (String cityName : cities) {
				if ((city = citiesMap.get(cityName)) == null) {
					city = new City(cityName);
					citiesMap.put(cityName, city);
				}
				citiesList.add(city);
			}
			if (!citiesList.get(0).hasConnectionWith(citiesList.get(1).getName())) {
				citiesList.get(0).addConnection(citiesList.get(1).getName(), citiesList.get(1));
			}
			if (!citiesList.get(1).hasConnectionWith(citiesList.get(0).getName())) {
				citiesList.get(1).addConnection(citiesList.get(0).getName(), citiesList.get(0));
			}
		}
	}

	/**
	 * Checks if there is a connection between origin and destination city
	 * 
	 */
	public String checkConnection(String origin, String destination, Map<String, City> citiesMap) {

		City originCity = citiesMap.get(origin);
		boolean result = search(originCity, destination);

		if (result)
			return Constants.CITIES_CONNECTED;
		else
			return Constants.CITIES_NOT_CONNECTED;

	}

	/**
	 * Search if city is present in the connections by looping through it
	 *
	 */
	private boolean search(City originCity, String destination) {
		boolean connected = false;
		if (originCity.getName().equals(destination)) {
			connected = true;
			return connected;
		}

		deleteRoutes(originCity);
		if (!originCity.isVisited()) {
			originCity.visited();
		}

		for (City city : originCity.getConnections().values()) {
			connected = search(city, destination);
			if(connected)
				break;
		}

		return connected;

	}

	/**
	 * Delete routes which are visited to prevent looping issue
	 * 
	 */
	private void deleteRoutes(City city) {
		for (City connectedCity : city.getConnections().values()) {
			connectedCity.getConnections().remove(city.getName());
		}
	}
}

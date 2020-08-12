package com.mastercard.demo.connectedCities.Controller;

import java.io.BufferedReader;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.demo.connectedCities.constants.Constants;
import com.mastercard.demo.connectedCities.models.City;
import com.mastercard.demo.connectedCities.services.RoutesBuilder;

@RestController
@Validated
public class ConnectedCitiesContoller {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedCitiesContoller.class);

	@Autowired
	private RoutesBuilder builder;

	@GetMapping("/connected")
	public String findConnectedCities(
			@RequestParam("origin") @NotBlank(message = Constants.REQUEST_PARAM_ORIGIN_VALIDATION_ERROR) String origin,
			@RequestParam("destination") @NotBlank(message = Constants.REQUEST_PARAM_DESTINATION_VALIDATION_ERROR) String destination) {

		LOGGER.info("Received request to find if cities are connected");

		origin = origin.toLowerCase();
		destination = destination.toLowerCase();

		BufferedReader bufferedReader = builder.readFile();
		Map<String, City> citiesMap = builder.buildRoutes(origin.toLowerCase(), destination.toLowerCase(),
				bufferedReader);

		// Check if the origin and destination cities are present in the Map before
		// processing
		if (citiesMap == null || citiesMap.isEmpty() || !citiesMap.containsKey(origin)
				|| !citiesMap.containsKey(destination))
			return Constants.CITIES_NOT_CONNECTED;

		String result = builder.checkConnection(origin, destination, citiesMap);

		return result;

	}

}

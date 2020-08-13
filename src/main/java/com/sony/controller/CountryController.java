package com.sony.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sony.entity.Country;
import com.sony.model.APIStatus;
import com.sony.model.CountryCodes;
import com.sony.model.CountryNames;
import com.sony.model.Greeting;
import com.sony.service.CountryService;

@RestController
public class CountryController {

	@Autowired
	private CountryService countryCodeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private RestTemplate restTemplate = new RestTemplate();
	private APIStatus apiStatus = new APIStatus();
	private ResponseEntity<String> response;
	private CountryCodes countryCodes;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/diag")
	public ResponseEntity<APIStatus> diag() {
		LOGGER.info("Checking API health..");
		try {
			response = restTemplate.getForEntity(new URI("http://localhost:8080/health"), String.class);
			if (null != response) {
				apiStatus.setStatusCode(response.getStatusCodeValue());
				apiStatus.setStatus(response.getStatusCode().OK.name());
			}else {
				LOGGER.info("rest call failed..");
			}
		} catch (RestClientException e) {
			LOGGER.error("rest call failed..");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			LOGGER.error("url is malformed..");
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(apiStatus, HttpStatus.OK);
	}

	@PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryCodes> convertCountryToCode(@RequestBody CountryNames countryNames) {
		LOGGER.info("adding Country details..");
		countryCodes = countryCodeService.convertCountryToCode(countryNames.getCountryNames());
		if (null != countryCodes) {
			return new ResponseEntity<CountryCodes>(countryCodes,HttpStatus.OK);
		}else {
			LOGGER.info("failed to parse country codes from provided country names");
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "/getCountryCodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCountryCodes() {
		LOGGER.info("Getting Country Code details for all countries..");
		return new ResponseEntity<Iterable<Country>>(countryCodeService.getCountryDetails(), HttpStatus.OK);
	}

	@PostMapping(value = "/addCountry", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCountry(@RequestBody Country country) {
		LOGGER.info("adding Country details..");
		countryCodeService.addCountry(country);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/updateCountry", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCountry(@RequestBody Country country) {
		LOGGER.info("updating Country details..");
		countryCodeService.updateCountry(country);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteCountry", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCountry(/* @RequestParam String countryCode, */@RequestBody Country country) {
		LOGGER.info("deleting Country details..");
		countryCodeService.deleteCountry(country);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

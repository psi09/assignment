package com.sony.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sony.entity.Country;
import com.sony.model.CountryCodes;
import com.sony.processor.CountryProcessor;
import com.sony.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private CountryProcessor countryProcessor;
	
	public Iterable<Country> getCountryDetails() {
		
		return countryRepository.findAll();
	}

	public Country addCountry(Country country) {
		return countryRepository.save(country);
	}

	public void deleteCountry(Country country) {
		countryRepository.delete(country);
	}

	public void updateCountry(Country country) {

		countryRepository.save(country);
	}

	public CountryCodes convertCountryToCode(List<String> countryNames) {

		return countryProcessor.processConversion(countryNames);
	}


}

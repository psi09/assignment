package com.sony.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sony.model.CountryCodes;

@Component
public class CountryProcessor {

	public CountryCodes processConversion(List<String> countryNames) {

		// TODO:: Logic for converting Country Names to Country Codes model goes here..
		return new CountryCodes();
	}

}

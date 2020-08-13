package com.sony.repository;

import org.springframework.data.repository.CrudRepository;

import com.sony.entity.Country;

public interface CountryRepository extends CrudRepository<Country, String>  {

}

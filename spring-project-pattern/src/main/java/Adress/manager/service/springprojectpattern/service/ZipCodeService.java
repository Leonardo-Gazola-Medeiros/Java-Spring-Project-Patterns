package Adress.manager.service.springprojectpattern.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import Adress.manager.service.springprojectpattern.model.Address;

@FeignClient(name = "ZipCode", url = "https://viacep.com.br/ws")
public interface ZipCodeService {

	@GetMapping("/{ZipcCode}/json/")
	Address searchZipCode(@PathVariable("ZipCode") String ZipCode);

}


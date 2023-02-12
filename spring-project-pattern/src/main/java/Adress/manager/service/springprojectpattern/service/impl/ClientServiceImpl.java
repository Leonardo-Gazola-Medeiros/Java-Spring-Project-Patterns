package Adress.manager.service.springprojectpattern.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Adress.manager.service.springprojectpattern.model.Client;
import Adress.manager.service.springprojectpattern.model.ClientRepository;
import Adress.manager.service.springprojectpattern.model.Address;
import Adress.manager.service.springprojectpattern.model.AddressRepository;
import Adress.manager.service.springprojectpattern.service.ClientService;
import Adress.manager.service.springprojectpattern.service.ZipCodeService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ZipCodeService zipCodeService;
	
    //Strategy: Implements the methods defined by the interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.
    //Facade: 

	@Override
	public Iterable<Client> searchAll() {
		// Search all Clients.
		return clientRepository.findAll();
	}

	@Override
	public Client searchById(Long id) {
		// Search Client by Id.
		Optional<Client> client = clientRepository.findById(id);
		return client.get();
	}

	@Override
	public void insert(Client client) {
		saveClientZip(client);
	}

	@Override
	public void update(Long id, Client client) {
		// Search Client by ID if it exists.
		Optional<Client> clientBd = clientRepository.findById(id);
		if (clientBd.isPresent()) {
			saveClientZip(client);
		}
	}

	@Override
	public void delete(Long id) {
		// Delete Client by ID
		clientRepository.deleteById(id);
	}

	private void saveClientZip(Client client) {
		// Verify if Client already exists by ZipCode.
		String zipCode = client.getAddress().getZipCode();
		Address address = AddressRepository.searchById(zipCode).orElseGet(() -> {
			// In case it doesn't exist, Integrate with ZipCode and return.
			Address newAddress = zipCodeService.searchZipCode(zipCode);
			addressRepository.save(newAddress);
			return newAddress;
		});
		client.setAddress(address);
		// Insert Client, vinculating it to the Address (new or existent).
		clientRepository.save(client);
	}

}

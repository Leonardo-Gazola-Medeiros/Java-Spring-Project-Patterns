package Adress.manager.service.springprojectpattern.service;

import Adress.manager.service.springprojectpattern.model.Client;

public interface ClientService {

    Iterable<Client> searchAll();
    
    Client searchById(Long id);
    
    void insert(Client client);
    
    void update(Long id, Client client);
    
    void delete(Long id);
    
}

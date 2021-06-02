package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Client;
import cs.vsu.hotel.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<Client> findAll() {
        return new ArrayList<>(clientRepository.findAll());
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public Client findByPassportId(Long passportId) {
        return clientRepository.findByPassportId(passportId).orElse(null);
    }

    public List<Client> findByPartOfName(String partOfName) {
        return new ArrayList<>(clientRepository.findByPartOfName(partOfName));
    }
}

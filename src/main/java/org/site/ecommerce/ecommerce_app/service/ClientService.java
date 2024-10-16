package org.site.ecommerce.ecommerce_app.service;

import org.site.ecommerce.ecommerce_app.entity.Client;
import org.site.ecommerce.ecommerce_app.entity.ClientEnregistre;
import org.site.ecommerce.ecommerce_app.entity.enumeration.Role;
import org.site.ecommerce.ecommerce_app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ClientEnregistre inscription(String prenom, String nom, String tel, String email, String password) {
        ClientEnregistre clientEnregistre = new ClientEnregistre();
        clientEnregistre.setPrenom(prenom);
        clientEnregistre.setNom(nom);
        clientEnregistre.setTel(tel);
        clientEnregistre.setEmail(email);
        clientEnregistre.setRole(Role.CLIENT);
        clientEnregistre.setPassword(passwordEncoder.encode(password));
        return clientRepository.save(clientEnregistre);
    }

    //methode pour obetenir un client avec son id
    public Optional<Client> findClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public ClientEnregistre saveClientEnregistre(ClientEnregistre client) {
        if (!passwordEncoder.matches(client.getPassword(), "{bcrypt}")) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
        }
        return clientRepository.save(client);
    }
    public ClientEnregistre updateClientRole(ClientEnregistre client) {
        return clientRepository.save(client); // Sauvegarde directe du rôle sans ré-encoder le mot de passe
    }

}

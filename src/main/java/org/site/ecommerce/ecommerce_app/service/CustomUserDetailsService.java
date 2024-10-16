package org.site.ecommerce.ecommerce_app.service;

import org.site.ecommerce.ecommerce_app.entity.ClientEnregistre;
import org.site.ecommerce.ecommerce_app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClientEnregistre clientEnregistre = (ClientEnregistre) clientRepository.findByEmail(email);
        if (clientEnregistre == null) {
            throw new UsernameNotFoundException("utilisateur non trouve");
        }

        return User.builder()
                .username(clientEnregistre.getEmail())
                .password(clientEnregistre.getPassword())
                .roles(clientEnregistre.getRole().name())
                .build();
    }
}

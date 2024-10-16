package org.site.ecommerce.ecommerce_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.site.ecommerce.ecommerce_app.dto.ConnexionDTO;
import org.site.ecommerce.ecommerce_app.dto.InscriptionDTO;
import org.site.ecommerce.ecommerce_app.entity.Client;
import org.site.ecommerce.ecommerce_app.entity.ClientEnregistre;
import org.site.ecommerce.ecommerce_app.entity.enumeration.Role;
import org.site.ecommerce.ecommerce_app.repository.ClientRepository;
import org.site.ecommerce.ecommerce_app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ecommerce")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@RequestBody InscriptionDTO inscriptionDTO) {
        if (clientRepository.findByEmail(inscriptionDTO.email())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email deja existant");
        }

        ClientEnregistre clientEnregistre = clientService.inscription(
                inscriptionDTO.prenom(),
                inscriptionDTO.nom(),
                inscriptionDTO.tel(),
                inscriptionDTO.email(),
                inscriptionDTO.password()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(clientEnregistre);
    }



    @PutMapping("/promouvoir/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ToAdmin(@PathVariable Long clientId) {
        Optional<Client> clientOpt = clientService.findClientById(clientId);
        if (clientOpt.isPresent() && clientOpt.get() instanceof ClientEnregistre client) {
            client.setRole(Role.ADMIN); // Utilisez l'enum directement
            clientService.updateClientRole(client); // Sauvegarde directe sans ré-encoder le mot de passe
            return ResponseEntity.status(HttpStatus.OK).body("Le client a été promu au rôle ADMIN");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé ou type incorrect");
    }




    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody ConnexionDTO connexionDTO, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(connexionDTO.email(), connexionDTO.password());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            return ResponseEntity.status(HttpStatus.OK).body("Connexion reussie");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }
    }



    @PostMapping("/deconnexion")
    public ResponseEntity<?> deconnexion(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return ResponseEntity.status(HttpStatus.OK).body("Deconnexion reussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas connecte");
    }
}

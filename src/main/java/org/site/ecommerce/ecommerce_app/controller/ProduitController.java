package org.site.ecommerce.ecommerce_app.controller;

import org.site.ecommerce.ecommerce_app.entity.Produit;
import org.site.ecommerce.ecommerce_app.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    public static String uploadDirectory = "%s\\src\\main\\resources\\images\\".formatted(System.getProperty("user.dir"));

    @PostMapping("/admin/dashboard/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produit> creerProduit(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("prix") double prix,
            @RequestParam("stock") int stock,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            String fileName= imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDirectory + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path,imageFile.getBytes());

            //generer l'URL pour pour acceder a l'image
            assert fileName != null;
            String imageUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(fileName).toUriString();

            //creer le produit avec l'url de l'image
            Produit produit = new Produit(nom,description,imageUrl,prix,stock);
            Produit produitSa= produitService.create(produit);
            return ResponseEntity.status(HttpStatus.CREATED).body(produitSa);// 201 created

        } catch (IOException e) {
            return    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/produits")
    public ResponseEntity<List<Produit>> getAllProduits() {
        List<Produit> produits = produitService.findAll();
        return ResponseEntity.ok().body(produits); //200 ok
    }


    @DeleteMapping("/admin/dashboard/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> SupprimerProduit(@PathVariable Long id) {
        if (produitService.findById(id) !=null) {
            produitService.delete(id);
            return ResponseEntity.noContent().build();// no content 204
        }else {
            return ResponseEntity.notFound().build(); // not found 404
        }
    }


    @PutMapping("/admin/dashboard/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produit> modifierProduit(@PathVariable Long id,
                                                   @RequestParam("nom") String nom,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("prix") double prix,
                                                   @RequestParam("stock") int stock,
                                                   @RequestParam(value = "image", required = false)  MultipartFile imageFile) {
        if (produitService.findById(id) ==null) {
            return ResponseEntity.notFound().build(); //404 not found
        }
        Produit produit= produitService.findById(id);
        produit.setNom(nom);
        produit.setDescription(description);
        produit.setPrix(prix);
        produit.setStock(stock);

        //verifie si une nouvelle image a ete fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            //supprime l'ancienne image si elle existe
            if (produit.getImageUrl()!=null){
                String ancienPathImage= produit.getImageUrl().replace(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/images/").toUriString(), ""
                );
                File ancienneImage = new File(uploadDirectory + ancienPathImage);
                if (ancienneImage.exists()) {
                    ancienneImage.delete();
                }
            }
            try {
                //enregistre la nouvelle image
                String fileName= imageFile.getOriginalFilename();
                Path path = Paths.get(uploadDirectory + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path,imageFile.getBytes());

                assert fileName != null;
                String imageUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(fileName).toUriString();
                produit.setImageUrl(imageUrl);// mis a jour de l'image

            } catch (IOException e) {
                return    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        //sauvegarde les changements du produit
        Produit produitSa= produitService.create(produit);
        return ResponseEntity.ok().body(produitSa);
    }


    @GetMapping("/produits/get/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        if (produitService.findById(id) !=null) {
            return ResponseEntity.ok().body(produitService.findById(id));
        }else {
            return ResponseEntity.notFound().build(); //404 not found
        }
    }
}

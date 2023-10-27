package fr.cytech.recette;

import fr.cytech.ingredient.Ingredient;
import fr.cytech.ingredient.Ingredient_Repository;
import fr.cytech.ingredientRecette.IngredientRecette;
import fr.cytech.ingredientRecette.IngredientRecette_Repository;
import fr.cytech.utilisateur.*;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Controlleur_Recette {

    @Autowired
    private RecetteRepository recetteRepository;

    @Autowired
    private Ingredient_Repository ingredientRepository;

    @Autowired
    private IngredientRecette_Repository ingredientRecetteRepository;
    
    @Autowired
    private Utilisateur_Repository utilisateurRepository;



    @GetMapping(path="/ajouterRecette")
    public String ajouterRecetteForm(Model model, HttpSession session) {
        if( session.getAttribute("id_utilisateur") != null ) {
            model.addAttribute("recette", new Recette());
            List<Ingredient> ingredients = ingredientRepository.findAll();
            model.addAttribute("ingredients", ingredients);
            return "ajouterRecette";
        } else {
            model.addAttribute("erreur", "Merci de se connecter pour ajouter une recette");
            return "connexion";
        }
    }


    @PostMapping("/ajouter_recette")
    public String ajouterRecetteSubmit(@ModelAttribute Recette recette,
                                       @RequestParam(required = false) String[] nom_ingredient,
                                       @RequestParam(required = false) Double[] quantite,
                                       @RequestParam(required = false) String[] unite,
                                       @RequestParam(required = false) Double[] quantiteExistant,
                                       @RequestParam(required = false) String[] uniteExistant,
                                       @RequestParam(required = false) List<Long> ingredientsExistantsIds,

                                       HttpSession session) {
        System.out.println("Les id : " + ingredientsExistantsIds);
        System.out.println("Quantite: " + Arrays.toString(quantiteExistant));
        System.out.println("Unite: " + Arrays.toString(uniteExistant));

        if(uniteExistant == null) {
            System.out.println("Pas de quantité");
        }
        // Récupérer l'utilisateur à partir de la session
        Long utilisateurId = (Long) session.getAttribute("id_utilisateur");
        if(utilisateurId == null) {
            return "redirect:/connexion";
        }
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(utilisateurId);
        if(!optionalUtilisateur.isPresent()) {
            return "redirect:/connexion"; 
        }
        Utilisateur utilisateur = optionalUtilisateur.get();
        if(utilisateur == null) {
            return "redirect:/connexion";
        }
        recette.setAuteur(utilisateur); 
        // Enregistrement de la recette
        Recette savedRecette = recetteRepository.save(recette);

        // Traitement des ingrédients existants
        if (ingredientsExistantsIds != null && quantiteExistant != null && uniteExistant != null) {
            for (int i = 0; i < ingredientsExistantsIds.size(); i++) {
                Long ingredientId = ingredientsExistantsIds.get(i);
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
                if (optionalIngredient.isPresent()) {
                    Ingredient ingredient = optionalIngredient.get();
                    Double ingredientQuantite = quantiteExistant[i];
                    String ingredientUnite = uniteExistant[i];
                    IngredientRecette ingredientRecette = new IngredientRecette(ingredient, savedRecette, ingredientQuantite, ingredientUnite);
                    ingredientRecetteRepository.save(ingredientRecette);
                }
            }
        }
        
        // Traitement des nouveaux ingrédients
        if (nom_ingredient != null) {
            for (int i = 0; i < nom_ingredient.length; i++) {
                String ingredientNom = nom_ingredient[i];
                Ingredient ingredient = ingredientRepository.findByNomIngredient(ingredientNom)
                        .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredientNom)));
                Double ingredientQuantite = quantite != null && quantite.length > i ? quantite[i] : null;
                String ingredientUnite = unite != null && unite.length > i ? unite[i] : null;
                IngredientRecette ingredientRecette = new IngredientRecette(ingredient, savedRecette, ingredientQuantite, ingredientUnite);
                ingredientRecetteRepository.save(ingredientRecette);
            }
        }

        return "redirect:/index";
    }
}

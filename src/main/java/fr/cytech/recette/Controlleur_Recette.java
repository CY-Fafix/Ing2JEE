package fr.cytech.recette;

import fr.cytech.ingredient.Ingredient;
import fr.cytech.ingredient.Ingredient_Repository;
import fr.cytech.ingredientRecette.IngredientRecette;
import fr.cytech.ingredientRecette.IngredientRecette_Repository;
import fr.cytech.utilisateur.*;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
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
        Long utilisateurId = (Long) session.getAttribute("id_utilisateur");;
        //Si pas connecté on le redirige
        if(utilisateurId == null) {
            return "redirect:/connexion";
        }
        //on cherche l'utilisateur dans la bDD
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(utilisateurId);
        // Si il existe pas on le redirige 
        if(!optionalUtilisateur.isPresent()) {
            return "redirect:/connexion"; 
        }
        Utilisateur utilisateur = optionalUtilisateur.get();
        recette.setAuteur(utilisateur); 
        // Enregistrement de la recette
        Recette savedRecette = recetteRepository.save(recette);

        // Traitement des ingrédients existants
        if (ingredientsExistantsIds != null && quantiteExistant != null && uniteExistant != null) {
        	//On parcours les ingrédients
            for (int i = 0; i < ingredientsExistantsIds.size(); i++) {
            	//on récupère l'id
                Long ingredientId = ingredientsExistantsIds.get(i);
                //On recupere l'ingredient dans la BDD
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
                //Si l'ingrédient est dans la BDD
                if (optionalIngredient.isPresent()) {
                	//on recup l'ingrédient
                    Ingredient ingredient = optionalIngredient.get();
                    //on récupère la quantité et l'unité
                    Double ingredientQuantite = quantiteExistant[i];
                    String ingredientUnite = uniteExistant[i];
                    //On ajoute tout à la BDD
                    IngredientRecette ingredientRecette = new IngredientRecette(ingredient, savedRecette, ingredientQuantite, ingredientUnite);
                    ingredientRecetteRepository.save(ingredientRecette);
                }
            }
        }
        
        // Traitement des nouveaux ingrédients
     // Traitement des nouveaux ingrédients
        if (nom_ingredient != null) {
            for (int i = 0; i < nom_ingredient.length; i++) {
                String ingredientNom = nom_ingredient[i];
                Ingredient ingredient = ingredientRepository.findByNomIngredient(ingredientNom)
                        .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredientNom)));
                
                Double ingredientQuantite = null;
                // On check si il y a bien une quantité et on dépasse pas le tableau
                if (quantite != null && quantite.length > i) {
                    ingredientQuantite = quantite[i];
                }
                
                // On check si il y a bien une unité et on dépasse pas le tableua
                String ingredientUnite = null;
                if (unite != null && unite.length > i) {
                    ingredientUnite = unite[i];
                }
                
                // Création et sauvegarde de l'ingrédient de la recette
                IngredientRecette ingredientRecette = new IngredientRecette(ingredient, savedRecette, ingredientQuantite, ingredientUnite);
                ingredientRecetteRepository.save(ingredientRecette);
            }
        }


        return "redirect:/index";
    }
    
    @GetMapping(path="/vosRecettes")
    public String vosRecettes(Model model, HttpSession session) {
        if( session.getAttribute("id_utilisateur") != null ) {
    			long id=(long) session.getAttribute("id_utilisateur");
    			Utilisateur utilisateur=utilisateurRepository.findById(id);
    			List<Recette> liste_recettes=recetteRepository.findByAuteur(utilisateur);
    			System.out.println(liste_recettes);
    			model.addAttribute("recettes", liste_recettes);
            return "vosRecettes";
        } else {
            model.addAttribute("erreur", "Merci de se connecter pour accèder à cette page");
            return "connexion";
        }
        
        
    }
    
    @GetMapping(path="/modifierRecette/{id}")
    public String modifierRecette(@PathVariable("id") Long recetteId, Model model, HttpSession session) {
        if(session.getAttribute("id_utilisateur") != null) {
            // On récupere la recette par l'ID
            Optional<Recette> recette = recetteRepository.findById(recetteId);
            
            // Vérifiez si la recette existe
            if(recette.isPresent()) {
                // Récupérez les ingrédients de la recette
                List<IngredientRecette> ingredientsRecette = ingredientRecetteRepository.findByRecette(recette.get());
                
                // Ajoutez la recette et ses ingrédients au modèle
                model.addAttribute("recette", recette.get());
                model.addAttribute("ingredientsRecette", ingredientsRecette);
                
                // Retournez la vue pour modifier la recette
                return "modifierRecette";
            } else {
                return "redirect:/vosRecettes";
            }
        } else {
            model.addAttribute("erreur", "Merci de se connecter pour accéder à cette page");
            return "connexion";
        }
    }
    
    @PostMapping("/modifierRecette/{id}")
    public String modifierRecetteSubmit(@PathVariable("id") Long recetteId,
                                        @ModelAttribute Recette recette,
                                        @RequestParam(required = false) List<Long> ingredientRecetteIds,
                                        @RequestParam(required = false) List<Double> quantites,
                                        @RequestParam(required = false) List<String> unites,
                                        HttpSession session) {
        // Vérifiez si l'utilisateur est connecté
        if (session.getAttribute("id_utilisateur") == null) {
            return "redirect:/connexion";
        }

        Optional<Recette> recetteExistante = recetteRepository.findById(recetteId);

        // On met a jour le titre/description de la recette
        Recette recetteToUpdate = recetteExistante.get();
        recetteToUpdate.setTitre(recette.getTitre());
        recetteToUpdate.setDescription(recette.getDescription());

        // On itère sur les ingrédients et on enregistre les modif
        if (ingredientRecetteIds != null && quantites != null && unites != null) {
            for (int i = 0; i < ingredientRecetteIds.size(); i++) {
                Long ingredientRecetteId = ingredientRecetteIds.get(i);
                Optional<IngredientRecette> optionalIngredientRecette = ingredientRecetteRepository.findById(ingredientRecetteId);
                if (optionalIngredientRecette.isPresent()) {
                    IngredientRecette ingredientRecetteToUpdate = optionalIngredientRecette.get();
                    ingredientRecetteToUpdate.setQuantite(quantites.get(i));
                    ingredientRecetteToUpdate.setUnite(unites.get(i));
                    ingredientRecetteRepository.save(ingredientRecetteToUpdate);
                }
            }
        }
        recetteRepository.save(recetteToUpdate);
        return "redirect:/vosRecettes";
    }


    @GetMapping("/recherche")
    public String recherche() {
    	
    	return "index.html";
    }
}

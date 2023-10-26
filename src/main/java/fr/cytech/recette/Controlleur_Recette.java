package fr.cytech.recette;

import fr.cytech.ingredient.Ingredient;
import fr.cytech.ingredient.Ingredient_Repository;
import fr.cytech.ingredientRecette.IngredientRecette;
import fr.cytech.ingredientRecette.IngredientRecette_Repository;
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


    @GetMapping(path="/ajouterRecette")
    public String ajouterRecetteForm(Model model,HttpSession session) {
    	if( session.getAttribute("id_utilisateur") != null ) {
    		model.addAttribute("recette", new Recette());
            // je dois changer d'autres données genre la liste d'ingrédients
            return "ajouterRecette";  // le nom du fichier HTML de la page de création
    	} else {
			 model.addAttribute("erreur", "Merci de se connecter pour ajouter une recette");
    		return "connexion";
    	}
    }

    @PostMapping("/ajouter_recette")
    public String ajouterRecetteSubmit(@ModelAttribute Recette recette,
                                       @RequestParam(required = false) String[] nom_ingredient,
                                       @RequestParam(required = false) Double[] quantite,
                                       @RequestParam(required = false) String[] unite) {
        System.out.println("Soumission du formulaire de recette");
        // Enregistrement de la recette
        Recette savedRecette = recetteRepository.save(recette);

        // Traitement des ingrédients
        for (int i = 0; i < nom_ingredient.length; i++) {
            String ingredientNom = nom_ingredient[i];
            Ingredient ingredient = ingredientRepository.findByNomIngredient(ingredientNom)
                    .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredientNom)));

            IngredientRecette ingredientRecette = new IngredientRecette(ingredient, savedRecette, quantite[i], unite[i]);
            ingredientRecetteRepository.save(ingredientRecette);
        }

        return "redirect:/index";
    }
}

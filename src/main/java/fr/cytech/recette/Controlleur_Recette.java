package fr.cytech.recette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class Controlleur_Recette {
	
    @Autowired
    private RecetteRepository recetteRepository;
    // Faut ajouter d'autres repo genre pour les ingrédients
    
    @GetMapping(path="/ajouterRecette")
    public String ajouterRecetteForm(Model model) {
        model.addAttribute("recette", new Recette());
        // je dois changer d'autres données genre la liste d'ingrédients
        return "ajouterRecette";  // le nom du fichier HTML de la page de création
    }
    
    //On recup de ajouterRecette.html
    @PostMapping(path="/ajouter_recette")
    public String ajouterRecetteSubmit(@ModelAttribute Recette recette) {
    	//Est-ce qu'on doit utiliser le controlleur de ingrédient ????
        recetteRepository.save(recette);  // sauvegarde la nouvelle recette dans la BDD !
        return "redirect:/";  // on redirige vers la page d'accueil
    }

    // Ajoutez la modification ou la suppression de recettes

}

package fr.cytech.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Controlleur_Utilisateur {
	
	@Autowired
	Utilisateur_Repository utilisateur_repository;
	
	@GetMapping(path="index")
	public String redirigerIndex() {
		return "index";
	}
	
	@GetMapping(path="/ajouterUtilisateur")
	public String ajouterUtilisateur() {
		return "ajouterUtilisateur";
	}
	
	@PostMapping(path="ajouter_Utilisateur")
	public String ajouter_Utilisateur(@ModelAttribute Utilisateur utilisateur) {
		
		utilisateur_repository.save(utilisateur);
		return "redirect:/index";
		
	}
	
}

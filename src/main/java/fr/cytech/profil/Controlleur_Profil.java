package fr.cytech.profil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.utilisateur.Utilisateur;

@Controller
public class Controlleur_Profil {
	@Autowired
	Profil_Repository profil_repository;
	
	@GetMapping(path="/creerProfil")
	public String creerProfil(Model model) {
		//TODO si premiere connexion rediriger vers creationProfil
		return "creerProfil"; 		
	}
	
	@GetMapping(path="/GererProfil")
	public String GererProfil(Model model) {
		Optional<Utilisateur> profil;
		//profil=profil_repository.FindByUtilisateur(/*email de l'utilisateur connecté qu'on récupère de la session*/"test@test.com");
		
		
		return "gererProfil"; 		
	}
	
	
	@PostMapping(path="ajouter_Profil")
	public String ajouter_Utilisateur(@ModelAttribute Profil profil) {
		
		profil_repository.save(profil);
		return "redirect:/index";
		
	}
}

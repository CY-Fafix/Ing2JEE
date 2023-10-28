package fr.cytech.profil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.utilisateur.Utilisateur;
import fr.cytech.utilisateur.Utilisateur_Repository;
import jakarta.servlet.http.HttpSession;

@Controller
public class Controlleur_Profil {
	@Autowired
	Profil_Repository profil_repository;
	
	@Autowired
	Utilisateur_Repository utilisateur_repository;
	
	
	@GetMapping(path="/creerProfil")
	public String creerProfil(Model model,HttpSession session ) {
		if (session.getAttribute("id_utilisateur") != null ) {
			return "creerProfil"; 		
		}
		model.addAttribute("erreur", "Merci de vous connecter pour créer un profil");
		return "connexion"; 		
	}
	
	@GetMapping(path="/Profil")
	public String GererProfil(Model model, HttpSession session) {
		long id=(long) session.getAttribute("id_utilisateur");
		Utilisateur utilisateur=utilisateur_repository.findById(id);
		Profil profil=profil_repository.findByUtilisateur(utilisateur);
		
		if(profil==null) {
			return "creerProfil";
		}else {
			return "gererProfil";
		}		
	}
	
	
	
	@PostMapping(path="ajouter_Profil")
	public String ajouter_Utilisateur(@ModelAttribute Profil profil,HttpSession session) {
		long id=(long)session.getAttribute("id_utilisateur");
		profil.setUtilisateur(utilisateur_repository.findById(id));
		profil_repository.save(profil);
		return "redirect:/index";
		
	}
}

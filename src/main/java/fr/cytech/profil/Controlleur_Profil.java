package fr.cytech.profil;

import java.sql.Date;
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
		try {
			long id=(long) session.getAttribute("id_utilisateur");
			Utilisateur utilisateur=utilisateur_repository.findById(id);
			Profil profil=profil_repository.findByUtilisateur(utilisateur);
			model.addAttribute("profil",profil);
			
			if(profil==null) {
				return "creerProfil";
			}else {
				return "gererProfil";
			}		
		
		}catch (java.lang.NullPointerException e) {
			//cas ou l'utilisateur n'est pas connecté
			model.addAttribute("erreur", "Merci de vous connecter pour acceder à cette page");
			return "connexion"; 		
		}
		
		}
	
	@GetMapping(path="/editerProfil")
	public String Rediriger_EditerProfil(Model model, HttpSession session) {
		
		if(session.getAttribute("id_utilisateur")!=null) {
			long id=(long)session.getAttribute("id_utilisateur");
			Utilisateur utilisateur=utilisateur_repository.findById(id);
			Profil profil=profil_repository.findByUtilisateur(utilisateur);
			model.addAttribute("AncienProfil", profil);
			return "editerProfil";
		}else {
			model.addAttribute("erreur", "Merci de vous connecter pour acceder à cette page");
			return "connexion"; 	
		}
		
	}
	
	
	
	@PostMapping(path="ajouter_Profil")
	public String ajouter_Utilisateur(@ModelAttribute Profil profil,HttpSession session) {
		long id=(long)session.getAttribute("id_utilisateur");
		profil.setUtilisateur(utilisateur_repository.findById(id));
		profil_repository.save(profil);
		return "redirect:/index";
		
	}
	
	@PostMapping(path="Modifier_Profil")
	public String Modifier_Profil(Model model,@ModelAttribute Profil profil,HttpSession session) {
		try {
			long id=(long)session.getAttribute("id_utilisateur");
			Utilisateur utilisateur=utilisateur_repository.findById(id);
			Profil profilExistant=profil_repository.findByUtilisateur(utilisateur);
			boolean modifierdate=true;
			profil.getDateDeNaissance();

			if(profil.getBio()!=null) {
				profilExistant.setBio(profil.getBio());
			}
			if(modifierdate) {
				profilExistant.setDateDeNaissance(profil.getDateDeNaissance());
			}
			profil_repository.save(profilExistant);
			return "index";

		}catch (java.lang.NullPointerException e) {
			//cas ou l'utilisateur n'est pas connecté
			model.addAttribute("erreur", "Merci de vous connecter pour acceder à cette page");
			return "connexion"; 	
		}
		
		
	}
	
	
}

package fr.cytech.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

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
	public String ajouter_Utilisateur(@ModelAttribute Utilisateur utilisateur, HttpSession session) {
		utilisateur_repository.save(utilisateur);
	 	session.setAttribute("id_utilisateur", utilisateur_repository.findByMail(utilisateur.getMail()).getId());
		return "redirect:/index";
		
	}
	
	@GetMapping(path="connexion")
	public String seConnecter() {
		return "connexion";
	}
	
	@PostMapping(path="connexion")
	public String connexion(@ModelAttribute Utilisateur utilisateur,HttpSession session, Model model) {
		if(utilisateur_repository.findByMail(utilisateur.getMail()) == null) {
			 model.addAttribute("erreur", "Adresse e-mail incorrecte.");
			 return "connexion";
		 }else if(utilisateur.getMdp().equals(utilisateur_repository.findByMail(utilisateur.getMail()).getMdp())) {
			 	session.setAttribute("id_utilisateur", utilisateur_repository.findByMail(utilisateur.getMail()).getId());
			 	return "redirect:/index";
		 }else{
			 model.addAttribute("erreur", "Mot de passe incorrect.");
			 return "connexion";
		 }
	}
	
	@GetMapping(path="deconnexion")
	public String deconnexion(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
}

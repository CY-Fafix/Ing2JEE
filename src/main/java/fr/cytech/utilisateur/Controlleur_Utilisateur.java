package fr.cytech.utilisateur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.recette.Recette;
import fr.cytech.recette.RecetteRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class Controlleur_Utilisateur {
	
	@Autowired
	Utilisateur_Repository utilisateur_repository;
	
    @Autowired
    RecetteRepository recetteRepository;	
	
    @GetMapping("/")
    public String Index(Model model) {
    	List<Recette> recettes = recetteRepository.findTop3ByOrderByIdDesc();
    	if( recettes.size() >= 3 ) {
    		model.addAttribute("recetteRecente",recettes.subList(0,3));
    	}else {
    		model.addAttribute("recetteRecente",recettes);
    	}
		return "index";
    }
	
	@GetMapping(path="index")
	public String redirigerIndex() {
		return "redirect:/";
	}
	
	@GetMapping(path="/ajouterUtilisateur")
	public String ajouterUtilisateur(HttpSession session) {
		if( session.getAttribute("id_utilisateur") != null ) {
			return "index";
		}
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

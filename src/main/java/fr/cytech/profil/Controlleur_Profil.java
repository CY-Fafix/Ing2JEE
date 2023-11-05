package fr.cytech.profil;



import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
			model.addAttribute("utilisateurConnecte",utilisateur);
			
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
			model.addAttribute("utilisateurConnecte",utilisateur);
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
	public String Modifier_Profil(Model model,@RequestParam("bio") String bio,@RequestParam("dateDeNaissance") String str_date,@RequestParam("nom") String nom, @RequestParam("email") String email, @RequestParam("mdp") String mdp, HttpSession session) {
		try {
			Profil profil=new Profil();
			
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date javaUtilDate = sdf.parse(str_date);
				java.sql.Date dateNaissance = new java.sql.Date(javaUtilDate.getTime());
				profil.setDateDeNaissance(dateNaissance);
								
				
			}catch (Exception e) {
				java.sql.Date dateNaissance=null;
				profil.setDateDeNaissance(dateNaissance);				
			}
			profil.setBio(bio);
			
			
			
			long id=(long)session.getAttribute("id_utilisateur");
			Utilisateur utilisateur=utilisateur_repository.findById(id);
			Profil profilExistant=profil_repository.findByUtilisateur(utilisateur);

			if(profil.getBio()!=null) {
				profilExistant.setBio(profil.getBio());
			}
			
			if(nom!=null) {
				utilisateur.setNom(nom);
			}
			if(email!=null) {
				utilisateur.setMail(email);
			}
			if(mdp!=null) {
				utilisateur.setMdp(mdp);
			}
			utilisateur_repository.save(utilisateur);
			profil_repository.save(profilExistant);
			return "index";

		}catch (java.lang.NullPointerException e) {
			//cas ou l'utilisateur n'est pas connecté
			model.addAttribute("erreur", "Merci de vous connecter pour acceder à cette page");
			return "connexion"; 	
		}
		
		
	}
	
	
	
}

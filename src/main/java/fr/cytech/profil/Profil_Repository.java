package fr.cytech.profil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import fr.cytech.utilisateur.Utilisateur;


public interface Profil_Repository extends JpaRepository<Profil, Long>{
	//Optional<Profil> FindByUtilisateur(String email);
	Profil findByUtilisateur(Utilisateur utilisateur);

}

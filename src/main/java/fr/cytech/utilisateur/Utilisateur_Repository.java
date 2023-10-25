package fr.cytech.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Utilisateur_Repository  extends JpaRepository<Utilisateur, Long> {
	
	Utilisateur findByMail(String mail);
	Utilisateur findById(long id);
	

}

package fr.cytech.recette;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.cytech.utilisateur.Utilisateur;

public interface RecetteRepository extends JpaRepository<Recette, Long> {
	List<Recette> findByAuteur(Utilisateur auteur);
}
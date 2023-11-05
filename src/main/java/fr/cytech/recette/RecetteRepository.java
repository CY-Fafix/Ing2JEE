package fr.cytech.recette;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.cytech.ingredientRecette.IngredientRecette;
import fr.cytech.utilisateur.Utilisateur;

public interface RecetteRepository extends JpaRepository<Recette, Long> {
	Optional<Recette> findById(long id);
	List<Recette> findByTitre(String titre);
	List<Recette> findByTitreContainingIgnoreCase(String titre);
	Optional<Recette> findByIngredientRecettes(IngredientRecette ingredientRecettes);
	List<Recette> findByAuteur(Utilisateur auteur);
	@Query("SELECT r FROM Recette r ORDER BY r.id DESC")
    List<Recette> findTop3ByOrderByIdDesc();
}
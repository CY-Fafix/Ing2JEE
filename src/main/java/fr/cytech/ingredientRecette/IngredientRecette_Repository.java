package fr.cytech.ingredientRecette;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRecette_Repository extends JpaRepository<IngredientRecette, Long> {
	List<IngredientRecette> findByIngredientNomIngredient(String nomIngredient);
    List<IngredientRecette> findByRecetteTitre(String titreRecette);
}

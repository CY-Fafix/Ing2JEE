package fr.cytech.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Ingredient_Repository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByNomIngredient(String name);
    Optional<Ingredient> findByNomIngredientIgnoreCase(String name);

}

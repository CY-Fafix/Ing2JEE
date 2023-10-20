package fr.cytech.ingredient;

import java.util.HashSet;
import java.util.Set;

import fr.cytech.ingredientRecette.IngredientRecette;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nom;
	
	@OneToMany
	private Set<IngredientRecette> ingredientRecettes = new HashSet<>();

	
	
	// Constructeurs
    public Ingredient() {}

    public Ingredient(String nom) {
        this.nom = nom;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<IngredientRecette> getIngredientRecettes() {
		return ingredientRecettes;
	}

	public void setIngredientRecettes(Set<IngredientRecette> ingredientRecettes) {
		this.ingredientRecettes = ingredientRecettes;
	}

}

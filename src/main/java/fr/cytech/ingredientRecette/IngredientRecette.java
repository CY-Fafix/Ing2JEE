package fr.cytech.ingredientRecette;

import fr.cytech.ingredient.Ingredient;
import fr.cytech.recette.Recette;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class IngredientRecette {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private Recette recette;

    private Double quantite;

    private String unite;
    
    public IngredientRecette() {}

    public IngredientRecette(Ingredient ingredient, Recette recette, Double quantite, String unite) {
        this.ingredient = ingredient;
        this.recette = recette;
        this.quantite = quantite;
        this.unite = unite;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}

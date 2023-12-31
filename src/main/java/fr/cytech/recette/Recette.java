package fr.cytech.recette;

import java.util.HashSet;

import java.util.Set;

import fr.cytech.ingredientRecette.IngredientRecette;
import fr.cytech.utilisateur.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recette {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String titre;
	@Column(length = 2000)
	private String description;
    
    @ManyToOne
	private Utilisateur auteur;
	
    @OneToMany(mappedBy = "recette")
    private Set<IngredientRecette> ingredientRecettes = new HashSet<>();
    
    public Recette() {}

    public Recette(String titre, String description, Utilisateur auteur) {
        this.setTitre(titre);
        this.setDescription(description);
        this.auteur = auteur;
    }
    
	

	public long getId() {
		return id;
	}
	
	public Utilisateur getAuteur() {
		return auteur;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<IngredientRecette> getIngredientRecettes() {
		return ingredientRecettes;
	}

	public void setIngredientRecettes(Set<IngredientRecette> ingredientRecettes) {
		this.ingredientRecettes = ingredientRecettes;
	}
	
	public void setAuteur(Utilisateur auteur) {
	    this.auteur = auteur;
	}
	

}

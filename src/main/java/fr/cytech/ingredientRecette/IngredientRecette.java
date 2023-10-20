package fr.cytech.ingredientRecette;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;

@Entity
public class ingredientRecette {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//TODO completer tous les attributs
}

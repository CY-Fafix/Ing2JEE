package fr.cytech.recette;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetteRepository extends JpaRepository<Recette, Long> {
    // Ajoutez ici d'autres méthodes spécifiques à vos besoins, si nécessaire.
    // Par exemple, vous pourriez vouloir rechercher des recettes par titre, etc.
    
    // Exemple: List<Recette> findByTitreContaining(String titre);
}
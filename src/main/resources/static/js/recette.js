document.addEventListener('DOMContentLoaded', function() {
    var ingredientsList = document.getElementById('ingredientsList');
    var ajouterIngredientBtn = document.getElementById('ajouterIngredient');
    
    ajouterIngredientBtn.addEventListener('click', function() {
        // On récupère le dernier groupe d'ingrédients
        var dernierIngredient = ingredientsList.lastElementChild;
        
        // On clone le dernier groupe d'ingrédients
        var nouvelIngredient = dernierIngredient.cloneNode(true);

        // On met à jour les noms pour les rendre uniques et interprétables comme des tableaux
        var inputs = nouvelIngredient.querySelectorAll('input, select');
        inputs.forEach(function(input) {
            if(input.name) {
                input.name = input.name.replace(/\[\d+\]/, '[' + (ingredientsList.childElementCount - 1) + ']');
            }
            input.value = ''; // Réinitialise la valeur
        });

        // Ajoute le nouveau groupe d'ingrédients à la liste
        ingredientsList.appendChild(nouvelIngredient);
    });
});

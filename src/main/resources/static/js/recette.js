document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('ajouterIngredient').addEventListener('click', function() {
        // ON recupere la div du form des ingrédients
        var ingredientsList = document.getElementById('ingredientsList');

        // On clone le premier groupe d'ingrédients
        var newIngredientGroup = ingredientsList.querySelector('.ingredient-group').cloneNode(true);

        // On remt à zéro les valeurs des champs pour lesn nouveaux ingrédients
        var inputs = newIngredientGroup.querySelectorAll('input, select');
        for(var i = 0; i < inputs.length; i++) {
            inputs[i].value = '';
        }

        // On ajoute le nouveau groupe d'ingrédients à la liste
        ingredientsList.appendChild(newIngredientGroup);
    });
});

/* doc :https://select2.org/
Fonction : 
Permet de dupliquer les formulaires de création d'ingrédients,
Permet de changer les valeurs des forms pour le controlleur
*/

document.addEventListener('DOMContentLoaded', function() {
    var ingredientsList = document.getElementById('ingredientsList');
    var ajouterIngredientBtn = document.getElementById('ajouterIngredient');
    var ingredientsExistantsSelect = $('#ingredientsExistant');
    
    // Initialisation de Select2 pour le champ de sélection des ingrédients existants
    ingredientsExistantsSelect.select2();
    
    // Ajout d'un ingrédient manuel 
    ajouterIngredientBtn.addEventListener('click', function() {
		//on recupere le dernier ingredient
        var dernierIngredient = ingredientsList.lastElementChild;
        //on le clone
        var nouvelIngredient = dernierIngredient.cloneNode(true);
        
        //On recupere tous les inpus et select
        var inputs = nouvelIngredient.querySelectorAll('input, select');
        //On parcourt tous les input du formulaire
        inputs.forEach(function(input) {
            //Les valeurs sont remises à zero
            input.value = '';
        });
        
        //On affiche le nouveau formulaire
        ingredientsList.appendChild(nouvelIngredient);
    });
    
    // Ajout d'un ingrédient existant
    //On séléctionne un ingrédient dans le menu select2, place l'id dans le hidden pour relier ingrédient et recette
    ingredientsExistantsSelect.on('select2:select', function (e) {
        var data = e.params.data;
        var ingredientId = data.id;
        var ingredientNom = data.text;
        
        //Formulaire pour ajouter la quantité et l'unité de l'ingrédient
		var html = `
		    <div class="ingredient-existant-detail" data-ingredient-id="${ingredientId}">
		        <input type="hidden" name="ingredientsExistantsIds" value="${ingredientId}">
		        
		        <h4>${ingredientNom}</h4>
		        <div class="form-group">
		            <label for="quantiteExistant_${ingredientId}">Quantité:</label>
		            <input type="number" id="quantiteExistant_${ingredientId}" name="quantiteExistant" required>
		        </div>
		        <div class="form-group">
		            <label for="uniteExistant_${ingredientId}">Unité:</label>
		            <select id="uniteExistant_${ingredientId}" name="uniteExistant">
		                <option value="grammes">Grammes</option>
		                <option value="litres">Litres</option>
		                <option value="cuillères à soupe">Cuillères à soupe</option>
		            </select>
		        </div>
		    </div>
		`;
		//On insère la div a la suite des formulaires deja existants
		document.getElementById('ingredientsExistantsDetails').insertAdjacentHTML('beforeend', html);
    });

    // Gestion de la désélection d'un ingrédient existant
    ingredientsExistantsSelect.on('select2:unselect', function (e) {
        var data = e.params.data;
        var ingredientId = data.id;
        document.querySelector(`.ingredient-existant-detail[data-ingredient-id="${ingredientId}"]`).remove();
    });
});

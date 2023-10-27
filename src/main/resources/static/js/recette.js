document.addEventListener('DOMContentLoaded', function() {
    var ingredientsList = document.getElementById('ingredientsList');
    var ajouterIngredientBtn = document.getElementById('ajouterIngredient');
    var ingredientsExistantsSelect = $('#ingredientsExistant');
    
    // Initialisation de Select2 pour le champ de sélection des ingrédients existants
    ingredientsExistantsSelect.select2();
    
    // Gestion de l'ajout d'un ingrédient manuellement
    ajouterIngredientBtn.addEventListener('click', function() {
        var dernierIngredient = ingredientsList.lastElementChild;
        var nouvelIngredient = dernierIngredient.cloneNode(true);

        var inputs = nouvelIngredient.querySelectorAll('input, select');
        inputs.forEach(function(input) {
            if(input.name) {
                input.name = input.name.replace(/\[\d+\]/, '[' + (ingredientsList.childElementCount - 1) + ']');
            }
            input.value = '';
        });

        ingredientsList.appendChild(nouvelIngredient);
    });
    
    // Gestion de la sélection d'un ingrédient existant
    ingredientsExistantsSelect.on('select2:select', function (e) {
		    console.log('Ingrédient sélectionné', e.params.data);
        var data = e.params.data;
        var ingredientId = data.id;
        var ingredientNom = data.text;
        
		var html = `
		    <div class="ingredient-existant-detail" data-ingredient-id="${ingredientId}">
		        <!-- Ajout d'un champ caché pour l'ID de l'ingrédient -->
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
		document.getElementById('ingredientsExistantsDetails').insertAdjacentHTML('beforeend', html);
    });

    // Gestion de la désélection d'un ingrédient existant
    ingredientsExistantsSelect.on('select2:unselect', function (e) {
        var data = e.params.data;
        var ingredientId = data.id;
        document.querySelector(`.ingredient-existant-detail[data-ingredient-id="${ingredientId}"]`).remove();
    });
});

function togglemdp() {
	let mdpField = document.getElementById("mdp");
  	let toggleButton = document.getElementById("togglemdp");
    if (mdpField.type === "password") {
      mdpField.type = "text";
      toggleButton.textContent = "Masquer le mot de passe";
    } else {
      mdpField.type = "password";
      toggleButton.textContent = "Afficher le mot de passe";
    }
  }
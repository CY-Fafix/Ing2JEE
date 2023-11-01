function togglemdp(){
	let mdp=document.getElementById("divmdp");
	let btnmdp=document.getElementById("btnmdp");
	
	if(btnmdp.textContent=="Afficher le mot de passe"){
		btnmdp.textContent="Cacher le mot de passe";
        mdp.style.display = "inline"; 

		
	}else{
		btnmdp.textContent="Afficher le mot de passe";
	    mdp.style.display = "none";

	}
	}

	
function togglemdpEditer_Profil() {
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
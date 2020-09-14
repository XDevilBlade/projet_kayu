# projet_kayu

## Context du projet :
Créer une application mobile qui doit fonctionner au minimum sur Android 4. Cette application s'appelle "KAYU". Elle doit, comme le permet l'application mobile "YUKA", 
posséder au minimum deux fonctionnalités indispensables qui sont :
* scanner un code barre au format EAN-13
* afficher les informations du produit scanné (nutri-score, les ingrédients, les nutriments, le nom du produit, dans quels magasins on peut le trouver, etc)

KAYU aura à sa disposition une base de données embarquée SQLite qui va stocker les informations des produits scannées. Donc, un historique 
des produits scannés devra être visible dans l'application.

Dès que l'utilisateur a scanné un produit, l'application devra aller requêter l'API d'Open Food Facts pour récupérer les informations du produit.

## Technos utilisées :
* IDE : Android Studio
* API REST
* SQLite
* Languages de programmation utilisés : JAVA

## Des screenshoots de l'application :
### Installation de l'application :
* lorsque vous allez installer l'application, des messages de "Play Protect" vont apparaitre à l'écran, vous devrez les ignorer. En effet, "Play Protect" va vous demander si vous voulez quand même installer l'appli à cause du fait que mon appli n'a pas été signé avec la signature d'application "Google Play". 

Voici les différents messages que vous aurez pendant son installation : 

<img src="Images_KAYU/message_playprotect.jpg" width="200">  <img src="Images_KAYU/analyse_appli.jpg" width="200">

### Interface d'accueil :
* lorsque vous lancez l'application, si l'application n'a pas les droits d'accès à la caméra vous aurez cette interface :

<img src="Images_KAYU/droit_camera.jpg" width="200">

Donc, lorsque vous accéderez à l'interface pour scanner votre code barre, vous aurez cette affichage :



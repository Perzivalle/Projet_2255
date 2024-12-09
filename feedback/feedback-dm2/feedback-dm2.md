# Feedback DM2

## Révisions

- Les corrections demandées sur le diagramme de CU n'ont pas été appliquées.

## Architecture

- L'architecture est un peu confuse. L'application que les résidents ouvrent est une application mobile, qui est reliée à
une application en ligne de commande...?
- Où est l'intervenant?
- L'architecture au niveau 3 est mal comprise, les utilisateurs n'interagissent pas *directement* avec les composants internes
de l'application. Ils interagissent seulement avec l'interface, qui elle fait appel au backend pour gérer la logique derrière
et renvoyer les données qui seront ensuite affichées.
- Le travail de la base de données est incomplet, il devrait aussi pouvoir enregistrer les données des utilisateurs, sinon
ces dernières ne seraient pas persistantes.

## Diagramme de classes

- L'usage d'une classe Database est bien pensé, mais pourquoi elle est seulement reliée à MaVille si Menu a aussi un attribut
BaseDeDonnées?
- Cela devrait être via des classes controller comme AccountController et RequestsController que les actions des résidents
et des intervenants devraient se faire. Les fonctions comme `soumetCandidature()` ne devraient pas être dans les classes
Resident et Intervenant eux-mêmes car cela brise le principe de responsabilité unique. 
- La classe Menu ne devrait pas contenir à la fois les fonctions d'affichage et les fonctions comme `modifierProfil()`.
De plus, elle devrait avoir deux enfants `MenuResident` et `MenuIntervenant` pour distinguer les deux menus, car ils sont
différents dépendamment de l'utilisateur.

## Diagramme de séquence    

- Le bloc *alt* \[requête déjà existante\] doit être étendu jusqu'à la gauche pour aussi inclure l'envoi du message 
"requête déjà existante" sinon c'est comme si l'envoi de ce message faisait partie de la séquence normale. Pareil pour
les autres blocs *alt*.
- De fait, la plupart des blocs *alt* utilisés ne respectent pas la convention et sont difficiles à lire et à distinguer
de la séquence principale.

## Justification des choix du design

Bien!

## Implémentation

- Le menu des travaux consultés est un peu difficile à lire. On a du mal à distinguer les différents travaux entre
eux car il n'y a aucune séparation visuelle. Pareil pour le menu des entraves et des requêtes de travail. L'idéal
serait de mieux formatter tout ça.
- ~~... Où est le code??~~ Le code est là. Faire une classe par CU est une mauvaise pratique cependant. La répartition des responsabilités
ne doit pas se faire comme ça, la logique d'affichage et la logique interne pour récupérer les travaux et les entraves doivent être
séparées, pas mises dans la même classe pour le même CU (par exemple, une classe comme DataFetcher avec les méthodes pour récupérer la liste
des travaux et des entraves, la classe EntravesView qui a une méthode pour afficher une liste d'entraves, et la classe DisplayController
qui appelle la fetcher fonction dans DataFetcher et appelle la méthode display de EntravesView avec la liste d'entraves qu'elle a récupéré).
Ce sont deux logiques et responsabilités complètement différentes (afficher vs récupérer) donc elles doivent être dans différentes classes.
- L'implémentation est très éloignée du design que vous avez proposé dans votre diagramme de classes. Où est la base de données?
- Il y a plusieurs `while(true)` imbriquées qui rendent le code difficile à comprendre.

## Tests

- Tous les tests marchent et sont pertinents.

## Rapport et Git

- J'ai l'impression que `Version 1.0` est le release du devoir 2 mais il est mal nommé. 
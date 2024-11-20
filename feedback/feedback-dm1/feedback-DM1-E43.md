# Feedback DM1

Disclaimer : Tout ce qui est mentionné dans le feedback sont les points et les détails à améliorer. Si des aspects du devoir n'ont pas été critiqués, c'est qu'ils sont déjà bons.

## Glossaire

- La définition pour la planification participative n'est pas suffisante : Ça ne décrit pas ce que c'est ni comment elle se fait.
- La définition du système de notifications personnalisées est insuffisante : Elle ne décrit pas comment les utilisateurs feraient pour personnaliser leurs notifications.
- Il manque des définitions importantes : "Travaux/Type de travaux", "Projet/Statut de projet".

## Diagramme de CUs

- Ce n'est pas dans le diagramme de CUs que vous décrivez avec détail les actions qu'il y a dans chaque CU. 
- "Nom du résident", "Adresse courriel", "Horaire des travaux", etc... ne sont pas des cas d'utilisation.
- Le CU "Connexion" est commun au résident et à l'intervenant car ils se connectent de la même manière donc vous auriez pu faire une généralisation avec "Utilisateur" sur ce CU.
- Le service de la ville devrait aussi vérifier l'inscription d'un intervenant car il doit vérifier que le code de la ville est valide.
- Le service de la ville doit aussi être impliquée dans la signalisation de problèmes car c'est la ville qui s'en occupe en externe.
- Il manque le CU pour permettre une planification participative.

## Scénarios

- Les scénarios alternatifs ne disent pas à quelle étape du scénario principal on est renvoyé ou si le scénario se termine sur une fin alternative.
- "Consulter la liste des requêtes de travail" : L'intervenant n'est pas obligé de soumettre une candidature quand il consulte cette liste.
- Le CU "S'inscrire sur MaVille" est correct en soi, mais puisque le processus d'inscription n'est pas le même pour le résident et l'intervenant, il aurait fallu le diviser en deux CUs différents. Par exemple, inclure que le service de la ville va vérifier le code de 8 chiffres pour confirmer que l'intervenant est valide lors de son inscription.
- Il n'y a que 8 scénarios alors qu'il y a au moins 11 cas d'utilisations explicites dans l'énoncé du cours, sans compter les CUs secondaires qui auraient pu être ajoutés. Par exemple, comment un utilisateur se connecte? 

## Diagrammes d'activité

- Les noeuds de décision qui forment une boucle sur eux-mêmes risquent de causer une boucle infinie si on n'inclut pas une option de fin de flot.
- Dans le diagramme principal, il y a un noeud de décision qui n'a qu'un seul branchement de sortie.
- Les branchements du noeud de décision du diagramme principal pour indiquer quelle action prendre une fois connecté n'ont pas de nom pour indiquer le choix pris.
- "Consulter les travaux en cours ou à venir": Quel est le nom de la décision prise pour choisir entre Accéder à la localisation du résident et Utiliser son adresse enregistrée? Où est le noeud de fusion pour mener à l'action commune aux deux qui suit? Comment la décision après "Filtrer la carte" marche?
- "Rechercher des travaux": Pourquoi il y a une activité qui mène à deux activités en même temps à la fin? Aussi, pourquoi est-ce que le résident doit "Recevoir une alerte quand des travaux seront planifiés dans cette zone" s'il n'a trouvé aucun travail? Il faudrait lui laisser le choix.
- "Signaler un problème à la ville": Remplir le formulaire est suffisant, pas besoin d'écrire une activité pour chaque champ à remplir (surtout quand ça le rend faux comme ici, une activité doit avoir un verbe d'action pour comprendre ce qu'il se passe, comme "Remplir champ de la description du problème". "Description du problème" n'est pas assez descriptif.).
- "Soumettre une requête de travail": Même feedback que pour signaler un problème à la ville. Aussi, "Un intervenant envoie sa candidature" ne fait plus partie de ce scénario car ça représente un évènement distinct, un nouveau flux de travail qui sera traité dans un autre cas d'utilisation comme "Accepter/refuser la candidature d'un intervenant".
- Il y a au moins 2 CUs du résident pour lesquels vous n'avez pas fait de diagramme d'activités : "Personnaliser ses notifications", "Permettre une planification participative".

## Analyse

### Risques

- Le risque concernant un trop grand nombre de signalisations de problèmes ne concerne pas vraiment l'application car tous les problèmes signalés sont gérés par la ville en dehors de l'application.

### Besoins non-fonctionnels

- La fiabilité est un besoin non-fonctionnel réel, mais il concerne surtout la précision et l'actualité des données présentées par l'application comme l'horaire des travaux et le statut des projets. Vous avez dit que ceci est un besoin de maintenance mais la maintenance concerne les solutions mises en place pour faciliter les mises à jour de l'application (ex: documentation technique)

### Besoins matériels

Bien!

### Solution de stockage

Bien!

### Solution d'intégration

Bien!

## Prototype

- Puisque la connexion en tant qu'intervenant et résident se fait de la même manière (courriel + mot de passe), on n'a pas besoin de distinguer les deux menus. Mais c'est correct pareillement.
- Il manque le fichier exécutable .jar.

## Git

Bien!

## Rapport

- Les images ne marchent pas dans le rapport. Les liens utilisés dans le HTML ne redirigent pas vers les images voulues.

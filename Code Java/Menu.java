public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n== Menu Principal ==");
        System.out.println("[1] Connexion");
        System.out.println("[2] Inscription");
        System.out.println("[3] Quitter");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                // Appeler une méthode de connexion
                break;
            case 2:
                showPageInscription();
                break;
            case 3:
                System.out.println("Au revoir !");
                System.exit(0);
            default:
                System.out.println("Choix invalide !");
        }
    }

  public void showPageInscription() {
    System.out.println("\n==--------------------*---*--- Page d'inscription: ---*---*---------------------==");

    String typeCompte;
    label164:
    while (true) {
        int choix;
        while (true) {
            System.out.print("Souhaitez-vous vous inscrire en tant que résident ou intervenant ?");
            System.out.println("\n[1] : Résident\n[2] : Intervenant\n");
            System.out.print("Votre choix : ");

            try {
                choix = this.scanner.nextInt();
                this.scanner.nextLine();
                break;
            } catch (InputMismatchException var14) {
                System.out.println("\n==------ ERREUR: Veuillez entrer un chiffre valide ------==");
                this.scanner.nextLine();
            }
        }

        switch (choix) {
            case 1:
                typeCompte = "Résident";
                break label164;
            case 2:
                typeCompte = "Intervenant";
                break label164;
            default:
                System.out.println("\n==------ ERREUR: Veuillez entrer un chiffre valide ------==");
        }
    }

    while (true) {
        System.out.println("Veuillez entrer votre adresse courriel : ");
        System.out.print("-> ");
        String email = this.scanner.nextLine();
        if (email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            while (true) {
                System.out.println("Veuillez entrer un mot de passe : ");
                System.out.print("-> ");
                String motDePasse = this.scanner.nextLine();
                if (!motDePasse.isBlank()) {
                    if (typeCompte.equals("Résident")) {
                        while (true) {
                            System.out.println("Veuillez entrer votre nom complet : ");
                            System.out.print("-> ");
                            String nomComplet = this.scanner.nextLine();
                            if (!nomComplet.isBlank()) {
                                while (true) {
                                    System.out.println("Veuillez entrer votre date de naissance (YYYY-MM-DD) : ");
                                    System.out.print("-> ");
                                    String dateDeNaissance = this.scanner.nextLine();
                                    if (!dateDeNaissance.isBlank()) {
                                        while (true) {
                                            System.out.println("Veuillez entrer votre adresse résidentielle : ");
                                            System.out.print("-> ");
                                            String adresseResidentielle = this.scanner.nextLine();
                                            if (!adresseResidentielle.isBlank()) {
                                                if (Resident.comptesResident.containsKey(email)) {
                                                    System.out.println("\n==------ ERREUR: Un compte avec cet email existe déjà ------==");
                                                } else {
                                                    Resident.comptesResident.put(email, motDePasse);
                                                    System.out.println("\n==------ Compte Résident créé avec succès ! ------==");
                                                    return;
                                                }
                                            } else {
                                                System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                                            }
                                        }
                                    } else {
                                        System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                                    }
                                }
                            } else {
                                System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                            }
                        }
                    } else {
                        while (true) {
                            System.out.println("Veuillez entrer votre nom complet : ");
                            System.out.print("-> ");
                            String nomComplet = this.scanner.nextLine();
                            if (!nomComplet.isBlank()) {
                                while (true) {
                                    System.out.println("Veuillez entrer le type d'intervenant : ");
                                    System.out.print("-> ");
                                    String typeIntervenant = this.scanner.nextLine();
                                    if (!typeIntervenant.isBlank()) {
                                        while (true) {
                                            System.out.println("Veuillez entrer l'identifiant de la ville (8 chiffres) : ");
                                            System.out.print("-> ");
                                            String identifiantVille = this.scanner.nextLine();
                                            if (identifiantVille.matches("\\d{8}")) {
                                                if (Intervenant.comptesIntervenant.containsKey(email)) {
                                                    System.out.println("\n==------ ERREUR: Un compte avec cet email existe déjà ------==");
                                                } else {
                                                    Intervenant.comptesIntervenant.put(email, motDePasse);
                                                    System.out.println("\n==------ Compte Intervenant créé avec succès ! ------==");
                                                    return;
                                                }
                                            } else {
                                                System.out.println("\n==------ ERREUR: L'identifiant de la ville doit être un code à 8 chiffres ------==");
                                            }
                                        }
                                    } else {
                                        System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                                    }
                                }
                            } else {
                                System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                            }
                        }
                    }
                } else {
                    System.out.println("\n==------ ERREUR: Ce champ ne peut pas être vide ------==");
                }
            }
        } else {
            System.out.println("\n==------ ERREUR: Veuillez entrer une adresse courriel valide ------==");
        }
    }
}


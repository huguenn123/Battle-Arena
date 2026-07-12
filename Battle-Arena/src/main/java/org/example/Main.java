package org.example;

import java.util.ArrayList; 
import java.util.List;      
import java.util.Scanner;   

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);


    private static final List<String> usedNames = new ArrayList<>();

    public static void main(String[] args) {
        printBanner("BATTLE ARENA");

        Player player1 = setupPlayer("Player 1");
        Player player2 = setupPlayer("Player 2");

        System.out.println("\n--- All characters are ready! Let the battle begin! ---\n");

        int turns = runBattle(player1, player2);

        printResults(player1, player2, turns);
    }

    // =========================================================================
    // PHASE 1 — SETUP : création des équipes
    // =========================================================================

    private static Player setupPlayer(String label) {
        System.out.println("=== " + label + " Setup ===");
        System.out.print("Enter your name: ");
        String playerName = SCANNER.nextLine().trim();
        Player player = new Player(playerName);

        List<String> usedTypesInTeam = new ArrayList<>();

        // Boucle pour créer 3 personnages (slot 1, 2, 3)
        for (int slot = 1; slot <= 3; slot++) {
            System.out.println("\n" + playerName + " — create character " + slot + " of 3:");
            Character character = createCharacter(usedTypesInTeam);
            player.addCharacter(character);
            usedTypesInTeam.add(character.getTypeName());
        }
        return player;
    }

    private static Character createCharacter(List<String> usedTypesInTeam) {
        String type = chooseType(usedTypesInTeam);
        String name = chooseName();
        return buildCharacter(type, name);
    }

    private static String chooseType(List<String> usedTypes) {
        // Liste de tous les types, puis on retire ceux déjà pris dans l'équipe
        List<String> available = new ArrayList<>(List.of("Warrior", "Magus", "Colossus", "Dwarf"));
        available.removeAll(usedTypes);

        System.out.println("Available types:");
        for (int i = 0; i < available.size(); i++) {
            // Affiche chaque option numérotée (1, 2, 3...)
            System.out.printf("  %d) %-10s%n", i + 1, available.get(i));
        }

        // Boucle infinie jusqu'à ce que le joueur entre un choix valide
        while (true) {
            System.out.print("Choose a type [1-" + available.size() + "]: ");
            String input = SCANNER.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= available.size()) {
                    return available.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {

            }
            System.out.println("  Invalid choice, try again.");
        }
    }

    private static String chooseName() {
        while (true) {
            System.out.print("Enter a unique name: ");
            String name = SCANNER.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("  Name cannot be empty.");
            } else if (usedNames.contains(name.toLowerCase())) {
                // On compare en minuscules pour que "Thor" et "thor" soient considérés identiques
                System.out.println("  That name is already taken. Choose another.");
            } else {
                usedNames.add(name.toLowerCase());
                return name;
            }
        }
    }

    private static Character buildCharacter(String type, String name) {
        return switch (type) {
            case "Warrior"  -> new Warrior(name);
            case "Magus"    -> new Magus(name);
            case "Colossus" -> new Colossus(name);
            case "Dwarf"    -> new Dwarf(name);
            default         -> throw new IllegalArgumentException("Unknown type: " + type); // Ne devrait jamais arriver
        };
    }

    // =========================================================================
    // PHASE 2 — COMBAT : boucle de jeu tour par tour
    // =========================================================================

    private static int runBattle(Player player1, Player player2) {
        int turns = 0;
        Player current  = player1;
        Player opponent = player2;

        // Continue tant qu'aucune équipe n'est entièrement éliminée
        while (!player1.isDefeated() && !player2.isDefeated()) {
            turns++;
            printBanner("TURN " + turns);
            printBothTeams(player1, player2);

            System.out.println("\n--- " + current.getName() + "'s turn ---");
            playTurn(current, opponent);

            // Échange les rôles : le joueur actuel devient l'adversaire et vice-versa
            Player tmp = current;
            current    = opponent;
            opponent   = tmp;
        }
        return turns;
    }


    private static void playTurn(Player active, Player opponent) {
        Character actor = chooseCharacter(active);
        String action   = chooseAction(actor);

        // Exécute l'action choisie
        switch (action) {
            case "attack" -> performAttack(actor, opponent);
            case "heal"   -> performHeal(actor, active);
        }
    }

    private static Character chooseCharacter(Player player) {
        List<Character> alive = player.getAliveCharacters();
        System.out.println("Choose your fighter:");
        for (int i = 0; i < alive.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, alive.get(i));
        }


        return alive.get(readInt(1, alive.size()) - 1);
    }
      private static String chooseAction(Character actor) {
        boolean canAttack = actor instanceof Attacker;
        boolean canHeal   = actor instanceof Healer;

        if (canAttack && canHeal) {

            System.out.println("Choose an action:");
            System.out.println("  1) Attack");
            System.out.println("  2) Heal an ally");
            return readInt(1, 2) == 1 ? "attack" : "heal";
        }
                return canAttack ? "attack" : "heal";
    }

    private static void performAttack(Character attacker, Player enemyTeam) {
        List<Character> targets = enemyTeam.getAliveCharacters(); // Seuls les ennemis vivants sont ciblables
        System.out.println("Choose a target to attack:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, targets.get(i)); // Affiche chaque cible possible
        }
        Character target = targets.get(readInt(1, targets.size()) - 1); // Le joueur choisit sa cible


        int damage = ((Attacker) attacker).ObjetAttack(target);

        // Affiche le résultat de l'attaque
        System.out.printf("%n  %s hits %s with %s — %d damage!%n",
                attacker.getName(), target.getName(),
                attacker.getWeapon().getNameWeapon(), damage);

        if (!target.isAlive()) {

            System.out.println("  " + target.getName() + " has been defeated!");
        }
    }

    private static void performHeal(Character healer, Player allyTeam) {
        List<Character> targets = allyTeam.getAliveCharacters(); // On ne peut soigner que des vivants
        System.out.println("Choose an ally to heal:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, targets.get(i));
        }
        Character target = targets.get(readInt(1, targets.size()) - 1); // Le joueur choisit l'allié à soigner

        int restored = ((Healer) healer).cure(target);

        System.out.printf("%n  %s heals %s for %d HP!%n",
                healer.getName(), target.getName(), restored);
    }

    // =========================================================================
    // PHASE 3 — RÉSULTATS : affichage de fin de partie
    // =========================================================================

    private static void printResults(Player player1, Player player2, int turns) {
        printBanner("BATTLE OVER");

        // Le gagnant est celui dont l'équipe n'est PAS vaincue
        Player winner = player1.isDefeated() ? player2 : player1;
        System.out.println("  Winner      : " + winner.getName());
        System.out.println("  Total turns : " + turns);

        System.out.println("\n--- Final character status ---");
        System.out.println(player1.getName() + "'s team:");
        player1.printTeamStatus();
        System.out.println(player2.getName() + "'s team:");
        player2.printTeamStatus();
    }

    // =========================================================================
    // MÉTHODES UTILITAIRES
    // =========================================================================

    private static void printBothTeams(Player p1, Player p2) {
        System.out.println(p1.getName() + "'s team:");
        p1.printTeamStatus();
        System.out.println(p2.getName() + "'s team:");
        p2.printTeamStatus();
    }


    private static void printBanner(String title) {
        System.out.println("\n========================================");
        System.out.println("  " + title);
        System.out.println("========================================");
    }


    private static int readInt(int min, int max) {
        while (true) { // Boucle infinie jusqu'à une saisie valide
            System.out.print("  > ");
            String input = SCANNER.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {

            }
            System.out.println("  Please enter a number between " + min + " and " + max + ".");
        }
    }
}

package org.example;

import java.util.ArrayList; // Importe ArrayList : liste dynamique (taille variable)
import java.util.List;      // Importe l'interface List (type générique de liste)


public class Player {

    private final String name;
    private final List<Character> team = new ArrayList<>();



    public Player(String name) {
        this.name = name;
    }


    public void addCharacter(Character character) {
        team.add(character);
    }


    public boolean isDefeated() {
        return team.stream().noneMatch(Character::isAlive);

    }


    public List<Character> getAliveCharacters() {
        return team.stream().filter(Character::isAlive).toList();
    }


    public void printTeamStatus() {
        for (Character c : team) {
            System.out.println("  " + c);         }
    }

    // --- GETTERS ---

    public List<Character> getTeam() { return team; }
    public String getName()          { return name; }
}

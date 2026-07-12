package org.example; // Déclare que cette classe appartient au package "org.example"


public abstract class Character {
    


    private final String name;
    private final String typeName;
    private final Weapon weapon;
    private final int hpMax;
    private int hp;
    private boolean alive;
    // --- CONSTRUCTEUR ---


    public Character(int hpMax, Weapon weapon, String typeName, String name) {
        this.hpMax    = hpMax;
        this.hp       = hpMax;
        this.weapon   = weapon;
        this.typeName = typeName;
        this.name     = name;
        this.alive    = true;
    }

    // --- MÉTHODES DE GESTION DES HP ---


    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        if (hp == 0) {
            alive = false;
        }
    }


    public void restoreHp(int amount) {
        hp = Math.min(hpMax, hp + amount);
    }

    // --- GETTERS (méthodes pour lire les attributs privés depuis l'extérieur) ---

    public String getName()     { return name; }
    public String getTypeName() { return typeName; }
    public Weapon getWeapon()   { return weapon; }
    public int getHp()          { return hp; }
    public int getHpMax()       { return hpMax; }
    public boolean isAlive()    { return alive; }
    // --- MÉTHODE ABSTRAITE ---


    public abstract void action();

    // --- AFFICHAGE ---



    @Override
    public String toString() {

        String status = alive ? String.format("HP: %3d / %3d", hp, hpMax) : "DEFEATED";


        return String.format("%-14s [%-8s]  %s", name, typeName, status);
    }
}

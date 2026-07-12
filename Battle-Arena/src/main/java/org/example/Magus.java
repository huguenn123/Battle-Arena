package org.example; // Déclare que cette classe appartient au package "org.example"


public class Magus extends Character implements Attacker, Healer {




    private static final int HEAL_POWER = 35;


    public Magus(String name) {
        super(130, new Weapon("Staff", 15), "Magus", name);

    }


    @Override
    public void action() {
        System.out.println(getName() + " channels arcane energy.");
    }


    @Override
    public int ObjetAttack(Character target) {
        int damage = getWeapon().rollDamage(); // Dégâts aléatoires basés sur la puissance du bâton (15)
        target.takeDamage(damage);             // Applique les dégâts à la cible
        return damage;
    }


    @Override
    public int cure(Character ally) {
        ally.restoreHp(HEAL_POWER); // Appelle restoreHp() sur l'allié (méthode de Character)
        return HEAL_POWER;          // Retourne 35 pour affichage dans Main
    }
}

package org.example; // Déclare que cette classe appartient au package "org.example"


public class Warrior extends Character implements Attacker {

    public Warrior(String name) {
        super(100, new Weapon("Sword", 25), "Warrior", name);

    }


    @Override
    public void action() {

        System.out.println(getName() + " stands ready with their " + getWeapon().getNameWeapon() + ".");
    }


    @Override
    public int ObjetAttack(Character target) {
        int damage = getWeapon().rollDamage();
        target.takeDamage(damage);
        return damage;
    }
}

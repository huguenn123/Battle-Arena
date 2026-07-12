package org.example;

public class Dwarf extends Character implements Attacker {


    public Dwarf(String name) {
        super(70, new Weapon("Battle Axe", 40), "Dwarf", name);

    }


    @Override
    public void action() {
        System.out.println(getName() + " raises their Battle Axe with fury!");
    }


    @Override
    public int ObjetAttack(Character target) {
        int damage = getWeapon().rollDamage();
        target.takeDamage(damage);
        return damage;
    }
}

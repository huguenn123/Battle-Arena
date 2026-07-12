package org.example; // Déclare que cette classe appartient au package "org.example"

import java.util.Random;

public class Weapon {

    private final String nameWeapon;
    private final int basicEnergyWeapon;

       private static final Random RNG = new Random();


    public Weapon(String nameWeapon, int basicEnergyWeapon) {
        this.nameWeapon        = nameWeapon;
        this.basicEnergyWeapon = basicEnergyWeapon;
    }



    public int rollDamage() {

        double variance = 0.85 + RNG.nextDouble() * 0.30;


        return Math.max(1, (int) Math.round(basicEnergyWeapon * variance));
    }

    // --- GETTERS ---

    public String getNameWeapon()     { return nameWeapon; }
    public int getBasicEnergyWeapon() { return basicEnergyWeapon; }
}

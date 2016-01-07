/**
 * Created by A. Joz on 1/7/2016.
 * <p>
 * --- Day 22: Wizard Simulator 20XX ---
 * <p>
 * Little Henry Case decides that defeating bosses with swords and stuff is boring. Now he's playing the game with a wizard.
 * Of course, he gets stuck on another boss and needs your help again.
 * <p>
 * In this version, combat still proceeds with the player and the boss taking alternating turns. The player still goes first.
 * Now, however, you don't get any equipment; instead, you must choose one of your spells to cast.
 * The first character at or below 0 hit points loses.
 * <p>
 * Since you're a wizard, you don't get to wear armor, and you can't attack normally.
 * However, since you do magic damage, your opponent's armor is ignored, and so the boss effectively has zero armor as well.
 * As before, if armor (from a spell, in this case) would reduce damage below 1, it becomes 1 instead - that is, the boss' attacks always deal at least 1 damage.
 * <p>
 * On each of your turns, you must select one of your spells to cast. If you cannot afford to cast any spell, you lose.
 * Spells cost mana; you start with 500 mana, but have no maximum limit.
 * You must have enough mana to cast a spell, and its cost is immediately deducted when you cast it.
 * Your spells are Magic Missile, Drain, Shield, Poison, and Recharge.
 * <p>
 * Magic Missile costs 53 mana. It instantly does 4 damage.
 * Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
 * Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
 * Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
 * Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
 * Effects all work the same way. Effects apply at the start of both the player's turns and the boss' turns.
 * Effects are created with a timer (the number of turns they last); at the start of each turn, after they apply any effect they have,
 * their timer is decreased by one. If this decreases the timer to zero, the effect ends.
 * You cannot cast a spell that would start an effect which is already active. However, effects can be started on the same turn they end.
 * <p>
 * For example, suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage:
 * <p>
 * -- Player turn --
 * - Player has 10 hit points, 0 armor, 250 mana
 * - Boss has 13 hit points
 * Player casts Poison.
 * <p>
 * -- Boss turn --
 * - Player has 10 hit points, 0 armor, 77 mana
 * - Boss has 13 hit points
 * Poison deals 3 damage; its timer is now 5.
 * Boss attacks for 8 damage.
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 0 armor, 77 mana
 * - Boss has 10 hit points
 * Poison deals 3 damage; its timer is now 4.
 * Player casts Magic Missile, dealing 4 damage.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 0 armor, 24 mana
 * - Boss has 3 hit points
 * Poison deals 3 damage. This kills the boss, and the player wins.
 * Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
 * <p>
 * -- Player turn --
 * - Player has 10 hit points, 0 armor, 250 mana
 * - Boss has 14 hit points
 * Player casts Recharge.
 * <p>
 * -- Boss turn --
 * - Player has 10 hit points, 0 armor, 21 mana
 * - Boss has 14 hit points
 * Recharge provides 101 mana; its timer is now 4.
 * Boss attacks for 8 damage!
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 0 armor, 122 mana
 * - Boss has 14 hit points
 * Recharge provides 101 mana; its timer is now 3.
 * Player casts Shield, increasing armor by 7.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 7 armor, 110 mana
 * - Boss has 14 hit points
 * Shield's timer is now 5.
 * Recharge provides 101 mana; its timer is now 2.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 1 hit point, 7 armor, 211 mana
 * - Boss has 14 hit points
 * Shield's timer is now 4.
 * Recharge provides 101 mana; its timer is now 1.
 * Player casts Drain, dealing 2 damage, and healing 2 hit points.
 * <p>
 * -- Boss turn --
 * - Player has 3 hit points, 7 armor, 239 mana
 * - Boss has 12 hit points
 * Shield's timer is now 3.
 * Recharge provides 101 mana; its timer is now 0.
 * Recharge wears off.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 7 armor, 340 mana
 * - Boss has 12 hit points
 * Shield's timer is now 2.
 * Player casts Poison.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 7 armor, 167 mana
 * - Boss has 12 hit points
 * Shield's timer is now 1.
 * Poison deals 3 damage; its timer is now 5.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 1 hit point, 7 armor, 167 mana
 * - Boss has 9 hit points
 * Shield's timer is now 0.
 * Shield wears off, decreasing armor by 7.
 * Poison deals 3 damage; its timer is now 4.
 * Player casts Magic Missile, dealing 4 damage.
 * <p>
 * -- Boss turn --
 * - Player has 1 hit point, 0 armor, 114 mana
 * - Boss has 2 hit points
 * Poison deals 3 damage. This kills the boss, and the player wins.
 * You start with 50 hit points and 500 mana points. The boss's actual stats are in your puzzle input.
 * What is the least amount of mana you can spend and still win the fight? (Do not include mana recharge effects as "spending" negative mana.)
 * <p>
 * The first half of this puzzle is complete! It provides one gold star: *
 * <p>
 * --- Part Two ---
 * <p>
 * On the next run through the game, you increase the difficulty to hard.
 * <p>
 * At the start of each player turn (before any other effects apply), you lose 1 hit point. If this brings you to or below 0 hit points, you lose.
 * <p>
 * With the same starting stats for you and the boss, what is the least amount of mana you can spend and still win the fight?
 */
public class Day22 {
    static int startHp = 50;
    static int startMp = 500;
    static int bossHp = 51;
    static int bossDamage = 9;
    static int minMp = Integer.MAX_VALUE;
    static int[] duration = {0, 0, 6, 6, 5};
    static int[] cost = {53, 73, 113, 173, 229};
    static int[] effect = {4, 2, 7, 3, 101};

    public static void main(String[] args) {
        //Part 1:
        fight(startHp, startMp, bossHp, 0, 0, 0, 0, 0, false);
        System.out.println("minMp for win " + minMp);

        //Part 2:
        minMp = Integer.MAX_VALUE;
        System.out.println();
        fight(startHp, startMp, bossHp, 0, 0, 0, 0, 0, true);
        System.out.println("minMp for win " + minMp);
    }

    static void castSpell(int hp, int mp, int bossHp, int armor, int shield, int poison, int regen, int mpUsed, int spellNum, boolean b) {
        if (mpUsed > minMp)
            return;
        if (spellNum == 0)
            bossHp -= 4;
        if (spellNum == 1) {
            bossHp -= 2;
            hp += 2;
        }
        if (spellNum == 2) {
            shield = duration[spellNum];
            armor = effect[spellNum];
        }
        if (spellNum == 3)
            poison = duration[spellNum];
        if (spellNum == 4)
            regen = duration[spellNum];

        // boss
        shield--;
        if (poison > 0) {
            bossHp -= effect[3];
            poison--;
        }
        if (regen > 0) {
            regen--;
            mp += effect[4];
        }
        if (bossHp <= 0 && mpUsed < minMp)                                            // if win on boss move: new damage + poison tick
        {
            minMp = mpUsed;
            System.out.println("win with hp: " + hp + " mp " + mp + " used mana " + mpUsed);
            return;
        }
        hp -= bossDamage - armor;
        if (hp > 0)
            fight(hp, mp, bossHp, armor, shield, poison, regen, mpUsed, b);

    }

    static void fight(int hp, int mp, int bossHp, int armor, int shield, int poison, int regen, int mpUsed, boolean b) {
        if (b) hp--;
        if (hp <= 0)
            return;

        shield--;
        if (shield == 0) armor = 0;
        if (poison > 0) {
            bossHp -= effect[3];
            poison--;
        }
        if (regen > 0) {
            mp += effect[4];
            regen--;
        }
        // win on player turn (poison tick)
        if (bossHp <= 0 && mpUsed < minMp)
        {
            minMp = mpUsed;
            return;
        }
        // branch to all spells
        for (int i = 0; i < 5; i++)
        {
            if ((i == 2 && shield > 0) || (i == 3 && poison > 0) || (i == 4 && regen > 0))
                continue;
            if (mp < cost[i])
                continue;
            castSpell(hp, mp - cost[i], bossHp, armor, shield, poison, regen, mpUsed + cost[i], i, b);
        }
    }
}

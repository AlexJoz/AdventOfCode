import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by A. Joz on 1/2/2016.
 * --- Day 21: RPG Simulator 20XX ---
 * <p>
 * Little Henry Case got a new video game for Christmas.
 * It's an RPG, and he's stuck on a boss. He needs to know what equipment to buy at the shop. He hands you the controller.
 * <p>
 * In this game, the player (you) and the enemy (the boss) take turns attacking.
 * The player always goes first. Each attack reduces the opponent's hit points by at least 1.
 * The first character at or below 0 hit points loses.
 * <p>
 * Damage dealt by an attacker each turn is equal to the attacker's damage score minus the defender's armor score.
 * An attacker always does at least 1 damage. So, if the attacker has a damage score of 8,
 * and the defender has an armor score of 3, the defender loses 5 hit points.
 * If the defender had an armor score of 300, the defender would still lose 1 hit point.
 * <p>
 * Your damage score and armor score both start at zero. They can be increased by buying items in exchange for gold.
 * You start with no items and have as much gold as you need.
 * Your total damage or armor is equal to the sum of those stats from all of your items. You have 100 hit points.
 * <p>
 * Here is what the item shop is selling:
 * <p>
 * Weapons:    Cost  Damage  Armor
 * Dagger        8     4       0
 * Shortsword   10     5       0
 * Warhammer    25     6       0
 * Longsword    40     7       0
 * Greataxe     74     8       0
 * <p>
 * Armor:      Cost  Damage  Armor
 * Leather      13     0       1
 * Chainmail    31     0       2
 * Splintmail   53     0       3
 * Bandedmail   75     0       4
 * Platemail   102     0       5
 * <p>
 * Rings:      Cost  Damage  Armor
 * Damage +1    25     1       0
 * Damage +2    50     2       0
 * Damage +3   100     3       0
 * Defense +1   20     0       1
 * Defense +2   40     0       2
 * Defense +3   80     0       3
 * You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one.
 * You can buy 0-2 rings (at most one for each hand). You must use any items you buy.
 * The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
 * <p>
 * For example,
 * suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
 * <p>
 * The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
 * In this scenario, the player wins! (Barely.)
 * <p>
 * You have 100 hit points. The boss's actual stats are in your puzzle input.
 * What is the least amount of gold you can spend and still win the fight?
 * <p>
 * --- Part Two ---
 * <p>
 * Turns out the shopkeeper is working with the boss, and can persuade you to buy whatever items he wants.
 * The other rules still apply, and he still only has one of each item.
 * <p>
 * What is the most amount of gold you can spend and still lose the fight?
 */
public class Day21 {
    private static final int myMoney = 100000;
    DB database;

    public Day21() {
        database = new DB();
        database.add(new Weapon("Dagger        8     4       0"));
        database.add(new Weapon("Shortsword   10     5       0"));
        database.add(new Weapon("Warhammer    25     6       0"));
        database.add(new Weapon("Longsword    40     7       0"));
        database.add(new Weapon("Greataxe     74     8       0"));

        database.add(new Armor("None          0     0       0"));
        database.add(new Armor("Leather      13     0       1"));
        database.add(new Armor("Chainmail    31     0       2"));
        database.add(new Armor("Splintmail   53     0       3"));
        database.add(new Armor("Bandedmail   75     0       4"));
        database.add(new Armor("Platemail   102     0       5"));

        database.add(new Accessory("None          0     0       0"));
        database.add(new Accessory("Damage +1    25     1       0"));
        database.add(new Accessory("Damage +2    50     2       0"));
        database.add(new Accessory("Damage +3   100     3       0"));
        database.add(new Accessory("Defense +1   20     0       1"));
        database.add(new Accessory("Defense +2   40     0       2"));
        database.add(new Accessory("Defense +3   80     0       3"));
    }

    public static void main(String[] args) {
        Day21 d21 = new Day21();

        // d21.play();
        d21.play2();
    }

    private void play2() {
        Player You = new Player("You", 100, 0, 0, myMoney);
        int minGold = Integer.MAX_VALUE;
        int maxGold = 0;
        int fightN = 0;
        GameWithBoss g = new GameWithBoss(You);
        for (Weapon w : database.weapons) {
            for (Armor a : database.armors) {
                for (Accessory ac : database.accessories) {
                    for (Accessory ac2 : database.accessories) {
                        System.out.println("Fight " + fightN++);
                        List<Outfit> newEquip = new ArrayList<>();
                        newEquip.add(w);
                        newEquip.add(a);
                        newEquip.add(ac);
                        newEquip.add(ac2);
                        g.changeEquip(newEquip);
                        Player W = g.runGame();

                        int goldSpent = myMoney - You.gold;

                        //you win
                        if (W == You) {
                            if (goldSpent < minGold) minGold = goldSpent;
                        } else {
                        //you lose
                            if (goldSpent > maxGold) maxGold = goldSpent;
                        }
                    }
                }
            }
        }
        System.out.println("Minimum gold needed to win: " + minGold);
        System.out.println("Maximum gold spent but still loose: " + maxGold);
    }

    private void play() {
        Player joz = new Player("Joz", 100, 0, 0, 0);
        Player skazzi = new Player("Skazzi", 100, 0, 0, 0);

        System.out.println("Player created: " + joz);
        System.out.println("Player created: " + skazzi);

        joz.equip(database.weapons.get(1));
        skazzi.equip(database.weapons.get(2));

        skazzi.attack(joz);
        joz.equip(database.armors.get(4));
        skazzi.attack(joz);
        joz.attack(skazzi);
        System.out.println(skazzi);
        System.out.println(joz);

    }

    class Boss extends Player {
        public Boss() {
            super("BOSS", 103, 9, 2, 0);
        }

        @Override
        public void rise() {
            hp = 103;
            damage = 9;
            armor = 2;

        }
    }

    private class Player {
        String name;
        int hp;
        int damage;
        int armor;
        int gold;
        Set<Outfit> equip = new HashSet<>();
        //Set<Outfit> bag = new HashSet<>();

        public Player(String name, int hp, int damage, int armor, int gold) {
            this.name = name;
            this.hp = hp;
            this.damage = damage;
            this.armor = armor;
            this.gold = gold;
        }

        public void rise() {
            hp = 100;
            damage = 0;
            armor = 0;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", hp=" + hp +
                    '}';
        }

        public void showEquip() {
            System.out.println(equip);
        }

        public void showBag() {
            // System.out.println(bag);
        }

        public boolean isAlive() {
            return hp > 0;
        }

        public void equip(Outfit thing) {
            if (!equip.contains(thing)) {
                equip.add(thing);
                // bag.remove(thing);
                armor += thing.armor;
                damage += thing.damage;

                //TODO: move gold deals to shop class
                gold -= thing.cost;

                System.out.println(this.name + ": " + thing + " equiped!");
            } else

                System.out.println("You can'not equip this!");
        }

        private void defend(Player other) {
            int loose = Math.max(other.damage - this.armor, 1);  //should hit 1 anyway!!
            hp -= loose;
        }

        public void attack(Player other) {
            int hit = Math.max(damage - other.armor, 1);
            other.defend(this);
            System.out.println(this.name + " hit " + other.name + " for " + hit + " hp");
        }

        public void unequip(Outfit thing) {
            if (equip.contains(thing)) {
                equip.remove(thing);
                armor -= thing.armor;
                damage -= thing.damage;
            } else System.out.println("Nothing to uneqip");
        }

        public void equip(List<Outfit> things) {
            equip.clear();
            // TODO: move gold deals to Shop class
            gold = myMoney;
            for (Outfit thing : things) {
                equip(thing);
            }
        }
    }

    abstract class Outfit {
        String name;
        int cost;
        int damage;
        int armor;

        public Outfit(String name, int cost, int damage, int armor) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }

        public Outfit(String params) {
            Pattern p = Pattern.compile("^(\\w+ (\\+\\d+)?) +(\\d+) +(\\d+) +(\\d+)$");
            Matcher m = p.matcher(params);
            while (m.find()) {
                this.name = m.group(1);
                this.cost = Integer.parseInt(m.group(3));
                this.damage = Integer.parseInt(m.group(4));
                this.armor = Integer.parseInt(m.group(5));
            }
        }

        @Override
        public String toString() {
            return name + "(" +
                    "damage=" + damage +
                    ", armor=" + armor + ")";
        }
    }

    class Weapon extends Outfit {

        public Weapon(String name, int cost, int damage) {
            super(name, cost, damage, 0);
        }

        public Weapon(String params) {
            super(params);
        }
    }

    class Armor extends Outfit {

        public Armor(String name, int cost, int armor) {
            super(name, cost, 0, armor);
        }

        public Armor(String params) {
            super(params);
        }
    }

    class Accessory extends Outfit {

        public Accessory(String name, int cost, int damage, int armor) {
            super(name, cost, damage, armor);
        }

        public Accessory(String params) {
            super(params);
        }
    }

    class DB {

        //TODO: make Maps instead...
        List<Weapon> weapons = new ArrayList<>();
        List<Armor> armors = new ArrayList<>();
        List<Accessory> accessories = new ArrayList<>();

        void add(Weapon thing) {
            weapons.add(thing);
        }

        void add(Armor thing) {
            armors.add(thing);
        }

        void add(Accessory thing) {
            accessories.add(thing);
        }
    }

    class GameWithBoss {
        Player You;
        Player BOSS;

        public GameWithBoss(Player player) {
            You = player;
            BOSS = new Boss();
        }

        public void changeEquip(List<Outfit> things) {
            You.equip(things);
        }

        public Player round() {
            You.attack(BOSS);
            if (BOSS.isAlive()) BOSS.attack(You);
            if (You.isAlive() && BOSS.isAlive()) return null;
            return (You.isAlive() && !BOSS.isAlive()) ? You : BOSS;
        }

        public Player runGame() {
            while (true) {
                Player winner = round();
                if (winner != null) {
                    System.out.println(winner + "WIN!");
                    You.rise();
                    BOSS.rise();
                    return winner;
                }
            }
        }
    }
}

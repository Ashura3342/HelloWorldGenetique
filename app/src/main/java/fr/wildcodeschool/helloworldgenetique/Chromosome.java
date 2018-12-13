package fr.wildcodeschool.helloworldgenetique;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Random;

public class Chromosome {
    private StringBuilder code;
    private int cost;

    public Chromosome() {
        this(new StringBuilder());
    }

    public Chromosome(StringBuilder code) {
        this.code = code;
        this.cost = 9999;
    }

    public String getCode() {
        return code.toString();
    }

    public int getCost() {
        return cost;
    }

    public void random(int length) {
        int leftLimit = 32; // letter 'a'
        int rightLimit = 126; // letter 'z'
        Random random = new Random();
        this.code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            code.append((char) randomLimitedInt);
        }
    }

    public void mutate(float chance) {
        if (Math.random() > chance)
            return ;
        int index = (int) Math.floor(Math.random() * this.code.length());
        int upOrDown = Math.random() <= 0.5 ? -1 : 1;
        char newChar = (char) (this.code.charAt(index) + upOrDown);
        this.code.setCharAt(index, newChar);
    }

    public Chromosome[] mate(Chromosome chromosome) {
        int pivot = Math.round(this.code.length() / 2);

        StringBuilder child1 = new StringBuilder()
                    .append(this.code.substring(0, pivot))
                    .append(chromosome.code.substring(pivot));

        StringBuilder child2 = new StringBuilder()
                    .append(chromosome.code.substring(0, pivot))
                    .append(this.code.substring(pivot));

        return new Chromosome[]{ new Chromosome(child1), new Chromosome(child1) };
    }

    public void calcCost(String compareTo) {
        int total = 0;
        for (int i = 0; i < this.code.length(); i++) {
            total += Math.pow(this.code.charAt(i) - compareTo.charAt(i), 2);
        }
        this.cost = total;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{%s} %d", this.code, this.cost);
    }
}

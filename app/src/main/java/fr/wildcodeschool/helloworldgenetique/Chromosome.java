package fr.wildcodeschool.helloworldgenetique;

import java.util.Locale;

public class Chromosome
        implements Comparable<Chromosome>{
    private StringBuilder code;
    private int distance;

    public Chromosome() {
        this("");
    }

    public Chromosome(String code) {
        this.code = new StringBuilder(code);
        this.distance = 9999;
    }

    public String getCode() {
        return code.toString();
    }

    public int getDistance() {
        return distance;
    }

    public void random(int length) {
        int min = ' ';
        int max = '~';
        int k = max - min + 1;
        this.code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int newChar = min + (int) (Math.random() * k);
            this.code.append((char) newChar);
        }
    }

    public void mutate(float chance) {
        if (Math.random() > chance)
            return ;
        int index = (int) (Math.random() * this.code.length());
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

        return new Chromosome[] {
                new Chromosome(child1.toString()),
                new Chromosome(child2.toString())
        };
    }

    public void evaluate(Chromosome chromosome) {
        int total = 0;
        for(int i = 0; i < this.code.length(); i++) {
            total += (int)Math.pow(this.code.charAt(i) - chromosome.code.charAt(i), 2);
        }
        this.distance = total;
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return this.distance - chromosome.distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chromosome) {
            Chromosome chromosome = (Chromosome) obj;
            return this.getCode().equals(chromosome.getCode());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{%s} %d", this.code, this.distance);
    }
}

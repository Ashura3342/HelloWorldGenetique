package fr.wildcodeschool.helloworldgenetique;

import java.util.Collections;
import java.util.LinkedList;

public class Population {
    private Chromosome goal;
    private int size;
    private int generationNumber;
    private LinkedList<Chromosome> chromosomes;

    public Population(String goal, int size) {
        this.goal = new Chromosome(goal);
        this.size = size;
        this.generationNumber = 0;
        chromosomes = new LinkedList<>();
        while(size-- > 0) {
            Chromosome chromosome = new Chromosome();
            chromosome.random(this.goal.getCode().length());
            chromosomes.add(chromosome);
        }
    }

    public void display() {
        int length = Math.min(this.chromosomes.size(), 15);
        System.out.println(this.generationNumber);
        for (int i = 0; i < length; i++) {
            System.out.println(this.chromosomes.get(i));
        }
    }

    public void sort() {
        Collections.sort(this.chromosomes);
        this.chromosomes = new LinkedList<>(this.chromosomes.subList(0, this.size));
    }

    public void select() {
        int index = Math.min((this.chromosomes.size() * 2) / 3, this.size);
        this.chromosomes = new LinkedList<>(this.chromosomes.subList(0, index));
    }

    public void mate() {
        int max = this.chromosomes.size() / 2;
        for (int i = 0; i < max; i++) {
            int random = i;

            while (random == i) {
                random = (int) Math.floor(Math.random() * max);
            }

            Chromosome c1 = this.chromosomes.get(i);
            Chromosome c2 = this.chromosomes.get(random);

            Chromosome[] children = c1.mate(c2);
            this.chromosomes.push(children[0]);
            this.chromosomes.push(children[1]);
        }
    }

    public void mutate(float chance) {
       for(Chromosome chromosome : this.chromosomes) {
           chromosome.mutate(chance);
       }
    }

    public void evaluate() {
        for(Chromosome chromosome : this.chromosomes) {
            chromosome.evaluate(this.goal);
        }
    }

    public void generation() {
        generationNumber = 0;

        this.evaluate();
        while (!this.chromosomes.contains(this.goal)) {

            this.generationNumber++;

            this.sort();
            this.display();
            this.select();
            this.mate();
            this.mutate(0.7f);
            this.evaluate();
        }

        this.sort();
        this.display();
    }
}

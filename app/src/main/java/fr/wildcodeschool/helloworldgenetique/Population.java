package fr.wildcodeschool.helloworldgenetique;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Population {
    private String goal;
    private int size;
    private int generationNumber;
    private LinkedList<Chromosome> chromosomes;

    public Population(String goal, int size) {
        this.goal = goal;
        this.size = size;
        this.generationNumber = 0;
        chromosomes = new LinkedList<>();
        while(size-- > 0) {
            Chromosome chromosome = new Chromosome();
            chromosome.random(this.goal.length());
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
        this.chromosomes.sort(new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return o1.getCost() - o2.getCost();
            }
        });
        this.chromosomes = new LinkedList<>(this.chromosomes.subList(0, this.size));
    }

    public void select() {
        int index = Math.min((this.chromosomes.size() * 2) / 3, this.size);
        this.chromosomes = new LinkedList<>(this.chromosomes.subList(0, this.size));
    }

    public void mate() {
        int max = this.chromosomes.size() / 2;
        for (int i = 0; i < max; i++) {
            int random = i;

            while (random == i) {
                random = (int) Math.floor(Math.random() * max);
            }

            Chromosome[] children = this.chromosomes.get(i).mate(this.chromosomes.get(random));
            this.chromosomes.push(children[0]);
            this.chromosomes.push(children[1]);
        }
    }

    public boolean mutate(float chance) {
       for(Chromosome chromosome : this.chromosomes) {
           chromosome.mutate(chance);
           chromosome.calcCost(this.goal);
           if (chromosome.getCode().equals(this.goal)) {
               return true;
           }
       }
       return false;
    }

    public void generation() {
        generationNumber = 0;

        do {
            this.generationNumber++;
            for(int i = 0; i < this.chromosomes.size(); i++) {
                this.chromosomes.get(i).calcCost(this.goal);
            }

            this.sort();
            this.display();
            this.select();
            this.mate();

        } while(!this.mutate(0.7f));

        this.sort();
        this.display();
    }
}

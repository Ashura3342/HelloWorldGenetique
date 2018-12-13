package fr.wildcodeschool.helloworldgenetique;

public class Program {
    public static void main(String... args) {
        Population population = new Population("Hello World!", 2000);
        population.generation();
    }
}

package com.toy.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Toy {
    private String id;
    private String name;
    private int weight;

    public Toy(String id, String name, String weightStr) {
        this.id = id;
        this.name = name;
        try {
            this.weight = Integer.parseInt(weightStr);
        } catch (NumberFormatException e) {
            this.weight = 1; // Значение по умолчанию
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public static void main(String[] args) {
        // Пример данных
        Toy toy1 = new Toy("1", "Мишка", "10");
        Toy toy2 = new Toy("2", "Конструктор", "5");
        Toy toy3 = new Toy("3", "Кукла", "15");

        List<Toy> toys = new ArrayList<>();
        toys.add(toy1);
        toys.add(toy2);
        toys.add(toy3);

        // Вычисление общей суммы весов
        int totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();

        // Проведение 10 розыгрышей
        try (FileWriter writer = new FileWriter("draw_results.txt")) {
            for (int i = 0; i < 10; i++) {
                int rand = random.nextInt(totalWeight) + 1; // Случайное число от 1 до totalWeight
                int cumulativeWeight = 0;
                Toy selectedToy = null;
                for (Toy toy : toys) {
                    cumulativeWeight += toy.getWeight();
                    if (rand <= cumulativeWeight) {
                        selectedToy = toy;
                        break;
                    }
                }
                if (selectedToy != null) {
                    writer.write("Выпала игрушка: " + selectedToy.getName() + " (ID: " + selectedToy.getId() + ")\n");
                } else {
                    writer.write("Нет доступных игрушек для розыгрыша.\n");
                }
            }
            System.out.println("Результаты розыгрыша записаны в draw_results.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при записи результатов: " + e.getMessage());
        }
    }
}


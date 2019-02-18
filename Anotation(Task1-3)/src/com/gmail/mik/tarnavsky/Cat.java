package com.gmail.mik.tarnavsky;

@Save
public class Cat {
    @Save
    private int taleLenght;
    @Save
    String name;
    private int age;
    @Save
    double weight;
    private String color;
    @Save
    private boolean sex;

    public Cat() {
        this.taleLenght = 7;
        this.name = "Kytty";
        this.age = 5;
        this.weight = 6.2;
        this.color = "Red";
        this.sex = false;
    }

    public Cat(int taleLenght, String name, int age, double weight, String color, boolean sex) {
        this.taleLenght = taleLenght;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.color = color;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "taleLenght=" + taleLenght +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", color='" + color + '\'' +
                ", sex=" + sex +
                '}';
    }
}

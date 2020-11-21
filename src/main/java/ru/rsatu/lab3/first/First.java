package ru.rsatu.lab3.first;

public class First implements FirstInterface {
    /**
     * Описание полей.
     */
    private String someString;

    /**
     * Конструктор default.
     */
    public First() {
        this.someString = "default value";
    }

    /**
     * Конструктор с параметром
     * @param someString - строка в классе
     */
    public First(String someString) {
        this.someString = someString;
    }

    /**
     * Геттер поля someString первого класса.
     */
    public String getFirstString() {
        return this.someString;
    }

    /**
     * Сеттер поля someString первого класса.
     */
    public void setFirstString(String someString) {
        this.someString = someString;
    }
}

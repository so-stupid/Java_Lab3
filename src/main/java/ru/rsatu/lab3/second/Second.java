package ru.rsatu.lab3.second;

import ru.rsatu.lab3.first.First;

public class Second {
    /**
     * Описание полей.
     */
    private First firstClassObject;

    /**
     * Конструктор.
     */
    public Second() {
        this.firstClassObject = new First();
    }

    /**
     * Геттер для поля второго класса.
     */
    public String getSecondString() {
        return this.firstClassObject.getFirstString();
    }

    /**
     * Сеттер для поля второго класса.
     */
    public void setSecondString(String someString) {
        this.firstClassObject.setFirstString(someString);
    }

}

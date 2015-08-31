package com.example.models;

import java.io.Serializable;

/**
 * Описание сотрудника
 *
 * @author Q-OLN
 */
public class Employee implements Serializable {

    /**
     * Идентификатор
     */
    private int id;

    /**
     * Фамилия
     */
    private String surname;

    /**
     * Имя
     */
    private String name;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Должность
     */
    private String post;

    /**
     * Конструктор
     *
     * @param surname    фамилия
     * @param name       имя
     * @param middleName отчество
     * @param post       должность
     */
    public Employee(String surname, String name, String middleName, String post) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.post = post;
    }

    /**
     * Конструктор
     */
    public Employee() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPost() {
        return post;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;

        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

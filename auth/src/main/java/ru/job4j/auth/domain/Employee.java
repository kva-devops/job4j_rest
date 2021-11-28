package ru.job4j.auth.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private int inn;

    private Timestamp hiring;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> personList = new ArrayList<>();

    public static Employee of(String name, String surname, int inn) {
        Employee employee = new Employee();
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.hiring = new Timestamp(System.currentTimeMillis());
        return employee;
    }

    public void addPerson(Person person) {
        this.personList.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public Timestamp getHiring() {
        return hiring;
    }

    public void setHiring(Timestamp hiring) {
        this.hiring = hiring;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}

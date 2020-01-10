package es.carlos.santos.p2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Author {

    interface Shallow {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Shallow.class)
    private long id;

    @JsonView(Shallow.class)
    private String name;

    private int age;



    public Author(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Author [age=" + age + ", id=" + id + ", name=" + name + "]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
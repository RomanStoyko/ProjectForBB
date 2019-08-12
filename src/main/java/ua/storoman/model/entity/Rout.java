package ua.storoman.model.entity;


import java.util.Objects;

public class Rout {
    private int id;
    private String name;

    public Rout() {
    }

    public Rout(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Rout{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rout rout = (Rout) o;
        return Objects.equals(name, rout.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

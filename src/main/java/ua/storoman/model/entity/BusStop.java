package ua.storoman.model.entity;


import java.util.Objects;

public class BusStop {

    private int id;
    private String name;
    private String streetName;
    private boolean isCovered;

    public BusStop(){}

    public BusStop(String name, String streetName, boolean isCovered) {
        this.name = name;
        this.streetName = streetName;
        this.isCovered = isCovered;
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }


    @Override
    public String toString() {
        return "BusStopEntyry{" +
                ", name='" + name + '\'' +
                ", streetName='" + streetName + '\'' +
                ", isCovered=" + isCovered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusStop that = (BusStop) o;
        return isCovered == that.isCovered &&
                Objects.equals(name, that.name) &&
                Objects.equals(streetName, that.streetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, streetName, isCovered);
    }
}
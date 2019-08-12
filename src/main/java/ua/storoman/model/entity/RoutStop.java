package ua.storoman.model.entity;

import java.util.Objects;

public class RoutStop {

    private int id;
    private Rout rout;
    private BusStop busStop;

    public RoutStop() {
    }

    public RoutStop(Rout rout, BusStop busStop) {
        this.rout = rout;
        this.busStop = busStop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rout getRout() {
        return rout;
    }

    public void setRout(Rout rout) {
        this.rout = rout;
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public void setBusStop(BusStop busStop) {
        this.busStop = busStop;
    }

    @Override
    public String toString() {
        return "RoutStop{" +
                ", rout=" + rout.getId() +
                ", busStop=" + busStop.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutStop routStop = (RoutStop) o;
        return Objects.equals(rout, routStop.rout) &&
                Objects.equals(busStop, routStop.busStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rout, busStop);
    }
}

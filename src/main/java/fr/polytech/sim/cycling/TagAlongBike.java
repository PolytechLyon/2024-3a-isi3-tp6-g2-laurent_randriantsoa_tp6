package fr.polytech.sim.cycling;

public class TagAlongBike extends SimpleBike {

    public TagAlongBike() {
        components.add(new SimpleBike());
    }
}

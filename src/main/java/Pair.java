public class Pair {
    private final Transmitter transmitter;
    private Double weight;

    public static Pair of(Transmitter transmitter, double weight) {
        return new Pair(transmitter, weight);
    }

    private Pair(Transmitter transmitter, double weight) {
        this.transmitter = transmitter;
        this.weight = weight;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}

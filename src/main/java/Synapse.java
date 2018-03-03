public class Synapse {
    private final Neuron neuron;
    private Double weight;

    public static Synapse to(Neuron neuron, double weight) {
        return new Synapse(neuron, weight);
    }

    private Synapse(Neuron neuron, double weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}

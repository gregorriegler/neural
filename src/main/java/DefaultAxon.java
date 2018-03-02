public class DefaultAxon implements Axon {

    private final Neuron neuron;
    private double weight;

    public static Axon create(Neuron neuron) {
        return new DefaultAxon(neuron, 1);
    }

    public static Axon create(Neuron neuron, double weight) {
        return new DefaultAxon(neuron, weight);
    }

    private DefaultAxon(Neuron neuron, double weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    @Override
    public void feed(double i) {
        neuron.feed(i * weight);
    }
}

public class DefaultAxon implements Axon {

    private final Neuron neuron;

    public static Axon create(Neuron neuron) {
        return new DefaultAxon(neuron);
    }

    private DefaultAxon(Neuron neuron) {
        this.neuron = neuron;
    }

    @Override
    public void feed(int i) {
        neuron.feed(i);
    }
}

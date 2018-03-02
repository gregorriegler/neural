public interface Neuron {
    void feed(double i);

    void addAncestor(Neuron ancestor);
}

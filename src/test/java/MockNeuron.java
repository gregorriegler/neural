public class MockNeuron implements Neuron {

    private Double fed;

    @Override
    public void feed(double i) {
        fed = i;
    }

    @Override
    public void addAncestor(Neuron ancestor) {

    }

    public Double getFed() {
        return fed;
    }
}

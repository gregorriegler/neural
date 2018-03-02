public class MockNeuron implements Neuron {

    private Integer fed;

    @Override
    public void feed(int i) {
        fed = i;
    }

    @Override
    public void addAncestor(Neuron ancestor) {

    }

    public Integer getFed() {
        return fed;
    }
}

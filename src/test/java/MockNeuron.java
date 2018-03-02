public class MockNeuron implements Transmitter {

    private double output;

    public MockNeuron(double output) {
        this.output = output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    @Override
    public double output() {
        return output;
    }

    @Override
    public void backpropagate(double expected) {

    }

    @Override
    public double getLatestOutput() {
        return 0;
    }
}

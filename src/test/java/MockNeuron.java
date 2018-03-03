public class MockNeuron extends Neuron {

    private double spoofedOutput;

    public MockNeuron(double output) {
        super(0d);
        this.spoofedOutput = output;
    }

    public void spoofOutput(double spoofedOutput) {
        this.spoofedOutput = spoofedOutput;
    }

    @Override
    public double output() {
        return spoofedOutput;
    }

    @Override
    public void backpropagate(double expected, double rate) {

    }

    @Override
    public void hiddenBackpropagate(double descendantError, double currentWeight, double rate) {

    }

    @Override
    public double getLatestOutput() {
        return spoofedOutput;
    }

    @Override
    public void setBias(double bias) {

    }

    @Override
    public double getBias() {
        return 0;
    }
}

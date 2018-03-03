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
    public void backpropagate(double expected, double learningRate) {

    }

    @Override
    public void hiddenBackpropagate(double descendantError, double currentWeight, double learningRate) {

    }

    @Override
    public double getLatestOutput() {
        return spoofedOutput;
    }
}

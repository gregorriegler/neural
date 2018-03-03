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
        System.out.print(" " + output + " ");
        return output;
    }

    @Override
    public void backpropagate(double expected) {

    }

    @Override
    public void hiddenBackpropagate(double delta) {

    }

    @Override
    public double getLatestOutput() {
        return output;
    }

    @Override
    public void setBias(double bias) {

    }

    @Override
    public double getBias() {
        return 0;
    }
}

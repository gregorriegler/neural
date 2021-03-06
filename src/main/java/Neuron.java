import java.util.Arrays;
import java.util.List;

public class Neuron {

    private final List<Synapse> predecessors;
    private double latestOutput;
    private double bias;

    public static Neuron create(double bias, Synapse... predecessors) {
        return new Neuron(bias, predecessors);
    }

    public Neuron(double bias, Synapse... predecessors) {
        this.bias = bias;
        this.predecessors = Arrays.asList(predecessors);
    }

    public double output() {
        latestOutput = sigmoid(predecessors.stream()
                .mapToDouble(synapse -> synapse.getNeuron().output() * synapse.getWeight())
                .sum() + bias);

        return latestOutput;
    }

    public double getLatestOutput() {
        return latestOutput;
    }

    public void backpropagate(double expected, double learningRate) {
        output();
        double error = (expected - latestOutput) * derivative(latestOutput);
        adaptBias(learningRate, error);
        predecessors.forEach(synapse -> synapse.backpropagate(error, learningRate));
    }

    public void backpropagate(double descendantError, double currentWeight, double learningRate) {
        double error = currentWeight * descendantError * derivative(latestOutput);
        adaptBias(learningRate, error);
        predecessors.forEach(synapse -> synapse.backpropagate(error, learningRate));
    }

    private double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-1.0 * x));
    }

    private double derivative(double output) {
        return output * (1 - output);
    }

    private void adaptBias(double learningRate, double error) {
        bias = bias + learningRate * error;
    }
}

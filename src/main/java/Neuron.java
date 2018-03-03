import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.Arrays;
import java.util.List;

public class Neuron implements Transmitter {

    private final String name;
    private final List<Pair> predecessors;
    private double latestOutput;
    private double bias;

    public Neuron(String name, double bias, Pair... predecessors) {
        this.name = name;
        this.bias = bias;
        this.predecessors = Arrays.asList(predecessors);
    }

    public static Neuron create(double bias, Pair... predecessors) {
        return new Neuron("neuron", bias, predecessors);
    }

    public static Neuron create(String name, double bias, Pair... predecessors) {
        return new Neuron(name, bias, predecessors);
    }

    public double output() {
        double result = predecessors.stream()
                .mapToDouble(n -> n.getTransmitter().output() * n.getWeight())
                .sum() + bias;
        latestOutput = sigmoid(result);

        return latestOutput;
    }

    public double getLatestOutput() {
        return latestOutput;
    }

    @Override
    public void setBias(double bias) {
        this.bias = bias;
    }

    @Override
    public double getBias() {
        return bias;
    }

    private static double sigmoid(double x) {
        return new Sigmoid(0, 1).value(x);
    }

    private double derivative(double output) {
        return output * (1 - output);
    }

    public void backpropagate(double expected, double rate) {
        output();
        double error = (expected - latestOutput) * derivative(latestOutput);
        bias = bias + rate * error;

        predecessors.forEach(predecessor -> {
            predecessor.setWeight(predecessor.getWeight() + rate * error * predecessor.getTransmitter().getLatestOutput());
            predecessor.getTransmitter().hiddenBackpropagate(error, predecessor.getWeight(), rate);
        });
    }

    public void hiddenBackpropagate(double descendantError, double currentWeight, double rate) {
        double error = currentWeight * descendantError * derivative(latestOutput);
        bias = bias + rate * error;

        predecessors.forEach(predecessor -> {
            predecessor.setWeight(predecessor.getWeight() + rate * error * predecessor.getTransmitter().getLatestOutput());
            predecessor.getTransmitter().hiddenBackpropagate(error, predecessor.getWeight(), rate);
        });
    }
}

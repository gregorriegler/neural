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
        latestOutput = round(sigmoid(result));

        return latestOutput;
    }

    private double round(double value) {
        return Math.round(value * 1000) / 1000;
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

    public void backpropagate(double expected) {
        double output = output();

        double error = Math.pow(latestOutput - expected, 2);
        System.out.println(name + ": expected: " + expected + " output: " + latestOutput + " error: " + error);

        predecessors.forEach(predecessor -> {

            double deltaOutpout = derivative(output) * (output - expected);

            System.out.println("delta: " + deltaOutpout);
            double newWeight = -.1 * deltaOutpout * predecessor.getTransmitter().getLatestOutput();
            System.out.println("old weight: " + predecessor.getWeight() + " new weight: " + newWeight);
            predecessor.setWeight(newWeight);
            double newBias = .1 * deltaOutpout;
            System.out.println("old bias: " + predecessor.getTransmitter().getBias() + " new bias: " + newBias);
            predecessor.getTransmitter().setBias(newBias);

            predecessor.getTransmitter().hiddenBackpropagate(deltaOutpout * predecessor.getWeight());
        });
    }

    public void hiddenBackpropagate(double deltaOutpout) {
        predecessors.forEach(predecessor -> {

            //hidden layer
            double dj = derivative(latestOutput) * deltaOutpout * predecessor.getWeight();

            predecessor.setWeight(-.1 * dj * predecessor.getTransmitter().getLatestOutput());
            predecessor.getTransmitter().setBias(.1 * dj);

            predecessor.getTransmitter().backpropagate(dj * predecessor.getWeight());
        });
    }

    private double derivative(double output) {
        return output * (1 - output);
    }
}

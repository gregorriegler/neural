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

        System.out.println(name + " output: " + latestOutput);
        return latestOutput;
    }

    private double round(double value) {
        return Math.round(value*1000)/1000;
    }


    public double getLatestOutput() {
        return latestOutput;
    }

    private static double sigmoid(double x) {
        return new Sigmoid(0, 1).value(x);
    }

    public void backpropagate(double expected) {
        output();
        double error = Math.pow(latestOutput - expected, 2);
        if(name.equals("EndNeuron")) System.out.println(name + ": expected: " + expected + " output: " + latestOutput + " error: " + error);
//        double delta = expected - latestOutput;
        predecessors.forEach(predecessor -> {
            double delta = expected-predecessor.getTransmitter().getLatestOutput();

            predecessor.setWeight(predecessor.getWeight() + (delta * error));
            predecessor.getTransmitter().backpropagate(expected);
        });
    }
}

import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.Arrays;
import java.util.List;

public class Neuron implements Transmitter {

    private final String name;
    private final List<Pair> predecessors;
    private final double bias = 0;
    private double latestOutput;

    public Neuron(String name, Pair... predecessors) {
        this.name = name;
        this.predecessors = Arrays.asList(predecessors);
    }

    public static Neuron create(Pair... predecessors) {
        return new Neuron("neuron", predecessors);
    }
    public static Neuron create(String name, Pair... predecessors) {
        return new Neuron(name, predecessors);
    }

    public double output() {
        double result = predecessors.stream()
                .mapToDouble(n -> n.getTransmitter().output() * n.getWeight())
                .sum() + bias;
        latestOutput = round(sigmoid(result));

//        System.out.println("*** result: " + result + " output: " + latestOutput);
        return latestOutput;
    }

    public double getLatestOutput() {
        return latestOutput;
    }

    private static double round(double value) {
        return value;//(double)Math.round(value * 1000d) / 1000d;
    }

    private static double sigmoid(double x) {
        return new Sigmoid(0, 1).value(x);
    }

    public void backpropagate(double expected) {
        double output = output();
        double error = round(Math.pow(output - expected, 2));
        if(name.equals("EndNeuron")) System.out.println(name + ": expected: " + expected + " output: " + output + " error: " + error);
        predecessors.forEach(predecessor -> {
            double change = predecessor.getWeight() - error;
//            System.out.println("current weight: " + predecessor.getWeight() + " change weight to: " + change);
            predecessor.setWeight(change);
            predecessor.getTransmitter().backpropagate(latestOutput - error);
        });
    }
}

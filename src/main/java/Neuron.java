import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron {

    private final Map<Neuron, Double> predecessors;
    private final List<Neuron> descendants;
    private Map<Neuron, Double> input = new HashMap<>();

    public Neuron(Pair<Neuron, Double>... predecessors) {
        this.predecessors = Arrays.stream(predecessors)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        this.descendants = new ArrayList<>();
    }

    public static Neuron create() {
        return new Neuron();
    }

    public static Neuron create(Pair<Neuron, Double>... predecessors) {
        return new Neuron(predecessors);
    }

    public boolean hasAllInputs() {
        return predecessors.keySet().equals(input.keySet());
    }

    public void feed(Neuron neuron, double value) {
        input.put(neuron, predecessors.get(neuron) * value);
    }

    public void trigger(double value) {
        descendants.forEach(d -> d.feed(this, value));
    }

    public void live() {
        if (!hasAllInputs()) return;
        double result = result();
        descendants.forEach(d -> d.feed(this, result));
    }

    public double result() {
        return Math.max(0, input.values().stream().mapToDouble(d -> d).sum());
    }
}

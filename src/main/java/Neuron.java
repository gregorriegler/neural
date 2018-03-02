import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class Neuron implements Transmitter {

    private final List<Pair<Transmitter, Double>> predecessors;
    private final double bias = 1;

    public Neuron(Pair<Transmitter, Double>... predecessors) {
        this.predecessors = Arrays.asList(predecessors);
    }

    public static Neuron create() {
        return new Neuron();
    }

    public static Neuron create(Pair<Transmitter, Double>... predecessors) {
        return new Neuron(predecessors);
    }

    public double output() {
        return Math.max(
                0,
                predecessors.stream()
                        .mapToDouble(n -> n.getKey().output() * n.getValue())
                        .sum() + bias
        );
    }
}

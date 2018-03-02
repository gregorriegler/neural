import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultNeuron implements Neuron {

    private final List<Axon> axons;
    private final List<Neuron> ancestors = new ArrayList<>();

    public DefaultNeuron(List<? extends Neuron> descendants) {
        this.axons = descendants.stream()
            .peek(descendant -> descendant.addAncestor(this))
            .map(descendant -> DefaultAxon.create(descendant))
            .collect(Collectors.toList());
    }

    public static DefaultNeuron create() {
        return new DefaultNeuron(Collections.emptyList());
    }

    public static DefaultNeuron create(List<? extends Neuron> descendants) {
        return new DefaultNeuron(descendants);
    }

    public void feed(double i) {
        axons.forEach(a -> a.feed(i));
    }

    @Override
    public void addAncestor(Neuron ancestor) {
        ancestors.add(ancestor);
    }

    public List<Neuron> getAncestors() {
        return ancestors;
    }
}

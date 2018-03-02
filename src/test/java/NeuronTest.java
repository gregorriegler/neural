import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class NeuronTest {

    @Test
    void createNeuron() {
        assertNotNull(DefaultNeuron.create());
    }

    @Test
    void createWithDescendant() {
        DefaultNeuron descendant = DefaultNeuron.create();
        DefaultNeuron neuron = DefaultNeuron.create(asList(descendant));

        assertNotNull(neuron);
        assertThat(descendant.getAncestors()).contains(neuron);
    }

    @Test
    void feedNeuronPassesToDescendant() {
        MockNeuron descendant = new MockNeuron();
        DefaultNeuron neuron = DefaultNeuron.create(asList(descendant));

        Integer expectedValue = 1;
        neuron.feed(expectedValue);
        assertEquals(expectedValue, descendant.getFed());
    }
}

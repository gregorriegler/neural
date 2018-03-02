import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AxonTest {

    @Test
    void feedAxon() {
        MockNeuron neuron = new MockNeuron();
        Axon axon = DefaultAxon.create(neuron);
        Double expectedFed = 1d;

        axon.feed(expectedFed);

        assertEquals(expectedFed, neuron.getFed());
    }

    @Test
    void feedAxonWithWeight() {
        MockNeuron neuron = new MockNeuron();
        Axon axon = DefaultAxon.create(neuron, 0.5);

        axon.feed(1);

        assertThat(neuron.getFed()).isEqualTo(0.5);
    }
}

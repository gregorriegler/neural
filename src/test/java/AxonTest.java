import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AxonTest {

    @Test
    void feedAxon() {
        MockNeuron neuron = new MockNeuron();
        Axon axon = DefaultAxon.create(neuron);
        Integer expectedFed = 1;

        axon.feed(expectedFed);

        assertEquals(expectedFed, neuron.getFed());
    }
}

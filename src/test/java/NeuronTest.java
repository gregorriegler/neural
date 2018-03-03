import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuronTest {

    public static final Transmitter ZERO = new MockNeuron(0);

    public static final Transmitter ONE = new MockNeuron(1);

    private Neuron xorNet(Transmitter a, Transmitter b) {
        return Neuron.create("EndNeuron", -30,
                Pair.of(Neuron.create("T", -10, Pair.of(a, 20), Pair.of(b, 20)), 20),
                Pair.of(Neuron.create("B", 30, Pair.of(a, -20), Pair.of(b, -20)), 20)
        );
    }

    @Test
    void xor_0_0() {
        assertThat(xorNet(ZERO, ZERO).output()).isEqualTo(0);
    }

    @Test
    void xor_0_1() {
        assertThat(xorNet(ZERO, ONE).output()).isEqualTo(1);
    }

    @Test
    void xor_1_0() {
        assertThat(xorNet(ONE, ZERO).output()).isEqualTo(1);
    }

    @Test
    void xor_1_1() {
        assertThat(xorNet(ONE, ONE).output()).isEqualTo(0);
    }

    @Test
    void xor_backpropagate() {
        MockNeuron inputA = new MockNeuron(0);
        MockNeuron inputB = new MockNeuron(0);
        Neuron result = xorNet(inputA, inputB);

        for (int i = 0; i < 100; i++) {
            inputA.setOutput(0);
            inputB.setOutput(0);
            for (int j = 0; j < 10; j++) {
                result.backpropagate(0);
            }
            inputA.setOutput(1);
            inputB.setOutput(0);
            for (int j = 0; j < 10; j++) {
                result.backpropagate(1);
            }
            inputA.setOutput(0);
            inputB.setOutput(1);
            for (int j = 0; j < 10; j++) {
                result.backpropagate(1);
            }
            inputA.setOutput(1);
            inputB.setOutput(1);
            for (int j = 0; j < 10; j++) {
                result.backpropagate(0);
            }
        }

    }


}

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuronTest {

    public static final Transmitter ZERO = new Transmitter() {
        @Override
        public double output() {
            return 0;
        }

        @Override
        public void backpropagate(double expected) {
        }

        @Override
        public double getLatestOutput() {
            return 0;
        }
    };

    public static final Transmitter ONE = new Transmitter() {
        @Override
        public double output() {
            return 1;
        }

        @Override
        public void backpropagate(double expected) {

        }

        @Override
        public double getLatestOutput() {
            return 0;
        }
    };

    @Test
    void simple() {
        Neuron output = Neuron.create(Pair.of(ZERO, 1d));

        assertThat(output.output()).isEqualTo(1d);
    }

    private Neuron xorNet(Transmitter a, Transmitter b) {
        return Neuron.create("EndNeuron",
                Pair.of(Neuron.create(Pair.of(a, -0.1d), Pair.of(b, -0.5d)), -1.7d),
                Pair.of(Neuron.create(Pair.of(a, 0.4d), Pair.of(b, 0.1d)), 0.60d),
                Pair.of(Neuron.create(Pair.of(a, -1.2d), Pair.of(b, -0.5d)), 0.1d)
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

        for (int i = 0; i < 10000; i++) {
            result.backpropagate(0);
            inputA.setOutput(1);
            inputB.setOutput(0);
            result.backpropagate(1);
            inputA.setOutput(0);
            inputB.setOutput(1);
            result.backpropagate(1);
            inputA.setOutput(1);
            inputB.setOutput(1);
            result.backpropagate(0);
        }

    }


}

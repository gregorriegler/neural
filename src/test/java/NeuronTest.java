import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public class NeuronTest {

    public static final Neuron ZERO = new MockNeuron(0);
    public static final Neuron ONE = new MockNeuron(1);
    private MockNeuron A = new MockNeuron(0);
    private MockNeuron B = new MockNeuron(0);
    private Neuron network;

    private Neuron workingXorNet(Neuron a, Neuron b) {
        return Neuron.create(-30,
                Synapse.to(Neuron.create(-10, Synapse.to(a, 20), Synapse.to(b, 20)), 20),
                Synapse.to(Neuron.create(30, Synapse.to(a, -20), Synapse.to(b, -20)), 20)
        );
    }

    @Test
    void xor_0_0() {
        assertThat(workingXorNet(ZERO, ZERO).output()).isCloseTo(0, offset(0.01));
    }

    @Test
    void xor_0_1() {
        assertThat(workingXorNet(ZERO, ONE).output()).isCloseTo(1, offset(0.01));
    }

    @Test
    void xor_1_0() {
        assertThat(workingXorNet(ONE, ZERO).output()).isCloseTo(1, offset(0.01));
    }

    @Test
    void xor_1_1() {
        assertThat(workingXorNet(ONE, ONE).output()).isCloseTo(0, offset(0.01));
    }

    private Neuron nonWorkingXorNet(Neuron a, Neuron b) {
        return Neuron.create(
                random(),
                Synapse.to(
                        Neuron.create(
                                random(),
                                Synapse.to(a, random()),
                                Synapse.to(b, random())
                        ),
                        random()
                ),
                Synapse.to(
                        Neuron.create(
                                random(),
                                Synapse.to(a, random()),
                                Synapse.to(b, random())
                        ),
                        random()
                )
        );
    }

    private double random() {
        return 0.5 - Math.random();
    }

    @Test
    void xor_backpropagate() {
        double learningRate = 7;
        network = nonWorkingXorNet(A, B);

        input(0, 0).andPrintResult(network.output());
        input(0, 1).andPrintResult(network.output());
        input(1, 0).andPrintResult(network.output());
        input(1, 1).andPrintResult(network.output());

        for (int i = 0; i < 1000000; i++) {
            input(0, 0).network.backpropagate(0, learningRate);
            input(1, 0).network.backpropagate(1, learningRate);
            input(0, 1).network.backpropagate(1, learningRate);
            input(1, 1).network.backpropagate(0, learningRate);
        }
        System.out.println("-finished-learning---------------------------");

        input(0, 0).andPrintResult(network.output());
        assertThat(network.getLatestOutput()).isCloseTo(0, offset(0.01));

        input(0, 1).andPrintResult(network.output());
        assertThat(network.getLatestOutput()).isCloseTo(1, offset(0.01));

        input(1, 0).andPrintResult(network.output());
        assertThat(network.getLatestOutput()).isCloseTo(1, offset(0.01));

        input(1, 1).andPrintResult(network.output());
        assertThat(network.getLatestOutput()).isCloseTo(0, offset(0.01));
    }

    private NeuronTest input(double a, double b) {
        A.spoofOutput(a);
        B.spoofOutput(b);
        return this;
    }

    private void andPrintResult(double result) {
        System.out.println(A.output() + ", " + B.output() + " -> " + result);
    }


}

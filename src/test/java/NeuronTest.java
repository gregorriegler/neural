import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public class NeuronTest {

    public static final Neuron ZERO = new MockNeuron(0);
    public static final Neuron ONE = new MockNeuron(1);

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
                Synapse.to(Neuron.create(random(), Synapse.to(a, random()), Synapse.to(b, random())), random()),
                Synapse.to(Neuron.create(random(), Synapse.to(a, random()), Synapse.to(b, random())), random())
        );
    }

    private double random() {
        return 0.5 - Math.random();
    }

    @Test
    void xor_backpropagate() {
        double learningRate = 7;
        MockNeuron inputA = new MockNeuron(0);
        MockNeuron inputB = new MockNeuron(0);
        Neuron result = nonWorkingXorNet(inputA, inputB);


        inputA.spoofOutput(0);
        inputB.spoofOutput(0);
        System.out.println("0, 0 -> " + result.output());

        inputA.spoofOutput(0);
        inputB.spoofOutput(1);
        System.out.println("0, 1 -> " + result.output());

        inputA.spoofOutput(1);
        inputB.spoofOutput(0);
        System.out.println("1, 0 -> " + result.output());

        inputA.spoofOutput(1);
        inputB.spoofOutput(1);
        System.out.println("1, 1 -> " + result.output());

        for (int i = 0; i < 100000; i++) {
            inputA.spoofOutput(0);
            inputB.spoofOutput(0);
            result.backpropagate(0, learningRate);
            inputA.spoofOutput(1);
            inputB.spoofOutput(0);
            result.backpropagate(1, learningRate);
            inputA.spoofOutput(0);
            inputB.spoofOutput(1);
            result.backpropagate(1, learningRate);
            inputA.spoofOutput(1);
            inputB.spoofOutput(1);
            result.backpropagate(0, learningRate);
        }

        System.out.println("-----------------------------");

        inputA.spoofOutput(0);
        inputB.spoofOutput(0);
        double zeroZeroOutput = result.output();
        System.out.println("0, 0 -> " + zeroZeroOutput);
        assertThat(zeroZeroOutput).isCloseTo(0, offset(0.01));

        inputA.spoofOutput(0);
        inputB.spoofOutput(1);
        double zeroOneOutput = result.output();
        System.out.println("0, 1 -> " + zeroOneOutput);
        assertThat(zeroOneOutput).isCloseTo(1, offset(0.01));

        inputA.spoofOutput(1);
        inputB.spoofOutput(0);
        double oneZeroOutput = result.output();
        System.out.println("1, 0 -> " + oneZeroOutput);
        assertThat(oneZeroOutput).isCloseTo(1, offset(0.01));

        inputA.spoofOutput(1);
        inputB.spoofOutput(1);
        double oneOneOutput = result.output();
        System.out.println("1, 1 -> " + oneOneOutput);
        assertThat(oneOneOutput).isCloseTo(0, offset(0.01));
    }


}

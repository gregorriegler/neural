import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuronTest {

    public static final Transmitter ZERO = new MockNeuron(0);

    public static final Transmitter ONE = new MockNeuron(1);

    private Neuron workingXorNet(Transmitter a, Transmitter b) {
        return Neuron.create("EndNeuron", -30,
                Pair.of(Neuron.create("T", -10, Pair.of(a, 20), Pair.of(b, 20)), 20),
                Pair.of(Neuron.create("B", 30, Pair.of(a, -20), Pair.of(b, -20)), 20)
        );
    }

    @Test
    void xor_0_0() {
        assertThat(workingXorNet(ZERO, ZERO).output()).isEqualTo(0);
    }

    @Test
    void xor_0_1() {
        assertThat(workingXorNet(ZERO, ONE).output()).isEqualTo(1);
    }

    @Test
    void xor_1_0() {
        assertThat(workingXorNet(ONE, ZERO).output()).isEqualTo(1);
    }

    @Test
    void xor_1_1() {
        assertThat(workingXorNet(ONE, ONE).output()).isEqualTo(0);
    }

    private Neuron nonWorkingXorNet(Transmitter a, Transmitter b) {
        return Neuron.create("EndNeuron", random(),
                Pair.of(Neuron.create("T", random(), Pair.of(a, random()), Pair.of(b, random())), random()),
                Pair.of(Neuron.create("B", random(), Pair.of(a, random()), Pair.of(b, random())), random())
        );
    }

    private double random() {
        return 0.5 - Math.random();
    }

    @Test
    void xor_backpropagate() {
        double rate = 7;
        MockNeuron inputA = new MockNeuron(0);
        MockNeuron inputB = new MockNeuron(0);
        Neuron result = nonWorkingXorNet(inputA, inputB);


        inputA.setOutput(0);
        inputB.setOutput(0);
        System.out.println("0, 0 -> " + result.output());

        inputA.setOutput(0);
        inputB.setOutput(1);
        System.out.println("0, 1 -> " + result.output());

        inputA.setOutput(1);
        inputB.setOutput(0);
        System.out.println("1, 0 -> " + result.output());

        inputA.setOutput(1);
        inputB.setOutput(1);
        System.out.println("1, 1 -> " + result.output());

        for (int i = 0; i < 100000; i++) {
            inputA.setOutput(0);
            inputB.setOutput(0);
            result.backpropagate(0, rate);
            inputA.setOutput(1);
            inputB.setOutput(0);
            result.backpropagate(1, rate);
            inputA.setOutput(0);
            inputB.setOutput(1);
            result.backpropagate(1, rate);
            inputA.setOutput(1);
            inputB.setOutput(1);
            result.backpropagate(0, rate);
        }

        System.out.println("-----------------------------");

        inputA.setOutput(0);
        inputB.setOutput(0);
        System.out.println("0, 0 -> " + result.output());

        inputA.setOutput(0);
        inputB.setOutput(1);
        System.out.println("0, 1 -> " + result.output());

        inputA.setOutput(1);
        inputB.setOutput(0);
        System.out.println("1, 0 -> " + result.output());

        inputA.setOutput(1);
        inputB.setOutput(1);
        System.out.println("1, 1 -> " + result.output());

    }


}

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuronTest {

    public static final Transmitter ZERO = () -> 0;
    public static final Transmitter ONE = () -> 1;

    @Test
    void simple() {
        //Middle Layer
        Neuron output = Neuron.create(new Pair<>(ZERO, 1d));

        assertThat(output.output()).isEqualTo(1d);
    }

    private Neuron xorNet(Transmitter a, Transmitter b) {
        return Neuron.create(
                new Pair<>(Neuron.create(new Pair<>(a, -0.1d), new Pair<>(b, -0.5d)), -1.7d),
                new Pair<>(Neuron.create(new Pair<>(a, 0.4d), new Pair<>(b, 0.33333333333d)), 0.60d),
                new Pair<>(Neuron.create(new Pair<>(a, -1.2d), new Pair<>(b, -0.5d)), 0.1d)
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


}

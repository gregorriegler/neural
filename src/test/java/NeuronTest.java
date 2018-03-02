import javafx.util.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeuronTest {

    @Test
    void xor() {
        //Input Layer
        Neuron a = Neuron.create();
        Neuron b = Neuron.create();

        //Middle Layer
        Neuron middleTop = Neuron.create(new Pair<>(a, 1d), new Pair<>(b, 1d));
        Neuron middleBottom = Neuron.create(new Pair<>(a, 1d), new Pair<>(b, 1d));

        //Output Layer
        Neuron res1 = Neuron.create(new Pair<>(middleTop, 1d), new Pair<>(middleBottom, 1d));
        Neuron res0 = Neuron.create(new Pair<>(middleTop, 1d), new Pair<>(middleBottom, 1d));

        a.trigger(0);
        b.trigger(0);
        middleTop.live();
        middleBottom.live();
        assertThat(res1.result()).isEqualTo(0d);
        assertThat(res0.result()).isEqualTo(0d);
    }
}

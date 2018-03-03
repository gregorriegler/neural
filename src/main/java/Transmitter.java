public interface Transmitter {
    double output();

    void backpropagate(double expected, double rate);

    void hiddenBackpropagate(double descendantError, double currentWeight, double rate);

    double getLatestOutput();

    void setBias(double bias);

    double getBias();
}

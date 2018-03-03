public interface Transmitter {
    double output();

    void backpropagate(double expected);

    void hiddenBackpropagate(double delta);

    double getLatestOutput();

    void setBias(double bias);

    double getBias();
}

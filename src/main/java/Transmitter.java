public interface Transmitter {
    double output();

    void backpropagate(double expected);

    double getLatestOutput();
}

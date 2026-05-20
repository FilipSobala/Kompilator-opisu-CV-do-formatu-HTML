package org.example;

public class NumberNode extends ValueNode {

    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

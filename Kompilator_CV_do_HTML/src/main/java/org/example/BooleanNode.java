package org.example;

public class BooleanNode extends ValueNode {

    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

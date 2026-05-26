package org.example;

public class FieldNode implements Node {

    private final String key;

    private final ValueNode value;

    public FieldNode(String key, ValueNode value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public ValueNode getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " = " + value;
    }
    @Override
    public String toHtml() {
        return "<div class=\"field\"><span class=\"key\">" + key + "</span>"
                + value.toHtml() + "</div>";
    }
}

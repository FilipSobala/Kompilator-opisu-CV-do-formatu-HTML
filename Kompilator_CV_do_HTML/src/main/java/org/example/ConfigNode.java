package org.example;

import java.util.ArrayList;
import java.util.List;

public class ConfigNode implements Node {

    private final List<FieldNode> fields = new ArrayList<>();

    public void add(FieldNode f) {
        fields.add(f);
    }

    public List<FieldNode> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CONFIG\n");

        for (FieldNode f : fields) {
            sb.append("  ").append(f).append("\n");
        }

        return sb.toString();
    }
}

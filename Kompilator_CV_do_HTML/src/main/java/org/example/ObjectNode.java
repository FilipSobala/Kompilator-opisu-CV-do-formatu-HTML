package org.example;

import java.util.ArrayList;
import java.util.List;

public class ObjectNode extends ValueNode {

    private final List<FieldNode> fields = new ArrayList<>();

    public void addField(FieldNode field) {
        fields.add(field);
    }

    public List<FieldNode> getFields() {
        return fields;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");

        for (FieldNode f : fields) {
            sb.append(f).append(" ");
        }

        sb.append("}");
        return sb.toString();
    }
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"object\">");
        for (FieldNode f : fields) {
            sb.append(f.toHtml());
        }
        sb.append("</div>");
        return sb.toString();
    }
}
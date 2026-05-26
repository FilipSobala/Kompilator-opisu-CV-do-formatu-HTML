package org.example;

import java.util.ArrayList;
import java.util.List;

public class SectionNode implements Node {

    private final String name;

    private final List<Node> content = new ArrayList<>();

    public SectionNode(String name) {
        this.name = name;
    }

    public void add(Node node) {
        content.add(node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  SECTION: ").append(name).append("\n");

        for (Node n : content) {
            sb.append("    ").append(n).append("\n");
        }

        return sb.toString();
    }
    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<section id=\"").append(name).append("\">");
        sb.append("<h2>").append(name.replace("_", " ")).append("</h2>");
        for (Node n : content) {
            sb.append(n.toHtml());
        }
        sb.append("</section>");
        return sb.toString();
    }
}

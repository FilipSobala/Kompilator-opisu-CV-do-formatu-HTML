package org.example;

import java.util.ArrayList;
import java.util.List;

public class ListNode extends ValueNode {

    private final List<ValueNode> items = new ArrayList<>();

    public void addItem(ValueNode node) {
        items.add(node);
    }

    public List<ValueNode> getItems() {
        return items;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i));
            if (i < items.size() - 1) sb.append(", ");
        }

        sb.append("]");
        return sb.toString();
    }
}
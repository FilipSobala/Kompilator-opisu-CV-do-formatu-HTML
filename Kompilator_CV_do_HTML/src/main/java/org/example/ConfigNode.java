package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigNode implements Node {

    private static final Set<String> KNOWN_FIELDS = Set.of(
            "LANG", "THEME", "SHOW_PHOTO", "ACCENT_COLOR", "EXPORT_PDF"
    );
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
    @Override
    public String toHtml() {
        return "";
    }
    public String getField(String key) {
        return fields.stream()
                .filter(f -> f.getKey().equals(key))
                .map(f -> f.getValue().toString().replace("\"", ""))
                .findFirst()
                .orElse(null);
    }
    public boolean getBooleanField(String key) {
        return "TRUE".equalsIgnoreCase(key);
    }
    public void validate() {
        for (FieldNode f : fields) {
            if (!KNOWN_FIELDS.contains(f.getKey())) {
                System.err.println("[CONFIG] Nieznane pole: " + f.getKey() + " — ignoruję");
            }
        }
    }
}

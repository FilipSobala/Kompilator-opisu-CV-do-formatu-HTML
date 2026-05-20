package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cv  implements Node{

    private ConfigNode config;
    private final List<SectionNode> sections = new ArrayList<>();

    public void addSection(SectionNode section) {
        sections.add(section);
    }

    public List<SectionNode> getSections() {
        return sections;
    }
    public void setConfig(ConfigNode config) {
        this.config = config;
    }
    public ConfigNode getConfig() {
        return config;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CV\n");
        if (config != null) {
            sb.append(config).append("\n");
        }

        for (SectionNode s : sections) {
            sb.append(s).append("\n");
        }

        return sb.toString();
    }
}
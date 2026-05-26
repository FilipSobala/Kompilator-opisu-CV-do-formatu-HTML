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
    @Override
    public String toHtml() {
        if (config != null) config.validate();

        String lang= config != null ? config.getField("LANG") : "pl";
        String theme=config != null ? config.getField("THEME") : "default";
        String accent= config != null ? config.getField("ACCENT_COLOR") : "#4CAF50";
        boolean showPhoto= config != null && config.getBooleanField("SHOW_PHOTO");

        if (lang   == null) lang   = "pl";
        if (accent == null) accent = "#4CAF50";
        if (theme  == null) theme  = "default";

        boolean darkMode = theme.contains("Dark");
        String bg = darkMode ? "#1e1e1e" : "#ffffff";
        String textColor = darkMode ? "#f0f0f0" : "#222222";
        String cardBg = darkMode ? "#2a2a2a" : "#f9f9f9";

        StringBuilder sb = new StringBuilder();
        sb.append("""
            <!DOCTYPE html>
            <html lang="%s">
            <head>
                <meta charset="UTF-8">
                <title>CV</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        max-width: 900px;
                        margin: 40px auto;
                        padding: 20px;
                        background: %s;
                        color: %s;
                    }
                    h2 {
                        border-bottom: 2px solid %s;
                        padding-bottom: 4px;
                        color: %s;
                    }
                    .field { margin: 6px 0; }
                    .key {
                        font-weight: bold;
                        margin-right: 8px;
                        color: %s;
                    }
                    .object {
                        border-left: 3px solid %s;
                        padding-left: 12px;
                        margin: 8px 0;
                        background: %s;
                        border-radius: 4px;
                        padding: 8px 12px;
                    }
                </style>
            </head>
            
            """.formatted(lang, bg, textColor, accent, textColor, accent, accent, cardBg));
        for (SectionNode s : sections) {
            sb.append(s.toHtml());
        }


        sb.append("</body></html>");
        return sb.toString();
    }
}
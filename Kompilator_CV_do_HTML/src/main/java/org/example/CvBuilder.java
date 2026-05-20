package org.example;

import org.example.cv.antlr.*;
import java.util.ArrayList;

public class CvBuilder extends CvDslBaseVisitor<Node> {
    @Override
    public Node visitCv_document(CvDslParser.Cv_documentContext ctx) {

        Cv cv = new Cv();

        if (ctx.config_block() != null) {
            cv.setConfig((ConfigNode) visit(ctx.config_block()));
        }
        for (CvDslParser.SectionContext s : ctx.section()) {
            cv.addSection((SectionNode) visit(s));
        }

        return cv;
    }
    @Override
    public Node visitConfig_block(CvDslParser.Config_blockContext ctx) {

        ConfigNode config = new ConfigNode();

        for (CvDslParser.PairContext p : ctx.pair()) {
            config.add((FieldNode) visit(p));
        }

        return config;
    }
    @Override
    public Node visitSection(CvDslParser.SectionContext ctx) {

        SectionNode section = new SectionNode(ctx.T_LABEL().getText());

        for (CvDslParser.ContentContext c : ctx.content()) {
            section.add(visit(c));
        }

        return section;
    }
    @Override
    public Node visitPair(CvDslParser.PairContext ctx) {

        String key = ctx.T_KEY().getText().replace(":", "");
        ValueNode value = visitValue(ctx.value());

        return new FieldNode(key, value);
    }
    @Override
    public Node visitList_field(CvDslParser.List_fieldContext ctx) {
        String key = stripKey(ctx.T_KEY().getText());
        ListNode list = new ListNode();

        for (CvDslParser.ValueContext v : ctx.value()) {
            list.addItem(visitValue(v));
        }

        return new FieldNode(key, list);
    }
    @Override
    public Node visitBullet_list(CvDslParser.Bullet_listContext ctx) {
        String key = stripKey(ctx.T_KEY().getText());
        ListNode list = new ListNode();

        for (CvDslParser.ValueContext v : ctx.value()) {
            list.addItem(visitValue(v));
        }

        return new FieldNode(key, list);
    }
    @Override
    public Node visitObject_list(CvDslParser.Object_listContext ctx) {
        String key = stripKey(ctx.T_KEY().getText());
        ListNode list = new ListNode();

        for (CvDslParser.Object_blockContext obj : ctx.object_block()) {
            list.addItem((ObjectNode) visit(obj));
        }

        return new FieldNode(key, list);
    }
    @Override
    public Node visitObject_block(CvDslParser.Object_blockContext ctx) {ObjectNode obj = new ObjectNode();

        for (CvDslParser.ContentContext c : ctx.content()) {
            Node node = visit(c);

            if (node instanceof FieldNode f) {
                obj.addField(f);
            }
        }

        return obj;
    }
    public ValueNode visitValue(CvDslParser.ValueContext ctx) {
        if (ctx.T_STRING() != null)
            return new StringNode(strip(ctx.T_STRING().getText()));

        if (ctx.T_MULTILINE() != null)
            return new StringNode(stripMulti(ctx.T_MULTILINE().getText()));

        if (ctx.T_NUMBER() != null)
            return new NumberNode(Integer.parseInt(ctx.T_NUMBER().getText()));

        if (ctx.T_BOOLEAN() != null)
            return new BooleanNode(ctx.T_BOOLEAN().getText().equalsIgnoreCase("true"));

        if (ctx.T_URL() != null)
            return new StringNode(ctx.T_URL().getText());

        if (ctx.T_EMAIL() != null)
            return new StringNode(ctx.T_EMAIL().getText());

        if (ctx.T_PHONE() != null)
            return new StringNode(ctx.T_PHONE().getText());

        if (ctx.T_DATE() != null)
            return new StringNode(ctx.T_DATE().getText());

        if (ctx.T_PRESENT() != null)
            return new StringNode(ctx.T_PRESENT().getText());

        throw new IllegalArgumentException(
                "Nieznany typ wartości w kontekście: " + ctx.getText()
        );
    }
    private String stripKey(String s) {
        return s.endsWith(":") ? s.substring(0, s.length() - 1) : s;
    }
    private String strip(String s) {
        if (s.length() < 2) return s;
        return s.substring(1, s.length() - 1);
    }

    private String stripMulti(String s) {
        return s.replaceAll("^\"\"\"|\"\"\"$", "").strip();
    }

}
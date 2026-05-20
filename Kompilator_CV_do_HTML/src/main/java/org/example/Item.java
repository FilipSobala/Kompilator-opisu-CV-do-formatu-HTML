package org.example;


import java.util.ArrayList;

public class Item {
    public  String type;
    public String data;
    public ArrayList<String> lista;
    public Item(String type, String data, ArrayList<String> lista) {
        this.type = type;
        this.data = data;
        this.lista = lista;
    }
    public Item(String type, String data) {
        this(type, data, new ArrayList<>());
    }
}

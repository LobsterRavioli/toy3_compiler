package main.visitors.utils;

import main.node.Type;

import java.util.ArrayList;

public interface Firm {
    public Firm clone() ;
    Type getType();
    ArrayList<Type> getMultipleTypes();
}

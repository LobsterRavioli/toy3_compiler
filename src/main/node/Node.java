package main.node;

// Nodo dell'albero
public interface Node {
    Object accept(Visitor visitor);
}

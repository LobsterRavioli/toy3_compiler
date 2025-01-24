package main.node.stat;

import main.node.Node;
import main.node.Visitor;

public interface StatementOperationNode extends Node {
    Object accept(Visitor visitor);
}

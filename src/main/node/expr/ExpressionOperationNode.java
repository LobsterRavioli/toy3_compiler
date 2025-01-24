package main.node.expr;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;

public interface ExpressionOperationNode extends Node {
    Object accept(Visitor visitor);
    Type getType();
    void setType(Type type);

}

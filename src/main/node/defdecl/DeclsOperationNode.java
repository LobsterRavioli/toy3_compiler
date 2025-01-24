package main.node.defdecl;

import main.node.Node;
import main.node.Visitor;

public interface DeclsOperationNode extends Node {
    Object accept(Visitor visitor);
}

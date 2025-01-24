package main.node.pardecl;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.expr.constants.IdentifierNode;
import main.visitors.utils.SymbolTable;

public class ParenthesisVariablesNode implements Node {

    private SymbolTable table;

    private Type type;
    private boolean hasRef;
    private IdentifierNode id;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }


    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ParenthesisVariablesNode(boolean hasRef, Object id) {
        this.hasRef = (boolean) hasRef;
        this.id = (IdentifierNode) id;
    }

    @Override
    public String toString() {
        return "ParenthesisVariablesNode{" +
                "hasRef=" + hasRef +
                ", id=" + id +
                '}';
    }

    public boolean isHasRef() {
        return hasRef;
    }

    public IdentifierNode getId() {
        return id;
    }

    public void setHasRef(boolean hasRef) {
        this.hasRef = hasRef;
    }


}

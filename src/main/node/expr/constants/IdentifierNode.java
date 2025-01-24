package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class IdentifierNode implements ExpressionOperationNode {

    private SymbolTable table;
    private Type type;
    boolean hasRef;

    public boolean isHasRef() {
        return hasRef;
    }

    public void setHasRef(boolean hasRef) {
        this.hasRef = hasRef;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public IdentifierNode(Object name) {
        this.name = (String) name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "IdentifierNode{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }


}
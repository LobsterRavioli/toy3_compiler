package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;
;

public class CharConstantNode implements ExpressionOperationNode {
    private SymbolTable table;

    private Type type;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public CharConstantNode(Object c) {
        this.constant = (char) c;
    }
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    private final char constant;

    public char getConstant() {
        return constant;
    }
    @Override
    public String toString() {
        return "CharConstantNode{" +
                "constant='" + constant + '\'' +
                '}';
    }
}

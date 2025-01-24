package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class FalseConstantNode implements ExpressionOperationNode {

    private SymbolTable table;

    private Type type;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    private boolean constant;

    public FalseConstantNode() {
        this.constant = false;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type=type;
    }

}

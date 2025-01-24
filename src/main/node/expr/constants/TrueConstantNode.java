package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class TrueConstantNode implements ExpressionOperationNode {

    public TrueConstantNode() {
        this.constant = true;
    }
    private Type type;

    private SymbolTable table;

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

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    private boolean constant;


}

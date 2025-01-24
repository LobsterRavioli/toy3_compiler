package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class StringConstantNode implements ExpressionOperationNode {

    private String constant;
    private SymbolTable table;
    private Type type;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public StringConstantNode(Object constant){
        this.constant = (String) constant;
    }

    public String getConstant() {
        return constant;
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


    @Override
    public String toString() {
        return "StringConstantNode{" +
                "constant='" + constant + '\'' +
                '}';
    }
}

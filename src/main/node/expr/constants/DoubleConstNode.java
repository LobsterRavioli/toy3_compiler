package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class DoubleConstNode implements ExpressionOperationNode {

    private Type type;
    private SymbolTable table;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public DoubleConstNode(Object c){
        this.constant = (double) c;
    }

    public Double getConstant() {
        return constant;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    private Double constant;

    @Override
    public String toString() {
        return "DoubleConstNode{" +
                "constant=" + constant +
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

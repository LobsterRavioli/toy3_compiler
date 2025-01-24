package main.node.expr.constants;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class IntegerConstNode implements ExpressionOperationNode {

    private Type type;
    private SymbolTable table;
    private int constant;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public IntegerConstNode(Object constant){
        this.constant = (int) constant;
    }

    public int getConstant() {
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
        return "IntegerConstNode{" +
                "constant=" + constant +
                '}';
    }



}

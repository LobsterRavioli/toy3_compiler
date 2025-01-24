package main.node.expr.operations;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class UnaryOperationNode  implements ExpressionOperationNode {

    private SymbolTable table;

    private Type type;
    private final String operator;
    private final ExpressionOperationNode child;

    public UnaryOperationNode(Object operator, Object child) {
        this.operator =  (String) operator;
        this.child = (ExpressionOperationNode) child;
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
        return "UnaryOperation{" +
                "operationType=" + operator +
                ", child=" + child +
                '}';
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionOperationNode getChild() {
        return child;
    }


}
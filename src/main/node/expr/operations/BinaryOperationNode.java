package main.node.expr.operations;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class BinaryOperationNode implements ExpressionOperationNode {

    private Type type;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }



    private SymbolTable table;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    private final String operator;
    private final ExpressionOperationNode left;
    private final ExpressionOperationNode right;

    public BinaryOperationNode(Object left,Object operator, Object right) {
        this.operator =  (String) operator;
        this.left = (ExpressionOperationNode) left;
        this.right = (ExpressionOperationNode) right;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ExpressionOperationNode getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionOperationNode getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return "BinaryOperationNode{" +
                "operationType=" + operator +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
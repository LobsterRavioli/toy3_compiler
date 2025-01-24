package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class WhileOperationNode implements StatementOperationNode {
    private SymbolTable table;
    private ExpressionOperationNode expression;
    private BodyOperationNode body;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public WhileOperationNode(Object expression, Object body) {
        this.expression = (ExpressionOperationNode)expression;
        this.body = (BodyOperationNode) body;
    }

    public ExpressionOperationNode getExpression() {
        return expression;
    }

    public BodyOperationNode getBody() {
        return body;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "WhileOperationNode{" +
                "expression=" + expression +
                ", body=" + body +
                '}';
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

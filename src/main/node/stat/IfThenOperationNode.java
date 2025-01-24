package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class IfThenOperationNode implements StatementOperationNode {

    private SymbolTable table;
    private Type type;

    private ExpressionOperationNode expression;
    private BodyOperationNode body;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public IfThenOperationNode(Object expression, Object body) {
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
        return "IfThenOperationNode{" +
                "expression=" + expression +
                ", body=" + body +
                '}';
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

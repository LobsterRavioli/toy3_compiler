package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class IfThenElseOperationNode implements StatementOperationNode {

    private Type type;
    private SymbolTable table;
    ExpressionOperationNode expression;
    BodyOperationNode ifBody;
    BodyOperationNode elseBody;

    public IfThenElseOperationNode(Object expression, Object ifBody, Object elseBody){
        this.expression = (ExpressionOperationNode) expression;
        this.ifBody = (BodyOperationNode) ifBody;
        this.elseBody = (BodyOperationNode) elseBody;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ExpressionOperationNode getExpression() {
        return expression;
    }

    public BodyOperationNode getIfBody() {
        return ifBody;
    }

    public BodyOperationNode getElseBody() {
        return elseBody;
    }

    @Override
    public String toString() {
        return "IfThenElseOperationNode{" +
                "expression=" + expression +
                ", ifBody=" + ifBody +
                ", elseBody=" + elseBody +
                '}';
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

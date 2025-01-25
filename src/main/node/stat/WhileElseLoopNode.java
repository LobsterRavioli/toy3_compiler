package main.node.stat;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class WhileElseLoopNode implements StatementOperationNode {
    private SymbolTable whiletable;
    private SymbolTable elseTable;

    private ExpressionOperationNode expression;
    private BodyOperationNode whileBody;
    private BodyOperationNode elseLoopBody;

    public SymbolTable getWhiletable() {
        return whiletable;
    }

    public void setWhiletable(SymbolTable whiletable) {
        this.whiletable = whiletable;
    }

    public SymbolTable getElseTable() {
        return elseTable;
    }

    public void setElseTable(SymbolTable elseTable) {
        this.elseTable = elseTable;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }


    public WhileElseLoopNode(Object expression, Object whileBody, Object elseLoopBody) {
        this.expression = (ExpressionOperationNode) expression;
        this.whileBody = (BodyOperationNode) whileBody;
        this.elseLoopBody = (BodyOperationNode) elseLoopBody;
    }

    public ExpressionOperationNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionOperationNode expression) {
        this.expression = expression;
    }

    public BodyOperationNode getWhileBody() {
        return whileBody;
    }

    public void setWhileBody(BodyOperationNode whileBody) {
        this.whileBody = whileBody;
    }

    public BodyOperationNode getElseLoopBody() {
        return elseLoopBody;
    }

    public void setElseLoopBody(BodyOperationNode elseLoopBody) {
        this.elseLoopBody = elseLoopBody;
    }




    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

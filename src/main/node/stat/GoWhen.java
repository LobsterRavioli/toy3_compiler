package main.node.stat;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class GoWhen implements Node {

    private Type type;
    private ExpressionOperationNode whenCondition;
    private BodyOperationNode body;
    private SymbolTable symbolTable;

    public GoWhen(Object whenCondition, Object statements) {
        this.whenCondition = (ExpressionOperationNode) whenCondition;
        this.body = (BodyOperationNode) statements;
    }

    public ExpressionOperationNode getWhenCondition() {
        return whenCondition;
    }

    public void setWhenCondition(ExpressionOperationNode whenCondition) {
        this.whenCondition = whenCondition;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public BodyOperationNode getBody() {
        return body;
    }

    public void setBody(BodyOperationNode body) {
        this.body = body;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

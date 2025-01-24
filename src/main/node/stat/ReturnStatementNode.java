package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

public class ReturnStatementNode implements StatementOperationNode {

    private SymbolTable table;

    private Type type;


    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    private ExpressionOperationNode result;

    public ReturnStatementNode(Object node){
        this.result = (ExpressionOperationNode) node;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReturnStatementNode{" +
                "result=" + result +
                '}';
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ExpressionOperationNode getResult() {
        return result;
    }
}

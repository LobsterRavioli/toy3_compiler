package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class WriteOperationNode implements StatementOperationNode {

    private ArrayList<ExpressionOperationNode> expressions;
    private boolean newLine; // True per newline, false per null.

    private SymbolTable table;

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public WriteOperationNode(ArrayList<ExpressionOperationNode> expressions, boolean newLine) {
        this.expressions = expressions;
        this.newLine = newLine;
    }

    public WriteOperationNode() {
        this.expressions = new ArrayList<>();
        this.newLine = false;
    }

    public ArrayList<ExpressionOperationNode> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<ExpressionOperationNode> expressions) {
        this.expressions = expressions;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }

    public void addExpression(ExpressionOperationNode expression) {
        this.expressions.add(expression);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "WriteOperationNode{" +
                "expressions=" + expressions +
                ", newLine=" + (newLine ? "true" : "false") +
                '}';
    }


}
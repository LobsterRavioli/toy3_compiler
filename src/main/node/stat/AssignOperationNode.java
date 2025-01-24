package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.IdentifierNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class AssignOperationNode implements StatementOperationNode {

    private SymbolTable table;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    private List<IdentifierNode> identifiers;
    private List<ExpressionOperationNode> expressions;

    public AssignOperationNode(List<IdentifierNode> identifiers, List<ExpressionOperationNode> expressions) {
        this.identifiers = identifiers;
        this.expressions = expressions;
    }

    public AssignOperationNode() {
        this.identifiers = new ArrayList<>();
        this.expressions = new ArrayList<>();
    }

    public List<IdentifierNode> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<IdentifierNode> identifiers) {
        this.identifiers = identifiers;
    }

    public List<ExpressionOperationNode> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionOperationNode> expressions) {
        this.expressions = expressions;
    }

    public void addAssignment(IdentifierNode identifier, ExpressionOperationNode expression) {
        this.identifiers.add(identifier);
        this.expressions.add(expression);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "AssignOperationNode{" +
                "identifiers=" + identifiers +
                ", expressions=" + expressions +
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
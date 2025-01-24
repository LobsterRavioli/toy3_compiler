package main.node.vardecl;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.IdentifierNode;
import main.visitors.utils.SymbolTable;

public class VariableOptionalInitializerNodeOperator implements Node {
    private IdentifierNode id;
    private ExpressionOperationNode expr=null;
    private SymbolTable table;
    private Type returnType;
    public VariableOptionalInitializerNodeOperator(Object id, Object expr) {
        this.id = (IdentifierNode) id;
        this.expr = (ExpressionOperationNode) expr;
    }
    public VariableOptionalInitializerNodeOperator(Object id) {
        this.id = (IdentifierNode) id;
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

    public IdentifierNode getId() {
        return id;
    }

    public ExpressionOperationNode getExpr() {
        return expr;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "VariableOptionalInitializerNodeOperator{" +
                "id=" + id +
                ", expr=" + expr +
                ", table=" + table +
                ", returnType=" + returnType +
                '}';
    }
}

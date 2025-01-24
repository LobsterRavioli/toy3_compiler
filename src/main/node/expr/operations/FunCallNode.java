package main.node.expr.operations;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.IdentifierNode;
import main.node.stat.StatementOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class FunCallNode implements ExpressionOperationNode, StatementOperationNode {

    private ArrayList<Type> inputTypes;
    private SymbolTable table;
    private Type type;
    private ArrayList<ExpressionOperationNode> expressions;
    private IdentifierNode id;

    public ArrayList<Type> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(ArrayList<Type> inputTypes) {
        this.inputTypes = inputTypes;
    }

    public FunCallNode(Object id, Object expressions){
        this.id = (IdentifierNode) id;
        this.expressions= (ArrayList<ExpressionOperationNode>) expressions;
    }

    public FunCallNode(Object id){
        this.id = (IdentifierNode) id;
        this.expressions= new ArrayList<>();
    }
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FunCallNode{" +
                "inputTypes=" + inputTypes +
                ", table=" + table +
                ", type=" + type +
                ", expressions=" + expressions +
                ", id=" + id +
                '}';
    }

    public ArrayList<ExpressionOperationNode> getExpressions() {
        return expressions;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public IdentifierNode getId() {
        return id;
    }
}

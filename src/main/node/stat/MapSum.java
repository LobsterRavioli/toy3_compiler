package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.IdentifierNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class MapSum implements StatementOperationNode,ExpressionOperationNode{
    private SymbolTable table;
    private Type type;
    private IdentifierNode id;
    private ArrayList<ExpressionOperationNode> expression1;
    private ArrayList<ExpressionOperationNode> expression2;
    private ArrayList<ExpressionOperationNode> expression3;

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

    public ArrayList<ExpressionOperationNode> getExpression1() {
        return expression1;
    }

    public void setExpression1(ArrayList<ExpressionOperationNode> expression1) {
        this.expression1 = expression1;
    }

    public IdentifierNode getId() {
        return id;
    }

    public void setId(IdentifierNode id) {
        this.id = id;
    }

    public ArrayList<ExpressionOperationNode> getExpression2() {
        return expression2;
    }

    public void setExpression2(ArrayList<ExpressionOperationNode> expression2) {
        this.expression2 = expression2;
    }

    public ArrayList<ExpressionOperationNode> getExpression3() {
        return expression3;
    }

    public void setExpression3(ArrayList<ExpressionOperationNode> expression3) {
        this.expression3 = expression3;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public MapSum(Object id, ArrayList<ExpressionOperationNode> expression1, ArrayList<ExpressionOperationNode> expression2, ArrayList<ExpressionOperationNode> expression3) {
        this.id = (IdentifierNode) id;
        this.expression1 = (ArrayList<ExpressionOperationNode>) expression1;
        this.expression2 = (ArrayList<ExpressionOperationNode>) expression2;
        this.expression3 = (ArrayList<ExpressionOperationNode>) expression3;
    }
}

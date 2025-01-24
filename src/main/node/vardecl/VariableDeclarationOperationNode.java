package main.node.vardecl;

import main.node.Type;
import main.node.Visitor;
import main.node.defdecl.DeclsOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class VariableDeclarationOperationNode implements DeclsOperationNode {

    private ArrayList<VariableOptionalInitializerNodeOperator> variables;
    private Type type;
    private ExpressionOperationNode constant;
    private SymbolTable table;


    // Costruttore che determina se riempire 'type' o 'constant'
    public VariableDeclarationOperationNode(Object variablesList, Object typeOrConstant) {
        this.variables = (ArrayList<VariableOptionalInitializerNodeOperator>) variablesList;

        if (typeOrConstant instanceof Type) {
            this.type = (Type) typeOrConstant;
        } else if (typeOrConstant instanceof ExpressionOperationNode) {
            this.constant = (ExpressionOperationNode) typeOrConstant;
        } else {
            throw new IllegalArgumentException("Invalid type for typeOrConstant: " + typeOrConstant.getClass().getName());
        }
    }

    public ArrayList<VariableOptionalInitializerNodeOperator> getVariables() {
        return variables;
    }

    public Type getType() {
        return type;
    }

    public ExpressionOperationNode getConstant() {
        return constant;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "VariableDeclarationOperationNode{" +
                "variables=" + variables +
                ", type=" + (type != null ? type : "null") +
                ", constant=" + (constant != null ? constant : "null") +
                '}';
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }
}
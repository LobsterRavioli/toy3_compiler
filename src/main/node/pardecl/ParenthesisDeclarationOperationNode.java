package main.node.pardecl;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class ParenthesisDeclarationOperationNode implements Node{

    SymbolTable table;
    private ArrayList<ParenthesisVariablesNode> variables;
    private Type type;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }



    // Costruttore che determina se riempire 'type' o 'constant'
    public ParenthesisDeclarationOperationNode(Object parenthesisVariableList, Object type) {
        this.variables = (ArrayList<ParenthesisVariablesNode>) parenthesisVariableList;
        this.type = (Type) type;
    }

    public ArrayList<ParenthesisVariablesNode> getVariables() {
        return variables;
    }

    public Type getType() {
        return type;
    }



    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ParenthesisDeclarationOperationNode{" +
                "variables=" + variables +
                ", type=" + type +
                '}';
    }
}

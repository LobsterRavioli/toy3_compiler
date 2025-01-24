package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.defdecl.DeclsOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class LetOperationNode implements StatementOperationNode{
    private Type type;


    private ArrayList<StatementOperationNode> statements;
    private ArrayList<DeclsOperationNode> declerations;

    private SymbolTable table;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<StatementOperationNode> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementOperationNode> statements) {
        this.statements = statements;
    }

    public ArrayList<DeclsOperationNode> getDeclerations() {
        return declerations;
    }

    public void setDeclerations(ArrayList<DeclsOperationNode> declerations) {
        this.declerations = declerations;
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

    public LetOperationNode(ArrayList<DeclsOperationNode> declerations, ArrayList<StatementOperationNode> statements) {
        this.declerations = declerations;
        this.statements = statements;
    }
}

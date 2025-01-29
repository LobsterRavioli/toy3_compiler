package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class LetGoWhen implements StatementOperationNode{

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private ArrayList<VariableDeclarationOperationNode> decls;
    private GoWhen firstGoWhen;
    private GoWhen secondGowhen;
    private ArrayList<StatementOperationNode> otherStatements;
    private SymbolTable table;


    public LetGoWhen(Object decls, Object firstGoWhen, Object secondGowhen, Object otherStatements) {
        this.decls = (ArrayList<VariableDeclarationOperationNode>) decls;
        this.firstGoWhen = (GoWhen) firstGoWhen;
        this.secondGowhen = (GoWhen) secondGowhen;
        this.otherStatements = (ArrayList<StatementOperationNode>)otherStatements;
    }

    public ArrayList<VariableDeclarationOperationNode> getDecls() {
        return decls;
    }

    public void setDecls(ArrayList<VariableDeclarationOperationNode> decls) {
        this.decls = decls;
    }

    public GoWhen getFirstGoWhen() {
        return firstGoWhen;
    }

    public void setFirstGoWhen(GoWhen firstGoWhen) {
        this.firstGoWhen = firstGoWhen;
    }

    public GoWhen getSecondGowhen() {
        return secondGowhen;
    }

    public void setSecondGowhen(GoWhen secondGowhen) {
        this.secondGowhen = secondGowhen;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public ArrayList<StatementOperationNode> getOtherStatements() {
        return otherStatements;
    }

    public void setOtherStatements(ArrayList<StatementOperationNode> otherStatements) {
        this.otherStatements = otherStatements;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LetGoWhen{" +
                "firstGoWhen=" + firstGoWhen +
                ", secondGowhen=" + secondGowhen +
                ", otherStatements=" + otherStatements +
                ", table=" + table +
                '}';
    }
}

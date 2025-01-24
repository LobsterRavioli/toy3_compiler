package main.node.program;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.defdecl.DeclsOperationNode;
import main.node.stat.StatementOperationNode;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class ProgramOperationNode implements Node {
    private SymbolTable programTable;
    private SymbolTable beginEndTable;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getProgramTable() {
        return programTable;
    }

    public void setProgramTable(SymbolTable programTable) {
        this.programTable = programTable;
    }

    public SymbolTable getBeginEndTable() {
        return beginEndTable;
    }

    public void setBeginEndTable(SymbolTable beginEndTable) {
        this.beginEndTable = beginEndTable;
    }

    private ArrayList<DeclsOperationNode> outerDeclaration;
    private ArrayList<VariableDeclarationOperationNode> innerDeclaration;
    private ArrayList<StatementOperationNode> statements;

    public ProgramOperationNode(ArrayList<DeclsOperationNode> outerDeclaration, ArrayList<VariableDeclarationOperationNode> innerDeclaration, ArrayList<StatementOperationNode> statements) {
        this.outerDeclaration = (ArrayList<DeclsOperationNode>)outerDeclaration;
        this.innerDeclaration = (ArrayList<VariableDeclarationOperationNode>) innerDeclaration;
        this.statements = (ArrayList<StatementOperationNode>) statements;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ProgramOperationNode{" +
                "outerDeclaration=" + outerDeclaration +
                ", innerDeclaration=" + innerDeclaration +
                ", statements=" + statements +
                '}';
    }

    public ArrayList<DeclsOperationNode> getOuterDeclaration() {
        return outerDeclaration;
    }

    public ArrayList<VariableDeclarationOperationNode> getInnerDeclaration() {
        return innerDeclaration;
    }

    public ArrayList<StatementOperationNode> getStatements() {
        return statements;
    }


}

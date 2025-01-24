package main.node.body;

import main.node.Node;
import main.node.Type;
import main.node.Visitor;
import main.node.stat.StatementOperationNode;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class BodyOperationNode implements Node {


    private ArrayList<VariableDeclarationOperationNode> declarations;
    private ArrayList<StatementOperationNode> statements;

    private SymbolTable table;

    private Type type;

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

    public BodyOperationNode(ArrayList<VariableDeclarationOperationNode> declarations, ArrayList<StatementOperationNode> statements) {
        this.declarations = declarations != null ? declarations : new ArrayList<>();
        this.statements = statements != null ? statements : new ArrayList<>();
    }

    // Getter for declarations
    public ArrayList<VariableDeclarationOperationNode> getDeclarations() {
        return declarations;
    }

    // Getter for statements
    public ArrayList<StatementOperationNode> getStatements() {
        return statements;
    }

    // Add a single declaration
    public void addDeclaration(VariableDeclarationOperationNode declaration) {
        if (declaration != null) {
            declarations.add(declaration);
        }
    }

    // Add a single statement
    public void addStatement(StatementOperationNode statement) {
        if (statement != null) {
            statements.add(statement);
        }
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BodyOperationNode{" +
                "declarations=" + declarations +
                ", statements=" + statements +
                '}';
    }
}
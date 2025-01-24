package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.expr.constants.IdentifierNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class ReadOperationNode  implements StatementOperationNode {

    private ArrayList<IdentifierNode> list;
    private SymbolTable table;

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public ReadOperationNode(ArrayList<IdentifierNode> list) {
        this.list = list;
    }

    public ReadOperationNode() {
        this.list = new ArrayList<>();
    }

    public ArrayList<IdentifierNode> getList() {
        return list;
    }

    public void setList(ArrayList<IdentifierNode> list) {
        this.list = list;
    }

    public void addIdentifier(IdentifierNode identifier) {
        this.list.add(identifier);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReadOperationNode{" +
                "list=" + list +
                '}';
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;
}
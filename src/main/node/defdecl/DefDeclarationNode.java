package main.node.defdecl;

import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.constants.IdentifierNode;
import main.node.pardecl.ParenthesisDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

import java.util.ArrayList;

public class DefDeclarationNode implements DeclsOperationNode {

    private ArrayList<ParenthesisDeclarationOperationNode> list;
    private IdentifierNode id;
    private Type type;
    private BodyOperationNode body;
    private SymbolTable table;

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }

    public DefDeclarationNode(Object list, Object id, Object type, Object body) {
        this.list = (ArrayList<ParenthesisDeclarationOperationNode>)list;
        this.id = (IdentifierNode) id;
        this.type = (Type) type;
        this.body = (BodyOperationNode) body;
    }

    public DefDeclarationNode(Object id, Object type, Object body) {
        this.list = (ArrayList<ParenthesisDeclarationOperationNode>)list;
        this.id = (IdentifierNode) id;
        this.type = (Type) type;
        this.body = (BodyOperationNode) body;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DefDeclarationNode{" +
                "id=" + id +
                ", list=" + list +

                ", type=" + type +
                ", body=" + body +
                '}';
    }

    public ArrayList<ParenthesisDeclarationOperationNode> getList() {
        return list;
    }

    public IdentifierNode getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public BodyOperationNode getBody() {
        return body;
    }
}

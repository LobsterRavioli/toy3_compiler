package main.node.stat;

import main.node.Type;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.IntegerConstNode;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.visitors.utils.SymbolTable;

public class ForNode implements StatementOperationNode {

    private IntegerConstNode constant;
    private BodyOperationNode body;
    private VariableDeclarationOperationNode decl;
    private Type type;
    private SymbolTable table;

    public ForNode(Object constant, Object body, Object decl) {
        this.constant = (IntegerConstNode)  constant;
        this.body = (BodyOperationNode) body;
        this.decl = (VariableDeclarationOperationNode) decl;
        this.type = (Type) type;
    }


    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public IntegerConstNode getConstant() {
        return constant;
    }

    public void setConstant(IntegerConstNode constant) {
        this.constant = constant;
    }

    public BodyOperationNode getBody() {
        return body;
    }

    public void setBody(BodyOperationNode body) {
        this.body = body;
    }

    public VariableDeclarationOperationNode getDecl() {
        return decl;
    }

    public void setDecl(VariableDeclarationOperationNode decl) {
        this.decl = decl;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }
}

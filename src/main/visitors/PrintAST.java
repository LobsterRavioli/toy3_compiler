package main.visitors;

import main.node.Node;
import main.node.Visitor;
import main.node.body.BodyOperationNode;
import main.node.defdecl.DeclsOperationNode;
import main.node.defdecl.DefDeclarationNode;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.*;
import main.node.expr.operations.BinaryOperationNode;
import main.node.expr.operations.FunCallNode;
import main.node.expr.operations.UnaryOperationNode;
import main.node.pardecl.ParenthesisDeclarationOperationNode;
import main.node.pardecl.ParenthesisVariablesNode;
import main.node.program.ProgramOperationNode;
import main.node.stat.*;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.node.vardecl.VariableOptionalInitializerNodeOperator;

import java.io.FileWriter;
import java.io.IOException;

public class PrintAST implements Visitor {
    private final FileWriter file;

    public PrintAST(FileWriter file) {
        this.file = file;
    }

    @Override
    public Object visit(DoubleConstNode doubleConstNode) {
        try {
            file.append("<DoubleConstNode value=\"").append(String.valueOf(doubleConstNode.getConstant())).append("\">\n");
            file.append("</DoubleConstNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(FalseConstantNode falseConstantNode) {
        try {
            file.append("<FalseConstantNode value=\"").append(String.valueOf(false)).append("\">\n");
            file.append("</FalseConstantNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(StringConstantNode stringConstantNode) {
        try {
            file.append("<StringConstantNode value=\"").append(String.valueOf(stringConstantNode.getConstant())).append("\">\n");
            file.append("</StringConstantNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(CharConstantNode charConstantNode) {
        try {
            file.append("<CharConstantNode value=\"").append(String.valueOf(charConstantNode.getConstant())).append("\">\n");
            file.append("</CharConstantNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(TrueConstantNode trueConstantNode) {
        try {
            file.append("<TrueConstantNode value=\"").append(String.valueOf(true)).append("\">\n");
            file.append("</TrueConstantNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(IntegerConstNode integerConstNode) {
        try {
            file.append("<IntegerConstNode value=\"").append(String.valueOf(integerConstNode.getConstant())).append("\">\n");
            file.append("</IntegerConstNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(BinaryOperationNode binaryOperationNode) {
        try {
            file.append("<BinaryOperationNode ").append("Operation=\"").append(String.valueOf(binaryOperationNode.getOperator())).append("\" ").append(">\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        binaryOperationNode.getLeft().accept(this);
        binaryOperationNode.getRight().accept(this);
        try {
            file.append("</BinaryOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(UnaryOperationNode unaryOperationNode) {
        try {
            file.append("<UnaryOperationNode ").append("Operation=\"").append(String.valueOf(unaryOperationNode.getOperator())).append("\" ").append(">\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        unaryOperationNode.getChild().accept(this);

        try {
            file.append("</UnaryOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(IdentifierNode identifierNode) {
        try {
            file.append("<IdentifierNode ").append("Type=\"").append(String.valueOf(identifierNode.getName())).append("\" ").append(">\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            file.append("</IdentifierNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(FunCallNode funCallNode) {
        try {
            file.append("<FunCallNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        funCallNode.getId().accept(this);
        if (funCallNode.getExpressions() != null){
            for(ExpressionOperationNode var : funCallNode.getExpressions())
            {
                var.accept(this);
            }
        }

        try {
            file.append("</FunCallNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(ReadOperationNode readOperationNode) {
        try {
            file.append("<ReadOperationNode>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (readOperationNode.getList() != null){
            for(IdentifierNode variables : readOperationNode.getList())
            {
                variables.accept(this);
            }
        }

        try {
            file.append("</ReadOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(WriteOperationNode writeOperationNode) {
        try {
            file.append("<WriteOperationNode ").append("Type=\"").append(String.valueOf(writeOperationNode.isNewLine())).append("\" ").append(">\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (writeOperationNode.getExpressions() != null){
            for(ExpressionOperationNode expressions : writeOperationNode.getExpressions())
            {
                expressions.accept(this);
            }
        }

        try {
            file.append("</WriteOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(AssignOperationNode assignOperationNode) {
        try {
            file.append("<AssignOperationNode>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (assignOperationNode.getIdentifiers() != null){
            for(IdentifierNode identifierNode : assignOperationNode.getIdentifiers())
            {
                identifierNode.accept(this);
            }
        }

        if (assignOperationNode.getExpressions() != null){
            for(ExpressionOperationNode expression : assignOperationNode.getExpressions())
            {
                expression.accept(this);
            }
        }

        try {
            file.append("</AssignOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(ReturnStatementNode returnStatementNode) {
        try {
            file.append("<ReturnStatementNode>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        returnStatementNode.getResult().accept(this);

        try {
            file.append("</ReturnStatementNode>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public Object visit(VariableDeclarationOperationNode variableDeclarationOperationNode) {
        try {
            if(variableDeclarationOperationNode.getType() != null)
                file.append("<VariableDeclarationOperationNode ").append("Type=\"").append(String.valueOf(variableDeclarationOperationNode.getType())).append("\" ").append(">\n");
            else{
                file.append("<VariableDeclarationOperationNode>");
                ExpressionOperationNode expr = variableDeclarationOperationNode.getConstant();
                expr.accept(this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (variableDeclarationOperationNode.getVariables() != null){
            for(VariableOptionalInitializerNodeOperator var : variableDeclarationOperationNode.getVariables())
            {
                var.accept(this);
            }
        }


        try {
            file.append("</VariableDeclarationOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(BodyOperationNode bodyOperationNode) {
        try {
            file.append("<BodyOperationNode>").append("Type=\"");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (bodyOperationNode.getDeclarations() != null){
            for(VariableDeclarationOperationNode bodyNode : bodyOperationNode.getDeclarations())
            {
                bodyNode.accept(this);
            }
        }

        if (bodyOperationNode.getStatements() != null){
            for(StatementOperationNode stats : bodyOperationNode.getStatements())
            {
                stats.accept(this);
            }
        }

        try {
            file.append("</BodyOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(IfThenElseOperationNode ifThenElseOperationNode) {
        try {
            file.append("<IfThenElseOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ifThenElseOperationNode.getExpression().accept(this);
        ifThenElseOperationNode.getIfBody().accept(this);
        ifThenElseOperationNode.getElseBody().accept(this);
        try {
            file.append("</IfThenElseOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(IfThenOperationNode ifThenOperationNode) {
        try {
            file.append("<IfThenOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ifThenOperationNode.getExpression().accept(this);
        ifThenOperationNode.getBody().accept(this);

        try {
            file.append("</IfThenOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(WhileOperationNode whileOperationNode) {
        try {
            file.append("<WhileOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        whileOperationNode.getExpression().accept(this);
        whileOperationNode.getBody().accept(this);


        try {
            file.append("</WhileOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return null;
    }

    @Override
    public Object visit(ParenthesisDeclarationOperationNode parenthesisDeclarationOperationNode) {
        try {
            file.append("<ParenthesisDeclarationOperationNode ").append("Type=\"").append(String.valueOf(parenthesisDeclarationOperationNode.getType())).append("\" ").append(">\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (parenthesisDeclarationOperationNode.getVariables() != null){
            for(ParenthesisVariablesNode variables : parenthesisDeclarationOperationNode.getVariables())
            {
                variables.accept(this);
            }
        }

        try {
            file.append("</ParenthesisDeclarationOperationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(DefDeclarationNode defDeclarationNode) {
        try {
            file.append("<DefDeclarationNode ").append("Type=\"").append(String.valueOf(defDeclarationNode.getType())).append("\" ").append(">\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Node node = defDeclarationNode.getId();
        node.accept(this);


        if (defDeclarationNode.getList() != null){
            for(ParenthesisDeclarationOperationNode decl : defDeclarationNode.getList())
            {
                node = (Node) decl;
                node.accept(this);
            }
        }


        node = defDeclarationNode.getBody();
        node.accept(this);

        try {
            file.append("</DefDeclarationNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(ProgramOperationNode program) {

        try {
            file.append("<Program>").append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if(program.getOuterDeclaration() != null){
            for(DeclsOperationNode decl : program.getOuterDeclaration())
            {
                Node node = (Node)decl;
                node.accept(this);
            }
        }



        if (program.getInnerDeclaration() != null){
            for(DeclsOperationNode decl : program.getInnerDeclaration())
            {
                Node node = (Node)decl;
                node.accept(this);
            }
        }


        if(program.getStatements() != null){
            for(StatementOperationNode statement : program.getStatements())
            {
                Node node = (Node)statement;
                node.accept(this);
            }
        }
        try {
            file.append("</Program>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(VariableOptionalInitializerNodeOperator variableOptionalInitializerNodeOperator) {
        try {
            file.append("<VariableOptionalInitializerNodeOperator>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        variableOptionalInitializerNodeOperator.getId().accept(this);

        if(variableOptionalInitializerNodeOperator.getExpr() !=  null)
            variableOptionalInitializerNodeOperator.getExpr().accept(this);

        try {
            file.append("</VariableOptionalInitializerNodeOperator>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public Object visit(ParenthesisVariablesNode parenthesisVariablesNode) {
        try {
            file.append("<ParenthesisVariablesNode ").append("ref=\"").append(String.valueOf(parenthesisVariablesNode.isHasRef())).append("\" ").append(">\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        parenthesisVariablesNode.getId().accept(this);

        try {
            file.append("</ParenthesisVariablesNode>\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }


}
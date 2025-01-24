package main.node;

import main.node.defdecl.DefDeclarationNode;
import main.node.body.BodyOperationNode;
import main.node.expr.constants.*;
import main.node.expr.operations.*;
import main.node.pardecl.ParenthesisDeclarationOperationNode;
import main.node.pardecl.ParenthesisVariablesNode;
import main.node.program.ProgramOperationNode;
import main.node.stat.*;
import main.node.vardecl.VariableDeclarationOperationNode;
import main.node.vardecl.VariableOptionalInitializerNodeOperator;

// Interfaccia del Visitor
public interface Visitor{
    Object visit(DoubleConstNode doubleConstNode);
    Object visit(FalseConstantNode falseConstantNode);
    Object visit(StringConstantNode stringConstantNode);
    Object visit(CharConstantNode charConstantNode);
    Object visit(TrueConstantNode trueConstantNode);
    Object visit(IntegerConstNode integerConstNode);
    Object visit(BinaryOperationNode binaryOperationNode);
    Object visit(UnaryOperationNode unaryOperationNode);
    Object visit(IdentifierNode identifierNode);
    Object visit(FunCallNode funCallNode);
    Object visit(ReadOperationNode readOperationNode);
    Object visit(WriteOperationNode writeOperationNode);
    Object visit(AssignOperationNode assignOperationNode);
    Object visit(ReturnStatementNode returnStatementNode);
    Object visit(VariableDeclarationOperationNode variableDeclarationOperationNode);

    Object visit(BodyOperationNode bodyOperationNode);

    Object visit(IfThenElseOperationNode ifThenElseOperationNode);

    Object visit(IfThenOperationNode ifThenOperationNode);

    Object visit(WhileOperationNode whileOperationNode);

    Object visit(ParenthesisDeclarationOperationNode parenthesisDeclarationOperationNode);

    Object visit(DefDeclarationNode defDeclarationNode);

    Object visit(ProgramOperationNode programOperationNode);

    Object visit(VariableOptionalInitializerNodeOperator variableOptionalInitializerNodeOperator);

    Object visit(ParenthesisVariablesNode parenthesisVariablesNode);
}
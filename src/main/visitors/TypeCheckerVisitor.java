package main.visitors;

import main.node.Node;
import main.node.Type;
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
import main.visitors.utils.FunctionType;
import main.visitors.utils.SymbolTable;
import main.visitors.utils.SymbolTableRow;

import java.util.ArrayList;
import java.util.Stack;

public class TypeCheckerVisitor implements Visitor {
    private Stack<SymbolTable> typeEnvironment = new Stack<SymbolTable>();
    private SymbolTable currentTable;

    @Override
    public Object visit(DoubleConstNode doubleConstNode) {
//        typeEnvironment.add(doubleConstNode.getTable());

//        typeEnvironment.pop();
        doubleConstNode.setType(Type.DOUBLE);

        return doubleConstNode.getType();
    }

    @Override
    public Object visit(FalseConstantNode falseConstantNode) {
//        typeEnvironment.add(falseConstantNode.getTable());

//        typeEnvironment.pop();
        falseConstantNode.setType(Type.BOOLEAN);

        return falseConstantNode.getType();
    }

    @Override
    public Object visit(StringConstantNode stringConstantNode) {
//        typeEnvironment.add(stringConstantNode.getTable());

//        typeEnvironment.pop();
        stringConstantNode.setType(Type.STRING);

        return stringConstantNode.getType();
    }

    @Override
    public Object visit(CharConstantNode charConstantNode) {
//        typeEnvironment.add(charConstantNode.getTable());

//        typeEnvironment.pop();
        charConstantNode.setType(Type.CHAR);

        return charConstantNode.getType();
    }

    @Override
    public Object visit(TrueConstantNode trueConstantNode) {
//        typeEnvironment.add(trueConstantNode.getTable());

//        typeEnvironment.pop();
        trueConstantNode.setType(Type.BOOLEAN);

        return trueConstantNode.getType();
    }

    @Override
    public Object visit(IntegerConstNode integerConstNode) {
//        typeEnvironment.add(integerConstNode.getTable());


//        typeEnvironment.pop();
        integerConstNode.setType(Type.INTEGER);

        return integerConstNode.getType();
    }

    @Override
    public Object visit(BinaryOperationNode binaryOperationNode) {
//        typeEnvironment.add(binaryOperationNode.getTable());

        ExpressionOperationNode leftOperand = binaryOperationNode.getLeft();
        ExpressionOperationNode rightOperand = binaryOperationNode.getRight();

        leftOperand.accept(this);
        rightOperand.accept(this);
        binaryOperationNode.setType(this.OpTypeChecker(binaryOperationNode));

//        typeEnvironment.pop();
        return binaryOperationNode.getType();
    }

    @Override
    public Object visit(UnaryOperationNode unaryOperationNode) {
//        typeEnvironment.add(unaryOperationNode.getTable());

        ExpressionOperationNode operand = unaryOperationNode.getChild();

        operand.accept(this);


        unaryOperationNode.setType(this.unaryChecker(unaryOperationNode));
//        typeEnvironment.pop();
        return unaryOperationNode.getType();
    }

    @Override
    public Object visit(IdentifierNode identifierNode) {

        Stack<SymbolTable> cloned = (Stack<SymbolTable>) typeEnvironment.clone();
        Type type = lookUpVariable(identifierNode,cloned);
        if(type == null){
            throw new RuntimeException("Variable is not declared.");
        }
        identifierNode.setType(type);

        return identifierNode.getType();
    }

    @Override
    public Object visit(FunCallNode funCallNode) {

        Stack<SymbolTable> cloned = (Stack<SymbolTable>) typeEnvironment.clone();
        FunctionType type = lookUpFunction(funCallNode.getId(),cloned);

        ArrayList<Boolean> references = type.getHasRef();

        if(type == null){
            throw new RuntimeException("The function or procedure " + funCallNode.getId().getName() + "has been not declared");
        }

        if(type.getInputTypes() != null){
            int numberOfFormalParameters =  type.getInputTypes().size();
            int numberOfActualParameters = funCallNode.getExpressions().size();

            if(numberOfActualParameters != numberOfFormalParameters){
                throw new RuntimeException("The function or procedure " + funCallNode.getId().getName() + " the actual parameters does not match to formal parameters.");
            }

            for(int i =0; i< numberOfActualParameters; i++){
                Type actualParameters = (Type) funCallNode.getExpressions().get(i).accept(this);
                Type formalParameters = type.getInputTypes().get(i);

                if(actualParameters != formalParameters){
                    throw  new RuntimeException("The parameters " + funCallNode.getExpressions().get(i)  +  "( position " + i + "; type " + funCallNode.getExpressions().get(i).getType() +") has a different number actual type from the formal one");
                }
            }


            for(int i =0; i< numberOfActualParameters; i++){
                boolean hasRef = references.get(i);
                ExpressionOperationNode expr = funCallNode.getExpressions().get(i);

                if(hasRef && !(expr instanceof IdentifierNode)){
                    throw  new RuntimeException("The referenced parameters " + funCallNode.getExpressions().get(i)  +  "( position " + i + "; type " + funCallNode.getExpressions().get(i).getType() +") is not avaible");
                }

            }
        }

        if(type.getOutputType() != null){
            funCallNode.setType(type.getType());
        }
        else {
            funCallNode.setType(Type.NOTYPE);
        }

        funCallNode.setInputTypes(type.getInputTypes());

        return funCallNode.getType();

    }

    @Override
    public Object visit(ReadOperationNode readOperationNode) {

        if (readOperationNode.getList() != null){
            for(IdentifierNode variables : readOperationNode.getList())
            {
                variables.accept(this);
            }
        }
        readOperationNode.setType(Type.NOTYPE);
        return readOperationNode.getType();

    }

    @Override
    public Object visit(WriteOperationNode writeOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        writeOperationNode.setTable(table);

        if (writeOperationNode.getExpressions() != null){
            for(ExpressionOperationNode expressions : writeOperationNode.getExpressions())
            {
                expressions.accept(this);
            }
        }

        writeOperationNode.setType(Type.NOTYPE);
        return writeOperationNode.getType();
    }

    @Override
    public Object visit(AssignOperationNode assignOperationNode) {
        ArrayList<IdentifierNode> ids = (ArrayList<IdentifierNode>) assignOperationNode.getIdentifiers();
        for(IdentifierNode id : ids){
            id.accept(this);
        }

        ArrayList<ExpressionOperationNode> exprs = (ArrayList<ExpressionOperationNode>) assignOperationNode.getExpressions();
        for(ExpressionOperationNode expr : exprs){
            expr.accept(this);
        }

        for(int i=0; i< ids.size(); i++){
            Type idType = ids.get(i).getType();
            Type exprType = exprs.get(i).getType();
            if(idType == Type.DOUBLE && exprType == Type.INTEGER){
//                System.out.println("Funziona");
            }
            else if(idType != exprType){
                throw new RuntimeException("The id :" + ids.get(i).getName() + " and "+ exprs.get(i).getType() + "  are not the same.");
            }
        }

        assignOperationNode.setType(Type.NOTYPE);
        return assignOperationNode.getType();
    }

    @Override
    public Object visit(ReturnStatementNode returnStatementNode) {
        ExpressionOperationNode result = returnStatementNode.getResult();

        Type exprType = (Type) result.accept(this);
        returnStatementNode.setType(Type.NOTYPE);
        return returnStatementNode.getType();
    }

    @Override
    public Object visit(VariableDeclarationOperationNode variableDeclarationOperationNode) {
//        typeEnvironment.add(variableDeclarationOperationNode.getTable());
        ArrayList<VariableOptionalInitializerNodeOperator> optVars = variableDeclarationOperationNode.getVariables();

        if(optVars != null){

            for(VariableOptionalInitializerNodeOperator optVar : optVars){

                Type optVarType = (Type) optVar.accept(this);

                if(optVar.getExpr() != null){

                    if(variableDeclarationOperationNode.getType() != null){

                        if(optVarType != variableDeclarationOperationNode.getType()){
                            throw new RuntimeException("The variable '" + optVar.getId().getName() +
                                    "' initialized with: " + optVar.getExpr() + " does not match the declaration type: " + variableDeclarationOperationNode.getType());
                        }

                    } else {
                        Type initializationConstantType = Type.getTypeFromExpressionNode(variableDeclarationOperationNode.getConstant());
                        if(optVarType != initializationConstantType){
                            throw new RuntimeException("The variable '" + optVar.getId().getName() +
                                    "' initialized with: " + optVar.getExpr() + " does not match the declaration type: " + initializationConstantType);
                        }
                    }
                }
            }
        }

        ExpressionOperationNode constant = variableDeclarationOperationNode.getConstant();
        if(constant != null){
            constant.accept(this);
        }
//        typeEnvironment.pop();
        return null;

    }

    @Override
    public Object visit(BodyOperationNode bodyOperationNode) {
        typeEnvironment.add(bodyOperationNode.getTable());

        if (bodyOperationNode.getDeclarations() != null){
            for(VariableDeclarationOperationNode bodyNode : bodyOperationNode.getDeclarations())
            {
                bodyNode.accept(this);
            }
        }

        if (bodyOperationNode.getStatements() != null){
            for(StatementOperationNode stats : bodyOperationNode.getStatements())
            {
                Type type = (Type) stats.accept(this);
                if(type == null)
                    throw new RuntimeException("Statements not valid.");

            }
        }
        bodyOperationNode.setType(Type.NOTYPE);

        typeEnvironment.pop();

        return bodyOperationNode.getType();
    }

    @Override
    public Object visit(IfThenElseOperationNode ifThenElseOperationNode) {

        typeEnvironment.add(ifThenElseOperationNode.getTable());

        ExpressionOperationNode expr = ifThenElseOperationNode.getExpression();

        Type exprType = (Type) expr.accept(this);

        if(exprType != Type.BOOLEAN){
            throw new RuntimeException("The expression ifthen is not boolean, but is " + exprType);
        }

        BodyOperationNode body1 = ifThenElseOperationNode.getIfBody();
        Type bodyType1 = (Type) body1.accept(this);

        if(bodyType1 != Type.NOTYPE)
            throw new RuntimeException("The expression ifthen is not boolean, but is " + bodyType1);

        BodyOperationNode body2 = ifThenElseOperationNode.getElseBody();
        Type bodyType2 = (Type) body2.accept(this);


        if(bodyType2 != Type.NOTYPE)
            throw new RuntimeException("The expression else is not boolean, but is " + bodyType2);

        typeEnvironment.pop();
        ifThenElseOperationNode.setType(Type.NOTYPE);
        return ifThenElseOperationNode.getType();
    }

    @Override
    public Object visit(IfThenOperationNode ifThenOperationNode) {
        typeEnvironment.add(ifThenOperationNode.getTable());

        ExpressionOperationNode expr = ifThenOperationNode.getExpression();
        Type exprType = (Type) expr.accept(this);

        if(exprType != Type.BOOLEAN){
            throw new RuntimeException("The expression ifthen is not boolean, but is " + exprType);
        }


        BodyOperationNode body = ifThenOperationNode.getBody();
        Type bodyType = (Type) body.accept(this);
        if(bodyType != Type.NOTYPE)
            throw new RuntimeException("The expression ifthen is not boolean, but is " + bodyType);

        typeEnvironment.pop();
        ifThenOperationNode.setType(Type.NOTYPE);
        return ifThenOperationNode.getType();
    }

    @Override
    public Object visit(WhileOperationNode whileOperationNode) {

        typeEnvironment.add(whileOperationNode.getTable());
        ExpressionOperationNode expr = whileOperationNode.getExpression();

        Type tmpExpr = (Type) expr.accept(this);
        if(tmpExpr != Type.BOOLEAN){
            throw new RuntimeException("The while expression is not boolean");
        }

        BodyOperationNode body = whileOperationNode.getBody();
        body.accept(this);

        if(body.getType() != Type.NOTYPE){
            throw new RuntimeException("The while body is not NOTYPE");

        }

        whileOperationNode.setType(Type.NOTYPE);
        typeEnvironment.pop();
        return whileOperationNode.getType();
    }

    @Override
    public Object visit(ParenthesisDeclarationOperationNode parenthesisDeclarationOperationNode) {
//        typeEnvironment.add(parenthesisDeclarationOperationNode.getTable());

        if (parenthesisDeclarationOperationNode.getVariables() != null){
            for(ParenthesisVariablesNode variables : parenthesisDeclarationOperationNode.getVariables())
            {
                variables.accept(this);
            }
        }

//        typeEnvironment.pop();
        return null;
    }

    @Override
    public Object visit(DefDeclarationNode defDeclarationNode) {
        Type type = defDeclarationNode.getType();
        BodyOperationNode bodyOperationNode;
        currentTable = defDeclarationNode.getBody().getTable();
        typeEnvironment.add(defDeclarationNode.getTable());
        Node node;

        if (defDeclarationNode.getList() != null){
            for(ParenthesisDeclarationOperationNode decl : defDeclarationNode.getList())
            {
                decl.accept(this);
            }
        }

        if(defDeclarationNode.getType() == null){ // check for return statement in pro
            for(StatementOperationNode statement : defDeclarationNode.getBody().getStatements()){
                if(statement instanceof ReturnStatementNode) throw new RuntimeException("A proc cannot have a return statement!");
            }
        }

        else { // checks for function
            boolean error = true;
            for(StatementOperationNode statement : defDeclarationNode.getBody().getStatements()){
                if(statement instanceof ReturnStatementNode){
                    error = false;
                    Type tmpType = null;
                    ReturnStatementNode returnStatement = (ReturnStatementNode) statement;
                    if(returnStatement.getType() == null){
                        tmpType = (Type) returnStatement.getResult().accept(this);
//                        tmpType = Type.getTypeFromExpressionNode(returnStatement.getResult());
                    }
                    if(tmpType != defDeclarationNode.getType()){
                        throw new RuntimeException("The return type for " + defDeclarationNode.getId().getName() +" is incorrect");
                    }

                }
            }
            if(error){
                throw new RuntimeException("Missing return statement" + " " + defDeclarationNode.getId().getName());
            }
        }

        bodyOperationNode = defDeclarationNode.getBody();

        if (bodyOperationNode.getDeclarations() != null){
            for(VariableDeclarationOperationNode decls : bodyOperationNode.getDeclarations())
            {
                decls.accept(this);
            }
        }

        if (bodyOperationNode.getStatements() != null){
            for(StatementOperationNode statement : bodyOperationNode.getStatements())
            {
               Type typeToCheck = (Type) statement.accept(this);
                if(typeToCheck == null){
                    throw  new RuntimeException("The current statement " + statement + " is wrong.");
                }

            }
        }

        bodyOperationNode.setType(Type.NOTYPE);


        typeEnvironment.pop();

        return  null;
    }

    @Override
    public Object visit(ProgramOperationNode programOperationNode) {

        typeEnvironment.add(programOperationNode.getProgramTable());
        if(programOperationNode.getOuterDeclaration() != null){
            for(DeclsOperationNode decl : programOperationNode.getOuterDeclaration())
            {
                Node node = (Node)decl;
                node.accept(this);
            }
        }

        typeEnvironment.add(programOperationNode.getBeginEndTable());

        if (programOperationNode.getInnerDeclaration() != null){
            for(DeclsOperationNode decl : programOperationNode.getInnerDeclaration())
            {
                Node node = (Node)decl;
                node.accept(this);
            }
        }
        if(programOperationNode.getStatements() != null){
            for(StatementOperationNode statement : programOperationNode.getStatements())
            {
                Node node = (Node)statement;
                Type type = (Type) node.accept(this);
                if(type == null)
                    throw new RuntimeException("Statements not valid.");
            }
        }
        typeEnvironment.pop(); // BeginEndTable pop
        typeEnvironment.pop(); // Program pop
        programOperationNode.setType(Type.NOTYPE);
        return null;
    }

    @Override
    public Object visit(VariableOptionalInitializerNodeOperator variableOptionalInitializerNodeOperator) {

//        variableOptionalInitializerNodeOperator.getId().accept(this);

        if(variableOptionalInitializerNodeOperator.getExpr() !=  null){
            variableOptionalInitializerNodeOperator.getExpr().accept(this);
            variableOptionalInitializerNodeOperator.setReturnType(variableOptionalInitializerNodeOperator.getExpr().getType());
        }else {
            variableOptionalInitializerNodeOperator.setReturnType(null);

        }



        return variableOptionalInitializerNodeOperator.getReturnType();
    }

    @Override
    public Object visit(ParenthesisVariablesNode parenthesisVariablesNode) {

//        parenthesisVariablesNode.getId().accept(this);
        parenthesisVariablesNode.setType(parenthesisVariablesNode.getId().getType());

        return parenthesisVariablesNode.getId().getType();
    }

    @Override
    public Object visit(ForNode forNode) {

        typeEnvironment.add(forNode.getTable());

        if(forNode.getDecl().getVariables().size() > 1)
            throw new RuntimeException("Declaration in the for should be 1");

        System.out.println(forNode.getDecl());
        Type initializationConstantType;
        VariableOptionalInitializerNodeOperator varOptInit = forNode.getDecl().getVariables().get(0);
        if(varOptInit.getExpr() == null) { //if the son hasn't an expression then the father can have a type
            ExpressionOperationNode constant = forNode.getDecl().getConstant();
            if(constant != null) {
                initializationConstantType = Type.getTypeFromExpressionNode(constant);
            }
            else {
                initializationConstantType = forNode.getDecl().getType();
            }
            //System.out.println("if" + initializationConstantType);
        } else {
            initializationConstantType = forNode.getDecl().getType();
            //System.out.println("else" + initializationConstantType);
        }

        if(initializationConstantType != Type.INTEGER) {
            throw new RuntimeException("The variable" + forNode.getDecl().getVariables().get(0).toString() + "is not initializsed as an INTEGER");
        }

        BodyOperationNode body = forNode.getBody();
        body.accept(this);

        if(body.getType() != Type.NOTYPE){
            throw new RuntimeException("The for body is not NOTYPE");
        }

        forNode.setType(Type.NOTYPE);
        typeEnvironment.pop();
        return forNode.getType();
    }

    public Stack<SymbolTable> cloneTypeEnvironment(Stack<SymbolTable> typeEnvironment){
        Stack<SymbolTable> clonedStack = new Stack<SymbolTable>();
        for(SymbolTable currSymbolTable: typeEnvironment){
            clonedStack.push(currSymbolTable);
        }
        return clonedStack;
    }


    public Type unaryChecker(UnaryOperationNode node){
        ExpressionOperationNode expression = node.getChild();
        String operator = node.getOperator();
        boolean minusCheck = operator.equals("MINUS");
        boolean notCheck = operator.equals("NOT") ;

        if(minusCheck && expression.getType() == Type.INTEGER){
            return Type.INTEGER;
        }

        if(minusCheck && expression.getType() == Type.DOUBLE){
            return Type.DOUBLE;
        }

        if(notCheck && expression.getType() == Type.BOOLEAN){
            return Type.BOOLEAN;
        }
        return null;
    }


    public Type lookUpVariable(IdentifierNode idNode,Stack<SymbolTable> typeEnvironment){
        Stack<SymbolTable> clonedTypeEnvironment = cloneTypeEnvironment(typeEnvironment);
        if(typeEnvironment != null){
            for(SymbolTable symbolTable: clonedTypeEnvironment){

                if(symbolTable.contains(idNode,"variable")){
                    return symbolTable.getRow(idNode,"variable").getFirm().getType();
                }

            }
        }

        return null;
    }

    public FunctionType lookUpFunction(IdentifierNode idNode, Stack<SymbolTable> typeEnvironment){

        Stack<SymbolTable> clonedTypeEnvironment = cloneTypeEnvironment(typeEnvironment);
        for(SymbolTable symbolTable: clonedTypeEnvironment){
            if(symbolTable.contains(idNode,"function")){
                return (FunctionType) symbolTable.getRow(idNode,"function").getFirm();
            }else if(symbolTable.contains(idNode,"procedure")){
                return (FunctionType) symbolTable.getRow(idNode,"procedure").getFirm();
            }
        }
        return null;
    }

    public Type OpTypeChecker(BinaryOperationNode operation){
        ExpressionOperationNode leftOperand = operation.getLeft();
        ExpressionOperationNode rightOperand = operation.getRight();
        String operator = operation.getOperator();

        boolean arithOpCheck = operator.equals("PLUS") || operator.equals("MINUS") || operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") || operator.equals("GT") || operator.equals("GE") || operator.equals("EQ") || operator.equals("NE");
        boolean booleanCheck = operator.equals("AND") || operator.equals("OR");

        if(arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER){
            return Type.INTEGER;
        }
        else if(arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE){
            return Type.DOUBLE;
        }
        else if(arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE){
            return Type.DOUBLE;
        }

        else if(arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER){
            return Type.DOUBLE;
        }

        else if (booleanCheck && leftOperand.getType() == Type.BOOLEAN && rightOperand.getType() == Type.BOOLEAN){
            return Type.BOOLEAN;
        }

        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER){
            return Type.BOOLEAN;
        }

        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER){
            return Type.BOOLEAN;
        }

        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE){
            return Type.BOOLEAN;
        }

        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE){
            return Type.BOOLEAN;
        }
        else if(operator.equals("PLUS") && (leftOperand.getType() == Type.STRING || rightOperand.getType() == Type.STRING)){
            return Type.STRING;
        }
        else if(operator.equals("NE") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)){
            return Type.BOOLEAN;
        }else if(operator.equals("EQ") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)){
            return Type.BOOLEAN;

        }
        else {
            throw new RuntimeException(" The assignment does not have a match in the table! " + leftOperand + " " +  operation.getOperator()+ " " + rightOperand );
        }
    }
}

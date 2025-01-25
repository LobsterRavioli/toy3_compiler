package main.visitors;

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
import main.visitors.utils.*;

import java.util.ArrayList;
import java.util.Stack;

public class ScopeVisitor implements Visitor {
    private Stack<SymbolTable> typeEnvironment = new Stack<>();

    public ScopeVisitor() {
    }


    @Override
    public Object visit(DoubleConstNode doubleConstNode) {
        SymbolTable table = typeEnvironment.peek();
        doubleConstNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(FalseConstantNode falseConstantNode) {
        SymbolTable table = typeEnvironment.peek();
        falseConstantNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(StringConstantNode stringConstantNode) {
        SymbolTable table = typeEnvironment.peek();
        stringConstantNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(CharConstantNode charConstantNode) {
        SymbolTable table = typeEnvironment.peek();
        charConstantNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(TrueConstantNode trueConstantNode) {
        SymbolTable table = typeEnvironment.peek();
        trueConstantNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(IntegerConstNode integerConstNode) {
        SymbolTable table = typeEnvironment.peek();
        integerConstNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(BinaryOperationNode binaryOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        binaryOperationNode.setTable(table);
        binaryOperationNode.getRight().accept(this);
        binaryOperationNode.getLeft().accept(this);

        return null;
    }

    @Override
    public Object visit(UnaryOperationNode unaryOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        unaryOperationNode.setTable(table);
        unaryOperationNode.getChild().accept(this);
        return null;
    }

    @Override
    public Object visit(IdentifierNode identifierNode) {
        SymbolTable table = typeEnvironment.peek();
        identifierNode.setTable(table);

        //needed for the references
        Stack<SymbolTable> symbolTableStack = cloneTypeEnvironment(typeEnvironment);
        for(int i = symbolTableStack.size() -1; i>=0; i--){
            SymbolTable symbolTable = symbolTableStack.get(i);
            SymbolTableRow row = symbolTable.getRow(identifierNode, "variable");

            if (row != null) {
//                if(row.isReference() && row.getFirm().getType() != Type.STRING){
//                    identifierNode.setHasRef(true);
//                }
                if(row.isReference()){
                    identifierNode.setHasRef(true);
                }
//                VariableFirm stringType = new VariableFirm(Type.STRING);
////                VariableFirm rowType = row.getType();
//                if(row.isReference() &&  !rowType.compareTypes(stringType.getType())) {
//                        //if needed in order to avoid strings passed as ref having ** in the declaration
//                    identifierNode.setHasRef(true);
//                }
            }
        }


        return null;
    }

    @Override
    public Object visit(FunCallNode funCallNode) {
        SymbolTable table = typeEnvironment.peek();
        funCallNode.setTable(table);

        funCallNode.getId().accept(this);
        if (funCallNode.getExpressions() != null){
            for(ExpressionOperationNode var : funCallNode.getExpressions())
            {
                var.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visit(ReadOperationNode readOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        readOperationNode.setTable(table);
        if (readOperationNode.getList() != null){
            for(IdentifierNode variables : readOperationNode.getList())
            {
                variables.accept(this);
            }
        }
        return null;
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

        return null;
    }

    @Override
    public Object visit(AssignOperationNode assignOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        assignOperationNode.setTable(table);

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



        if(assignOperationNode.getIdentifiers().size() > 1 ){
            if(assignOperationNode.getExpressions() != null){
                for (ExpressionOperationNode expr: assignOperationNode.getExpressions()){
                    if(expr instanceof FunCallNode){
                        throw new RuntimeException("You cannot use functions on multiple declarations.");
                    }
                }
            }

        }

        int idSize = assignOperationNode.getIdentifiers().size();
        int exprSize = assignOperationNode.getExpressions().size();

        if (idSize != exprSize)
            throw new RuntimeException("The size number of ids and the expressions are not equal");


        return assignOperationNode;
    }

    @Override
    public Object visit(ReturnStatementNode returnStatementNode) {
        SymbolTable table = typeEnvironment.peek();
        returnStatementNode.setTable(table);
        returnStatementNode.getResult().accept(this);
        return null;
    }

    @Override
    public Object visit(VariableDeclarationOperationNode variableDeclarationOperationNode) {

        SymbolTable table = typeEnvironment.peek();
        variableDeclarationOperationNode.setTable(table);


        Type variableType = variableDeclarationOperationNode.getType();

        if(variableDeclarationOperationNode.getConstant() != null){
            variableDeclarationOperationNode.getConstant().accept(this);

        }

        if (variableDeclarationOperationNode.getVariables() != null){
            for(VariableOptionalInitializerNodeOperator var : variableDeclarationOperationNode.getVariables())
            {
                var.accept(this);
            }

        }

        return null;
    }

    @Override
    public Object visit(BodyOperationNode bodyOperationNode) {
        SymbolTable fatherTable = typeEnvironment.peek();
        SymbolTable bodyTable = new SymbolTable("Body Table" + " " + fatherTable.getName());

        typeEnvironment.add(bodyTable);

        if (bodyOperationNode.getDeclarations() != null){
            for(VariableDeclarationOperationNode vars : bodyOperationNode.getDeclarations())
            {
                Firm type = new VariableFirm(vars.getType());

                if(vars.getType() == null) {
                    if(!checkVardecl(vars)) throw new RuntimeException("Incorrect Variable declaration");

                    type = new VariableFirm(vars.getConstant());
                }
                for(VariableOptionalInitializerNodeOperator var: vars.getVariables()){
                    SymbolTableRow row = new SymbolTableRow(var.getId().getName(), "variable", type);
                    bodyTable.addRow(row);
                }

                vars.accept(this);
            }
        }

        if (bodyOperationNode.getStatements() != null){
            for(StatementOperationNode stats : bodyOperationNode.getStatements())
            {
                stats.accept(this);
            }
        }

        bodyOperationNode.setTable(bodyTable);
        typeEnvironment.pop();


        return null;

    }

    @Override
    public Object visit(IfThenElseOperationNode ifThenElseOperationNode) {

        SymbolTable table = new SymbolTable("IF-THEN-ELSE");
        typeEnvironment.add(table);
        ifThenElseOperationNode.getExpression().accept(this);
        ifThenElseOperationNode.getIfBody().accept(this);
        ifThenElseOperationNode.getElseBody().accept(this);
        typeEnvironment.pop();

        ifThenElseOperationNode.setTable(table);


        return null;
    }

    @Override
    public Object visit(IfThenOperationNode ifThenOperationNode) {

        SymbolTable table = new SymbolTable("IF-THEN");
        typeEnvironment.add(table);
        ifThenOperationNode.getExpression().accept(this);
        ifThenOperationNode.getBody().accept(this);
        typeEnvironment.pop();
        ifThenOperationNode.setTable(table);

        return null;
    }

    @Override
    public Object visit(WhileOperationNode whileOperationNode) {

        SymbolTable table = new SymbolTable("WHILE");
        typeEnvironment.add(table);
        whileOperationNode.getExpression().accept(this);
        whileOperationNode.getBody().accept(this);
        typeEnvironment.pop();
        whileOperationNode.setTable(table);
        return null;
    }

    @Override
    public Object visit(ParenthesisDeclarationOperationNode parenthesisDeclarationOperationNode) {
        SymbolTable table = typeEnvironment.peek();
        parenthesisDeclarationOperationNode.setTable(table);

        if (parenthesisDeclarationOperationNode.getVariables() != null){
            for(ParenthesisVariablesNode variables : parenthesisDeclarationOperationNode.getVariables())
            {
                variables.accept(this);
            }
        }


        return null;
    }

    @Override
    public Object visit(DefDeclarationNode defDeclarationNode) {

        SymbolTable defDeclarationTable = new SymbolTable(new ArrayList<>(),defDeclarationNode.getId().getName());
        typeEnvironment.add(defDeclarationTable);
        ArrayList<ParenthesisDeclarationOperationNode> list = defDeclarationNode.getList();

        if(list != null){

            for(ParenthesisDeclarationOperationNode decl : list){

                Firm type = new VariableFirm(decl.getType());

                for(ParenthesisVariablesNode pvar :decl.getVariables()){
                    SymbolTableRow row = new SymbolTableRow(pvar.getId().getName(), "variable", type, pvar.isHasRef());
                    defDeclarationTable.addRow(row);
                }
                decl.accept(this);
            }

        }

        BodyOperationNode body = defDeclarationNode.getBody();
        ArrayList<VariableDeclarationOperationNode> decls = body.getDeclarations();
        for(VariableDeclarationOperationNode decl: decls){

            Firm type = new VariableFirm(decl.getType());
            if(decl.getType() == null) {
                if(!checkVardecl(decl)) throw new RuntimeException("Incorrect Variable declaration");
                type = new VariableFirm(decl.getConstant());
            }
            for(VariableOptionalInitializerNodeOperator var: decl.getVariables()){
                SymbolTableRow row = new SymbolTableRow(var.getId().getName(), "variable", type);
                defDeclarationTable.addRow(row);
            }
        }

        defDeclarationNode.setTable(defDeclarationTable);
        ArrayList<StatementOperationNode> statements = body.getStatements();
        for(StatementOperationNode statement : statements){
            statement.accept(this);
        }

        defDeclarationNode.getId().accept(this);
        typeEnvironment.pop();
        return null;
    }

    public Object visit(ProgramOperationNode node) {
        SymbolTable programTable = new SymbolTable(new ArrayList<>(),"ProgramTable");
        typeEnvironment.add(programTable);
        ArrayList<DeclsOperationNode> decls = node.getOuterDeclaration();
        //iterates on each declaration and adds the element as a row of the scoping table of Program
        for (DeclsOperationNode decl : decls){
            String name;
            String kind;
            Firm type; //variable used to indicate the firm of the method or the type of the function
            Type return_type = null;
            ArrayList<Boolean> hasRefList = new ArrayList<>();
            ArrayList<Type> inputs_type = new ArrayList<>(); //variable used to temporarily contain the parameters' type of the function
            if(decl instanceof DefDeclarationNode){
                name = ((DefDeclarationNode) decl).getId().getName(); //name will be equal to the function name
                if(((DefDeclarationNode) decl).getType() == null){ //checks if the function has a return type
                    kind = "procedure";
                }else {
                    kind = "function";
                    return_type = ((DefDeclarationNode) decl).getType();
                }
                if(((DefDeclarationNode) decl).getList() == null){ //checks if the function has parameters
                    inputs_type = null;
                } else {
                    for(ParenthesisDeclarationOperationNode par : ((DefDeclarationNode) decl).getList()){ //gets the type of each parameter
                        for(ParenthesisVariablesNode var: par.getVariables()){
                            inputs_type.add(par.getType());
                            hasRefList.add(var.isHasRef());
                        }

                    }
                }


                type = new FunctionType(inputs_type, return_type,hasRefList);
                SymbolTableRow row = new SymbolTableRow(name, kind, type);

                programTable.addRow(row);


            } else if (decl instanceof VariableDeclarationOperationNode) {
                for(VariableOptionalInitializerNodeOperator var : ((VariableDeclarationOperationNode) decl).getVariables()){ //there could be defined multiple variables together
                    name = var.getId().getName();
                    kind = "variable";

                    VariableDeclarationOperationNode vardecl = (VariableDeclarationOperationNode)decl;

                    if(vardecl.getType() == null){
                        if(!checkVardecl(vardecl)) throw new RuntimeException("Incorrect Variable declaration");
                        type = new VariableFirm(vardecl.getConstant());
                    }else{
                        type = new VariableFirm(vardecl.getType());
                    }
                    SymbolTableRow row = new SymbolTableRow(name, kind, type);
                    programTable.addRow(row);
                }

            }
            decl.accept(this);
        }

        node.setProgramTable(programTable);

        //Start of the begin-end body
        ArrayList<VariableDeclarationOperationNode> vars = node.getInnerDeclaration();
        SymbolTable beginEndTable = new SymbolTable("Begin-End");
        typeEnvironment.add(beginEndTable);
        if(vars != null){
            for(VariableDeclarationOperationNode var : vars){
                String name;
                String kind;
                Firm type;
                if(var.getType() == null){
                    if(!checkVardecl(var)) throw new RuntimeException("Incorrect Variable declaration");
                    type = new VariableFirm(var.getConstant());
                }else {
                    type =  new VariableFirm(var.getType());
                }
                for(VariableOptionalInitializerNodeOperator optVar : var.getVariables()){//there could be defined multiple variables together
                    name = optVar.getId().getName();
                    kind = "variable";

                    SymbolTableRow row = new SymbolTableRow(name, kind, type); //creates a new row (one for each variable defined).
                    // Multiple definitions on the same line are counted as separated rows
                    beginEndTable.addRow(row);
                }

                var.accept(this);
            }
        }

        node.setBeginEndTable(beginEndTable);
        ArrayList<StatementOperationNode> stats = node.getStatements();
        if(stats != null){
            for(StatementOperationNode stat : stats){
                stat.accept(this);
            }
        }


        typeEnvironment.pop();
        typeEnvironment.pop();
        return node;
    }

    @Override
    public Object visit(VariableOptionalInitializerNodeOperator variableOptionalInitializerNodeOperator) {

        SymbolTable table = typeEnvironment.peek();
        variableOptionalInitializerNodeOperator.setTable(table);

        variableOptionalInitializerNodeOperator.getId().accept(this);

        if(variableOptionalInitializerNodeOperator.getExpr() !=  null)
            variableOptionalInitializerNodeOperator.getExpr().accept(this);

        return null;
    }

    @Override
    public Object visit(ParenthesisVariablesNode parenthesisVariablesNode) {
        SymbolTable table = typeEnvironment.peek();
        parenthesisVariablesNode.setTable(table);
        parenthesisVariablesNode.getId().accept(this);
        return null;
    }

    @Override
    public Object visit(LetOperationNode letOperationNode) {
        SymbolTable table = new SymbolTable("LetOperationNode");
        letOperationNode.setTable(table);
        typeEnvironment.add(table);
        ArrayList<DeclsOperationNode> decls = letOperationNode.getDeclerations();
        //iterates on each declaration and adds the element as a row of the scoping table of Program
        for (DeclsOperationNode decl : decls){
            String name;
            String kind;
            Firm type; //variable used to indicate the firm of the method or the type of the function
            Type return_type = null;
            ArrayList<Boolean> hasRefList = new ArrayList<>();
            ArrayList<Type> inputs_type = new ArrayList<>(); //variable used to temporarily contain the parameters' type of the function
            if(decl instanceof DefDeclarationNode){
                name = ((DefDeclarationNode) decl).getId().getName(); //name will be equal to the function name
                if(((DefDeclarationNode) decl).getType() == null){ //checks if the function has a return type
                    kind = "procedure";
                }else {
                    kind = "function";
                    return_type = ((DefDeclarationNode) decl).getType();
                }
                if(((DefDeclarationNode) decl).getList() == null){ //checks if the function has parameters
                    inputs_type = null;
                } else {
                    for(ParenthesisDeclarationOperationNode par : ((DefDeclarationNode) decl).getList()){ //gets the type of each parameter
                        for(ParenthesisVariablesNode var: par.getVariables()){
                            inputs_type.add(par.getType());
                            hasRefList.add(var.isHasRef());
                        }

                    }
                }


                type = new FunctionType(inputs_type, return_type,hasRefList);

                SymbolTableRow row = new SymbolTableRow(name, kind, type);

                letOperationNode.getTable().addRow(row);

            } else if (decl instanceof VariableDeclarationOperationNode) {
                for(VariableOptionalInitializerNodeOperator var : ((VariableDeclarationOperationNode) decl).getVariables()){ //there could be defined multiple variables together
                    name = var.getId().getName();
                    kind = "variable";

                    VariableDeclarationOperationNode vardecl = (VariableDeclarationOperationNode)decl;

                    if(vardecl.getType() == null){
                        if(!checkVardecl(vardecl)) throw new RuntimeException("Incorrect Variable declaration");
                        type = new VariableFirm(vardecl.getConstant());
                    }else{
                        type = new VariableFirm(vardecl.getType());
                    }
                    SymbolTableRow row = new SymbolTableRow(name, kind, type);
                    letOperationNode.getTable().addRow(row);
                }

            }
            decl.accept(this);
        }



        ArrayList<StatementOperationNode> stats = letOperationNode.getStatements();
        if(stats != null){
            for(StatementOperationNode stat : stats){
                stat.accept(this);
            }
        }


        System.out.println(letOperationNode.getTable());
        typeEnvironment.pop();
        return letOperationNode;
    }

    @Override
    public Object visit(WhileElseLoopNode whileElseLoopNode) {
        SymbolTable whileLoopTable = new SymbolTable("WHILE-LOOP");
        SymbolTable elseLoopTable = new SymbolTable("ELSE-LOOP");

        typeEnvironment.add(whileLoopTable);
        whileElseLoopNode.getExpression().accept(this);
        whileElseLoopNode.getWhileBody().accept(this);
        typeEnvironment.pop();
        typeEnvironment.add(elseLoopTable);
        whileElseLoopNode.getElseLoopBody().accept(this);
        typeEnvironment.pop();
        whileElseLoopNode.setWhiletable(whileLoopTable);
        whileElseLoopNode.setElseTable(elseLoopTable);

        return null;
    }

    public boolean checkVardecl(VariableDeclarationOperationNode vars){

        int numberOfVars = vars.getVariables().size();
        if(numberOfVars == 1){
            VariableOptionalInitializerNodeOperator opt = vars.getVariables().get(0);
            if(opt.getExpr() == null){
                return true;
            }
        }
        return false;
    }


    public Stack<SymbolTable> cloneTypeEnvironment(Stack<SymbolTable> typeEnvironment){
        Stack<SymbolTable> clonedStack = new Stack<SymbolTable>();
        for(SymbolTable currSymbolTable: typeEnvironment){
            clonedStack.push(currSymbolTable);
        }
        return clonedStack;
    }

}

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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TranslationVisitor implements Visitor {

    private HashMap<String, ArrayList<Boolean>> firms = new HashMap<>();
    public TranslationVisitor() {
        return;
    }

    @Override
    public String visit(DoubleConstNode doubleConstNode) {
        return String.valueOf(doubleConstNode.getConstant());
    }

    @Override
    public String visit(FalseConstantNode falseConstantNode) {
        return "0";
    }

    @Override
    public String visit(StringConstantNode stringConstantNode) {
        return "\""+ stringConstantNode.getConstant() + "\"";
    }

    @Override
    public String visit(CharConstantNode charConstantNode) {
        return "\'" + charConstantNode.getConstant() + "\'";
    }

    @Override
    public String visit(TrueConstantNode trueConstantNode) {
        return "1";
    }

    @Override
    public String visit(IntegerConstNode integerConstNode) {
        return String.valueOf(integerConstNode.getConstant());
    }

    @Override
    public String visit(BinaryOperationNode binaryOperationNode) {
        StringBuilder builder = new StringBuilder();

        ExpressionOperationNode leftOperand = binaryOperationNode.getLeft();
        ExpressionOperationNode rightOperand = binaryOperationNode.getRight();
        String operator = binaryOperationNode.getOperator();
        builder.append(doubleExpressionOperation(operator, leftOperand, rightOperand));
        return builder.toString();

    }

    @Override
    public String visit(UnaryOperationNode unaryOperationNode) {
        StringBuilder builder = new StringBuilder();
        ExpressionOperationNode operand = unaryOperationNode.getChild();
        String operator = unaryOperationNode.getOperator();
        builder.append(singleExpressionOperator(unaryOperationNode));

        return builder.toString();
    }

    @Override
    public String visit(IdentifierNode identifierNode) {
        StringBuilder builder = new StringBuilder();
//        if(identifierNode.isHasRef() && identifierNode.getType() != Type.STRING)
//            builder.append("*");

        if(identifierNode.isHasRef())
            builder.append("*");

        builder.append(identifierNode.getName());

        return builder.toString();
    }

    @Override
    public String visit(FunCallNode funCallNode) {
        StringBuilder builder = new StringBuilder();

        IdentifierNode id = funCallNode.getId();
        builder.append(id.accept(this) + "_fun");

        ArrayList<Boolean> references = firms.get(id.getName());

        builder.append("(");

        ArrayList<ExpressionOperationNode> exprs = funCallNode.getExpressions();

        if(exprs != null){
            for(int i = exprs.size() -1; i >= 0; i--){
                ExpressionOperationNode expr = exprs.get(i);
                String exprContent = (String) expr.accept(this);
//                if(references.get(i) == true && expr.getType() != Type.STRING){
//                    builder.append("&");
//                }

                if(references.get(i) == true){
                    builder.append("&");
                }
                builder.append(exprContent);
                if(i != 0){
                    builder.append(", ");
                }
            }
        }

        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visit(ReadOperationNode readOperationNode) {

        StringBuilder result = new StringBuilder();
        for(IdentifierNode id: readOperationNode.getList()) {

            Type type = id.getType();
            String varName = (String) id.accept(this);

            if(type.name().equalsIgnoreCase(Type.STRING.name())) {

                String buffer = "buffer = (char*) malloc((1024*5)*sizeof(char) );\n";
                result.append(buffer);
                result.append("scanf(\"%[^\\n]\", buffer);\n");
                String alloc = varName + "= (char*) malloc( (strlen(buffer) + 1) *sizeof(char) );\n";
                alloc = alloc + "strcpy(" + varName + ",buffer);\n free(buffer);\n";
                result.append(alloc);
            }

            if(type.name().equalsIgnoreCase(Type.BOOLEAN.name())
                    || type.name().equalsIgnoreCase(Type.INTEGER.name())) {
                result.append("scanf(\"%d\", ").append("&").append(varName).append(");");
            }

            if(type.name().equalsIgnoreCase(Type.DOUBLE.name())) {
                result.append("scanf(\"%lf\", ").append("&").append(varName).append(");");
            }

            if(type.name().equalsIgnoreCase(Type.CHAR.name())) {
                result.append("scanf(\"%c\", ").append("&").append(varName).append(");");
            }

            result.append("getchar();");
        }
        return result.toString();
    }

    @Override
    public String visit(WriteOperationNode writeOperationNode) {
        StringBuilder printArgs = new StringBuilder("");
        StringBuilder printString = new StringBuilder("printf(\"");

        ArrayList<ExpressionOperationNode> expressions = writeOperationNode.getExpressions();
        int size = expressions.size() - 1;
        for(int i = size; i >= 0; i--){
            ExpressionOperationNode expr = expressions.get(i);
            String expression = (String) expr.accept(this);

            Type type = expr.getType();
            String specifier = getStringSpecifier(type);

            printString.append(specifier);
            printArgs.append(expression).append(",");
        }

        printArgs = new StringBuilder(printArgs.substring(0, printArgs.length() - 1));

        String newLine = "";
        if(writeOperationNode.isNewLine()){
            printString.append("\\n");
        }

        printString.append("\",").append(printArgs).append(newLine).append(");");


        return printString.toString();
    }

    @Override
    public String visit(AssignOperationNode assignOperationNode) {
        StringBuilder builder = new StringBuilder();
        ArrayList<IdentifierNode> ids = (ArrayList<IdentifierNode>) assignOperationNode.getIdentifiers();
        ArrayList<ExpressionOperationNode> exprs = (ArrayList<ExpressionOperationNode>) assignOperationNode.getExpressions();

        int size  = ids.size() - 1;
        for(int i = size; i>= 0; i--){
            String id = (String) ids.get(i).accept(this);
            String expr = (String) exprs.get(i).accept(this);
            builder.append(id).append(" = ").append(expr);
            builder.append(";\n");
        }



        return builder.toString();
    }

    @Override
    public String visit(ReturnStatementNode returnStatementNode) {
        StringBuilder stringBuilder = new StringBuilder();
        String expression = (String) returnStatementNode.getResult().accept(this);
        stringBuilder.append("return ").append(expression).append(";");
        return stringBuilder.toString();
    }

    @Override
    public String visit(VariableDeclarationOperationNode variableDeclarationOperationNode) {
        StringBuilder builder = new StringBuilder();
        if(variableDeclarationOperationNode.getConstant() != null){
                builder.append(getCType(Type.getTypeFromExpressionNode(variableDeclarationOperationNode.getConstant()))).append(" ");
                builder.append(variableDeclarationOperationNode.getVariables().get(0).accept(this));
                builder.append(" = ");
                builder.append(variableDeclarationOperationNode.getConstant().accept(this));
        }
        else {
            builder.append(getCType(variableDeclarationOperationNode.getType())).append(" ");
            int size = variableDeclarationOperationNode.getVariables().size() - 1;
            boolean isString = false;

            if(variableDeclarationOperationNode.getType() == Type.STRING)
                isString = true;

            for(int i = size; i>= 0; i--){

                // char* taglia,*ans1,*ans;
                // char* taglia;
                if(isString && i != size)
                    builder.append("*");

                if(i == 0){
                    builder.append(variableDeclarationOperationNode.getVariables().get(i).accept(this));
                }
                else
                    builder.append(variableDeclarationOperationNode.getVariables().get(i).accept(this)).append(",");

            }

        }

        builder.append(";");

        return builder.toString();

    }

    @Override
    public String visit(BodyOperationNode bodyOperationNode) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for(VariableDeclarationOperationNode decl: bodyOperationNode.getDeclarations())
            builder.append(decl.accept(this));
        for(StatementOperationNode statements: bodyOperationNode.getStatements()){
            builder.append(statements.accept(this));
            if(statements instanceof FunCallNode){
                builder.append(";").append("\n");
            }

        }

        builder.append("}");

        return builder.toString();
    }

    @Override
    public String visit(IfThenElseOperationNode ifThenElseOperationNode) {
        StringBuilder builder = new StringBuilder();
        String condition = (String) ifThenElseOperationNode.getExpression().accept(this);
        String ifBody = (String) ifThenElseOperationNode.getIfBody().accept(this);
        String elseBody = (String) ifThenElseOperationNode.getElseBody().accept(this);
        builder.append("if(").append(condition).append(")").append(ifBody).append("else").append(elseBody);
        return builder.toString();
    }

    @Override
    public String visit(IfThenOperationNode ifThenOperationNode) {
        StringBuilder builder = new StringBuilder();
        String condition = (String) ifThenOperationNode.getExpression().accept(this);
        String body = (String) ifThenOperationNode.getBody().accept(this);
        builder.append("if(").append(condition).append(")").append(body);
        return builder.toString();
    }

    @Override
    public String visit(WhileOperationNode whileOperationNode) {
        StringBuilder builder = new StringBuilder();
        String condition = (String) whileOperationNode.getExpression().accept(this);
        String body= (String) whileOperationNode.getBody().accept(this);
        builder.append("while(" + condition + "){\n" + body + "\n}\n" );

        return builder.toString();
    }

    @Override
    public String visit(ParenthesisDeclarationOperationNode parenthesisDeclarationOperationNode) {
        StringBuilder builder = new StringBuilder();
        int size = parenthesisDeclarationOperationNode.getVariables().size() - 1 ;
        ParenthesisVariablesNode variable;
        boolean hasRef;
        String ref = "";
        for(int i = size; i>=0; i--){
            variable = parenthesisDeclarationOperationNode.getVariables().get(i);

//            if(variable.isHasRef() && parenthesisDeclarationOperationNode.getType() != Type.STRING)
//                ref = "*";

            if(i == 0){
                builder.append(getCType(parenthesisDeclarationOperationNode.getType())).append(" ").append(ref).append(variable.accept(this));
            }
            else
                builder.append(getCType(parenthesisDeclarationOperationNode.getType())).append(" ").append(ref).append(variable.accept(this)).append(",");
        }


        return builder.toString();
    }

    @Override
    public String visit(DefDeclarationNode defDeclarationNode) {
        StringBuilder builder = new StringBuilder();
        String firm = (String) createFirm(defDeclarationNode);
        builder.append(firm);
        builder.append(defDeclarationNode.getBody().accept(this));
        return builder.toString();
    }

    public String visit(ProgramOperationNode programOperationNode) {
        StringBuilder builder = new StringBuilder();
        setupFirms(programOperationNode);
        // Aggiunge l'header iniziale
        builder.append(buildHeader());

        // Gestisce le outer declarations
        if (programOperationNode.getOuterDeclaration() != null) {
            for (DeclsOperationNode decl : programOperationNode.getOuterDeclaration()) {
                if (decl instanceof DefDeclarationNode) {
                    DefDeclarationNode prototype = (DefDeclarationNode) decl;
                    builder.append(createPrototype(prototype)).append("\n");
                }
            }
            for (DeclsOperationNode decl : programOperationNode.getOuterDeclaration()) {
                builder.append(decl.accept(this)).append("\n");
            }

        }


        // Aggiunge il main
        builder.append("int main() {\n");
        if (programOperationNode.getInnerDeclaration() != null) {
            for (DeclsOperationNode decl : programOperationNode.getInnerDeclaration()) {
                Node node = (Node) decl;
                builder.append(node.accept(this)).append("\n");
            }
        }
        // Gestisce le statements
        if (programOperationNode.getStatements() != null) {
            for (StatementOperationNode statement : programOperationNode.getStatements()) {

                Node node = (Node) statement;
                builder.append(node.accept(this)).append("\n");
                if(statement instanceof FunCallNode){
                    builder.append(";").append("\n");
                }
            }
        }

        builder.append("    // Main content here\n"); // Eventuale aggiunta di contenuti
        builder.append("}\n");

        return builder.toString();
    }


    @Override
    public String visit(VariableOptionalInitializerNodeOperator variableOptionalInitializerNodeOperator) {
        StringBuilder builder = new StringBuilder();
        String expression ="";
        String id = (String) variableOptionalInitializerNodeOperator.getId().accept(this);
        builder.append(id);
        if(variableOptionalInitializerNodeOperator.getExpr() != null){
            expression = (String) variableOptionalInitializerNodeOperator.getExpr().accept(this);
            builder.append(" = ").append(expression);
        }
        return builder.toString();
    }

    @Override
    public String visit(ParenthesisVariablesNode parenthesisVariablesNode) {
        StringBuilder builder = new StringBuilder();
        builder.append(parenthesisVariablesNode.getId().accept(this));
        return builder.toString();
    }

    @Override
    public Object visit(ForNode forNode) {
        StringBuilder builder = new StringBuilder();
        String declaration = (String) forNode.getDecl().accept(this);
        String integer = (String) forNode.getConstant().accept(this);
        String body= (String) forNode.getBody().accept(this);
        String id = forNode.getDecl().getVariables().get(0).getId().getName();
        builder.append("for(");
        builder.append(declaration);
        builder.append(id).append("<=").append(integer).append(";");
        builder.append(id).append("++");
        builder.append(")");
        builder.append(body);

        return builder.toString();
    }

    private String getCType(Type t){

        if (t == null){
            return "void";
        }

        switch (t) {

            case STRING : return "char*";

            case CHAR: return "char";

            case DOUBLE: return "double";

            case INTEGER, BOOLEAN: return "int";


            default: return "";

        }
    }

    private String buildHeader() {
        StringBuilder header = new StringBuilder();
        header.append("""
            #include <stdio.h>
            #include <stdlib.h>
            #include <string.h>
            #include <math.h>
            
            #define BUFFER_SIZE  1024*4
            """);

        header.append("""
            
            char* buffer;
            char* string_concat(char* s1, char* s2)
            {
                char* ns = malloc(strlen(s1) + strlen(s2) + 1);
                strcpy(ns, s1);
                strcat(ns, s2);
                return ns;
            }
            
            """);

        header.append("""
            char* int2str(int n)
            {
                char buffer[BUFFER_SIZE];
                int len = sprintf(buffer,"%d",n);
                char *ns = malloc(len + 1);
                sprintf(ns,"%d",n);
                return ns;
            }
                
            """);


        header.append("""
            char* char2str(char c)
            {
                char *ns = malloc(2);
                sprintf(ns, "%c", c);
                return ns;
            }
            
            """);

        header.append("""
            char* float2str(float f)
            {
                char buffer[BUFFER_SIZE];
                int len = sprintf(buffer,"%f", f);
                char *ns = malloc(len + 1);
                sprintf(ns, "%f", f);
                return ns;
            }
            
        """);

        header.append("""
            char* bool2str(int b)
            {
                char* ns = NULL;
                if(b)
                {
                    ns = malloc(5);
                    strcpy(ns, "true");
                }
                else
                {
                    ns = malloc(6);
                    strcpy(ns, "false");
                }
                return ns;
            }
            
           
            
            """);

        return header.toString();
    }

    private String getPrototype(DefDeclarationNode function){
        return "";
    }

    private String createPrototype(DefDeclarationNode decl){
        StringBuilder builder = new StringBuilder();
        builder.append(createFirm(decl));
        builder.append(";").append("\n");

        return builder.toString();
    }

    private String createFirm(DefDeclarationNode decl){
        StringBuilder builder = new StringBuilder();
        DefDeclarationNode prototype = decl;
        if(prototype.getType() == null)
            builder.append("void ");
        else if(prototype.getType() == Type.STRING)
            builder.append("char* ");
        else
            builder.append(getCType(prototype.getType())).append(" ");
        builder.append(prototype.getId().accept(this)).append("_fun(");


        if(prototype.getList() != null){
            int size = prototype.getList().size() - 1;
            ParenthesisDeclarationOperationNode param;
            for (int i = size; i >= 0;i--){
                param = prototype.getList().get(i);
                if(prototype.getList().indexOf(param) == 0){
                    builder.append(param.accept(this));
                }
                else builder.append(param.accept(this)).append(",");
            }
        }

        builder.append(")");
        return builder.toString();
    }

    //metodi privati utility
    private String getStringSpecifier(Type t) {

        switch (t) {
            case INTEGER:
                return "%d";
            case DOUBLE:
                return "%f";
            case BOOLEAN:
                return "%d";
            case STRING:
                return "%s";
            case CHAR:
                return "%c";
            default:
                return "";
        }
    }

    private void setupFirms (Node node){
        ProgramOperationNode programNode = (ProgramOperationNode) node;

        //code needed for FunctionCall
        SymbolTable table = programNode.getProgramTable();
        ArrayList<SymbolTableRow> rows = table.getRowList();

        if(rows != null){ //can be null
            for(SymbolTableRow row : rows){
                if(row.getFirm() instanceof FunctionType){ //needed for filtering all the functions
                    String name = row.getId();
                    ArrayList<Boolean> refs = ((FunctionType) row.getFirm()).getHasRef();
                    firms.put(name, refs); //a reverse list is not needed here since it will be done already in FunCallNode
                }
            }
        }
    }

    public String doubleExpressionOperation(String operator, ExpressionOperationNode leftOperand, ExpressionOperationNode rightOperand){

        boolean arithOpCheck = operator.equals("PLUS") || operator.equals("MINUS") || operator.equals("TIMES") || operator.equals("DIV");
        boolean relop = operator.equals("LT") || operator.equals("LE") || operator.equals("GT") || operator.equals("GE") || operator.equals("EQ") || operator.equals("NE");
        boolean booleanCheck = operator.equals("AND") || operator.equals("OR");

        if(arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);
        }
        else if(arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }
        else if(arithOpCheck && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if(arithOpCheck && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if (booleanCheck && leftOperand.getType() == Type.BOOLEAN && rightOperand.getType() == Type.BOOLEAN){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.INTEGER){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.INTEGER){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if (relop && leftOperand.getType() == Type.INTEGER && rightOperand.getType() == Type.DOUBLE){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);

        }

        else if (relop && leftOperand.getType() == Type.DOUBLE && rightOperand.getType() == Type.DOUBLE){
            return (String) leftOperand.accept(this) + " " + getSymbolFromString(operator) + " " +  (String) rightOperand.accept(this);
        }

        else if(operator.equals("PLUS") && (leftOperand.getType() == Type.STRING || rightOperand.getType() == Type.STRING)){
            return "string_concat(" + objectToCString((String) leftOperand.accept(this), leftOperand.getType()) + ", " + objectToCString((String) rightOperand.accept(this), rightOperand.getType()) + ")";
        }
        else if(operator.equals("NE") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)){
            return "strcmp( " + rightOperand.accept(this) + ", " + leftOperand.accept(this) + ") != 0";
        }else if(operator.equals("EQ") && (leftOperand.getType() == Type.STRING && rightOperand.getType() == Type.STRING)){
            return "strcmp( " + rightOperand.accept(this) + ", " + leftOperand.accept(this) + ") == 0";


        }

        else {
            throw new RuntimeException(" The assignment does not have a match in the table!");
        }
    }

    public String singleExpressionOperator(UnaryOperationNode node){
        ExpressionOperationNode expression = node.getChild();
        String operator = node.getOperator();
        boolean minusCheck = operator.equals("MINUS");
        boolean notCheck = operator.equals("NOT") ;

        if(minusCheck && expression.getType() == Type.INTEGER){
            return "- " + node.getChild().accept(this);
        }

        if(minusCheck && expression.getType() == Type.DOUBLE){
            return "- " + node.getChild().accept(this);
        }

        if(notCheck && expression.getType() == Type.BOOLEAN){
            return "!(" + node.getChild().accept(this) + " )";

        }
        return null;
    }

    private String objectToCString(String expression, Type type) {
        switch (type) {
            case INTEGER: return "int2str(" + expression + ")";
            case CHAR: return "char2str(" + expression +  ")";
            case DOUBLE: return "float2str(" + expression +  ")";
            case BOOLEAN: return "bool2str(" + expression +  ")";
            case STRING: return expression;
            default: return "";

        }

    }

    private String getSymbolFromString(String operator){
        switch (operator){
            case "MINUS": return "-";
            case "TIMES": return "*";
            case "PLUS": return "+";
            case "DIV": return "/";
            case "AND": return "&&";
            case "OR": return "||";
            case "NOT": return "!";
            case "LT": return "<";
            case "LE": return "<=";
            case "EQ": return "==";
            case "GE": return ">=";
            case "GT": return ">";
            case "NE": return "!=";
        }
        return "";
    }

}

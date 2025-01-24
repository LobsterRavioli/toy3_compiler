package main.node;

import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.*;

public enum Type {
    INTEGER,
    BOOLEAN,
    DOUBLE,
    STRING,
    CHAR,
    NOTYPE;

    public static Type getTypeFromExpressionNode(ExpressionOperationNode costantNode){
        if(costantNode instanceof CharConstantNode){
            return Type.CHAR;
        } else if(costantNode instanceof DoubleConstNode){
            return Type.DOUBLE;
        } else if(costantNode instanceof FalseConstantNode){
            return Type.BOOLEAN;
        } else if(costantNode instanceof IntegerConstNode){
            return Type.INTEGER;
        } else if(costantNode instanceof StringConstantNode){
            return Type.STRING;
        } else if(costantNode instanceof TrueConstantNode) {
            return Type.BOOLEAN;
        }
        return null;
    }
}




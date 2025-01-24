package main.visitors.utils;

import main.node.Type;
import main.node.expr.ExpressionOperationNode;
import main.node.expr.constants.*;

import java.util.ArrayList;

public class VariableFirm implements Firm, Cloneable{
    private Type type;

    public VariableFirm() {
    }

    public VariableFirm(Type type) {
        this.type = type;
    }

    public VariableFirm(ExpressionOperationNode constant) {
        if(constant instanceof CharConstantNode){
            this.type = Type.CHAR;
        } else if(constant instanceof DoubleConstNode){
            this.type = Type.DOUBLE;
        } else if(constant instanceof FalseConstantNode){
            this.type = Type.BOOLEAN;
        } else if(constant instanceof IntegerConstNode){
            this.type = Type.INTEGER;
        } else if(constant instanceof StringConstantNode){
            this.type = Type.STRING;
        } else if(constant instanceof TrueConstantNode){
            this.type = Type.BOOLEAN;
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public ArrayList<Type> getMultipleTypes() {
        return null;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "VariableFirm{" +
                "type=" + type +
                '}';
    }

    @Override
    public Firm clone()  {
        VariableFirm cloned = new VariableFirm();

        // Clonazione profonda del campo `type`
        // Clonazione profonda del campo `type`
        if (this.type != null) {
            cloned.type = this.type; // Assumiamo che `Type` implementi `Cloneable`
        }

        return cloned;
    }


    public Boolean compareTypes(Type newType){
        Boolean result = false;
        if(this.type == newType){
            return true;
        }
        return  result;
    }

}

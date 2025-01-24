package main.visitors.utils;

import main.node.Type;

import java.util.ArrayList;

public class FunctionType implements Firm,Cloneable {

    public ArrayList<Boolean> getHasRef() {
        return hasRef;
    }

    public void setHasRef(ArrayList<Boolean> hasRef) {
        this.hasRef = hasRef;
    }

    private ArrayList<Boolean> hasRef;

    private ArrayList<Type> inputTypes;
    private Type outputType;

    public FunctionType() {
    }

    public FunctionType(ArrayList<Type> inputTypes, Type outputType) {
        this.inputTypes = inputTypes;
        this.outputType = outputType;
    }

    public FunctionType(ArrayList<Type> inputTypes, Type outputType, ArrayList<Boolean> hasRef) {
        this.inputTypes = inputTypes;
        this.outputType = outputType;
        this.hasRef = hasRef;
    }

    public FunctionType(Type outputType) {
        this.inputTypes = inputTypes;
        this.outputType = outputType;
    }

    public ArrayList<Type> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(ArrayList<Type> inputTypes) {
        this.inputTypes = inputTypes;
    }

    public Type getOutputType() {
        return outputType;
    }

    public void setOutputType(Type outputType) {
        this.outputType = outputType;
    }

    @Override
    public String toString() {
        return "FunctionType{" +
                "hasRef=" + hasRef +
                ", inputTypes=" + inputTypes +
                ", outputType=" + outputType +
                '}';
    }

    @Override
    public Firm clone() {

        FunctionType cloned = new FunctionType();

        // Clonazione profonda di inputTypes
        if (this.inputTypes != null) {
            cloned.inputTypes = new ArrayList<>();
            for (Type type : this.inputTypes) {
                cloned.inputTypes.add(type); // Assumiamo che Type implementi Cloneable
            }
        }

        // Clonazione profonda di outputType
        if (this.outputType != null) {
            cloned.outputType = this.outputType; // Assumiamo che Type implementi Cloneable
        }
        return cloned;
    }

    @Override
    public Type getType() {
        return this.outputType;
    }

    @Override
    public ArrayList<Type> getMultipleTypes() {
        return inputTypes;
    }
}

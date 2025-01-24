package main.visitors.utils;

import main.node.Type;
import main.node.expr.constants.IdentifierNode;

import java.util.ArrayList;

public class SymbolTable {

    public SymbolTable() {
    }


    private ArrayList<SymbolTableRow> rowList;
    private String name;
    private SymbolTable father;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolTable(ArrayList<SymbolTableRow> rowList, String name) {
        this.rowList = rowList;
        this.name = name;
    }

    public SymbolTable(String name) {
        this.rowList = new ArrayList<SymbolTableRow>();
        this.name = name;
    }

    public SymbolTable getFather() {
        return father;
    }


    public void addRow(SymbolTableRow table) throws DuplicateException {
        if(rowList.contains(table)){
            throw new DuplicateException("AOO non puoi");
        }
        rowList.add(table);
        return;
    }

    public ArrayList<SymbolTableRow> getRowList() {
        return rowList;
    }

    public void setRowList(ArrayList<SymbolTableRow> rowList) {
        this.rowList = rowList;
    }

    public void setFather(SymbolTable father) {
        this.father = father;
    }

    public ArrayList<SymbolTableRow> getRecordsList() {
        return rowList;
    }

    public void setRecordsList(ArrayList<SymbolTableRow> recordsList) {
        this.rowList = recordsList;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "name=" + name +
                ", rowList='" + rowList + '\'' +
                ", father=" + father +
                '}';
    }


    public boolean contains(IdentifierNode node, String kind){
        SymbolTableRow toSearch = new SymbolTableRow(node.getName(), kind);

        if (rowList != null){
            for (SymbolTableRow current: rowList){
                if(current.equals(toSearch)) return true;
            }
        }

        return false;
    }


    public SymbolTableRow getRow(IdentifierNode node, String kind){
        SymbolTableRow toSearch = new SymbolTableRow(node.getName(), kind);
        if (rowList != null){
            for (SymbolTableRow current: rowList){
                if(current.equals(toSearch)){
                    return current;
                }
            }
        }

        return null;
    }




    public SymbolTable clone() {

        SymbolTable cloned =  new SymbolTable();

        if (this.rowList != null) {
            cloned.rowList = new ArrayList<>();
            for (SymbolTableRow row : this.rowList) {
                cloned.rowList.add(row.clone());
            }
        }
        // Clonazione profonda di `father`
        if (this.father != null) {
            cloned.father = (SymbolTable) this.father.clone();
        }

        cloned.name = this.name;

        return cloned;
    }

}

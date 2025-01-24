package main.visitors.utils;

public class SymbolTableRow {

    private String id;
    private String kind;
    private Firm firm;
    private boolean isReference;

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean reference) {
        isReference = reference;
    }

    public SymbolTableRow() {
    }

    public SymbolTableRow(String id, String properties, Firm firm) {
        this.id = id;
        this.kind = properties;
        this.firm = firm;
    }

    public SymbolTableRow(String id, String kind)
    {
        this.id = id;
        this.kind = kind;

    }
    public SymbolTableRow(String id, String kind, Firm firm, boolean isReference) {
        this.id = id;
        this.kind = kind;
        this.firm = firm;
        this.isReference = isReference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    @Override
    public boolean equals(Object obj) {
        SymbolTableRow row =  (SymbolTableRow) obj;
        return this.id.equals(row.getId()) && row.kind.equals(row.getKind());
    }

    @Override
    public String toString() {
        return "SymbolTableRow{" +
                "id='" + id + '\'' +
                ", kind='" + kind + '\'' +
                ", firm=" + firm +
                ", isReference=" + isReference +
                '}';
    }

    public SymbolTableRow clone() {
        SymbolTableRow clone = new SymbolTableRow();
        clone.id = this.id;
        clone.firm = this.firm.clone();
        clone.isReference = this.isReference;
        return clone;
    }

    public VariableFirm getVariableFirmFromId(){
        return null;
    }
}

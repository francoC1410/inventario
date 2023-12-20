package Models;

public class Registro {

    private int id;
    private String code;
    private String name;
    private String name1;
    private String name2;
    private String date;
    private int quantity;
    private int quantity2;
    private String created;
    private String updated;
    private String packages;

    public Registro() {

    }

    public Registro(int id, String code, String name, String name1, String name2, String date, int quantity, int quantity2, String created, String updated, String packages) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.name1 = name1;
        this.name2 = name2;
        this.date = date;
        this.quantity = quantity;
        this.quantity2 = quantity2;
        this.created = created;
        this.updated = updated;
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(int quantity2) {
        this.quantity2 = quantity2;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    
}

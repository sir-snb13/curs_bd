package org.example.curs_bd;

public class Singleton {
    private final static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    private Integer id;
    private boolean isClient;
    private String name;

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }

    public boolean getIsClient() {
        return isClient;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

}

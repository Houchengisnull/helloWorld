package org.hc.learning.jvm.reference;

public class User {

    private String name;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "------ id:" + id + " ------ name:" + name + " ------";
    }

}

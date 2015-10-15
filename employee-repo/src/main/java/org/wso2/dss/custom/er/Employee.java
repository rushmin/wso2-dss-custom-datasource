package org.wso2.dss.custom.er;

public class Employee {

    private String name;
    private int age;
    private String team;
    private String id;

    public Employee(String id, String name, int age, String team) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}

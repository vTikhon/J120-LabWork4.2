package ru.avalon.vergentev.j120.labwork4b;

public class Client {
    private String dateReg;
    private String name;
    private String address;
    private String dateOfBirth;
    private String age;

    public Client() {}

    public Client(String dateReg, String name, String address, String dateOfBirth, String age) {
        setDateReg(dateReg);
        setName(name);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setAge(age);
    }

    @Override
    public String toString() {
        return "Client{" +
                "dateReg='" + dateReg + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getDateReg() {return dateReg;}
    public void setDateReg(String dateReg) {this.dateReg = dateReg;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}
    public String getAge() {return age;}
    public void setAge(String age) {this.age = age;}
}

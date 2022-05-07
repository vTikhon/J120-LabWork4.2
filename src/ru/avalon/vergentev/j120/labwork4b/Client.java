package ru.avalon.vergentev.j120.labwork4b;

public class Client {
    private String dateReg;
    private String name;
    private String address;

    public Client() {}

    public Client(String dateReg, String name, String address) {
        setDateReg(dateReg);
        setName(name);
        setAddress(address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "dateReg='" + dateReg + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package ru.avalon.vergentev.j120.labwork4b;

public class Company {
    private String dateReg;
    private String nameCompany;
    private String addressCompany;
    private String directorName;
    private String contactPersonName;

    public Company() {}

    public Company(String dateReg, String nameCompany, String addressCompany, String directorName, String contactPersonName) {
        setDateReg(dateReg);
        setNameCompany(nameCompany);
        setAddressCompany(addressCompany);
        setDirectorName(directorName);
        setContactPersonName(contactPersonName);
    }

    @Override
    public String toString() {
        return "Company{" +
                "dateReg='" + dateReg + '\'' +
                ", nameCompany='" + nameCompany + '\'' +
                ", addressCompany='" + addressCompany + '\'' +
                ", directorName='" + directorName + '\'' +
                ", contactPersonName='" + contactPersonName + '\'' +
                '}';
    }

    public String getDateReg() {return dateReg;}
    public void setDateReg(String dateReg) {this.dateReg = dateReg;}
    public String getNameCompany() {return nameCompany;}
    public void setNameCompany(String nameCompany) {this.nameCompany = nameCompany;}
    public String getAddressCompany() {return addressCompany;}
    public void setAddressCompany(String addressCompany) {this.addressCompany = addressCompany;}
    public String getDirectorName() {return directorName;}
    public void setDirectorName(String directorName) {this.directorName = directorName;}
    public String getContactPersonName() {return contactPersonName;}
    public void setContactPersonName(String contactPersonName) {this.contactPersonName = contactPersonName;}
}

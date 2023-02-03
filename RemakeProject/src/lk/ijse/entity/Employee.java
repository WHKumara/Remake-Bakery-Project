package lk.ijse.entity;

public class Employee {
    private String EmpID;
    private String EmpName;
    private int EmpContact;
    private String EmpAddress;
    private String EmpEmail;

    public Employee() {
    }

    public Employee(String empID, String empName, int empContact, String empAddress, String empEmail) {
        this.EmpID = empID;
        this.EmpName = empName;
        this.EmpContact = empContact;
        this.EmpAddress = empAddress;
        this.EmpEmail = empEmail;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        this.EmpID = empID;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        this.EmpName = empName;
    }

    public int getEmpContact() {
        return EmpContact;
    }

    public void setEmpContact(int empContact) {
        this.EmpContact = empContact;
    }

    public String getEmpAddress() {
        return EmpAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.EmpAddress = empAddress;
    }

    public String getEmpEmail() {
        return EmpEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.EmpEmail = empEmail;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "EmpID='" + EmpID + '\'' +
                ", EmpName='" + EmpName + '\'' +
                ", EmpContact=" + EmpContact +
                ", EmpAddress='" + EmpAddress + '\'' +
                ", EmpEmail='" + EmpEmail + '\'' +
                '}';
    }
}

package ATM;

public class user {
    private String name;//姓名
    private String Pnumber;//电话号码
    private String  ID;//身份证号码
    private String bkID;//银行卡号码
    private double balance;//存款
    private String password;//密码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnumber() {
        return Pnumber;
    }

    public void setPnumber(String pnumber) {
        Pnumber = pnumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBkID() {
        return bkID;
    }

    public void setBkID(String bkID) {
        this.bkID = bkID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.substring(6);
    }
}

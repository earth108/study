import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private String authority;

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(ArrayList<Account> accounts) {
        Account.accounts = accounts;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account() {

    }
//将权限的值传递回来
    public Account(String authority){
        this.setAuthority(authority);
        try{
            readUser();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void readUser() throws Exception {
        if (getAuthority().equals("管理员")) {
            Account account = new Account();

            String url = "jdbc:mysql://localhost:3306/test_2?useSSL=false";
            String user = "root";
            String password = "hsp";

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql1 = "select username,`password` from teacher ";
//            String sql2 = "select password from teacher ";


            ResultSet rs1 = stmt.executeQuery(sql1);
            // ResultSet rs2 = stmt.executeQuery(sql2);
            rs1.last();
//            rs1.getString(1);
            //  rs2.getString(1);

            account.setUsername(rs1.getString(1));
            account.setPassword(rs1.getString(2));
            //  account.setPassword(rs2.getString(1));
            accounts.add(account);
            rs1.close();
        } else if (getAuthority().equals("学生")) {
            Account account = new Account();

            String url = "jdbc:mysql://localhost:3306/test_2?useSSL=false";
            String user = "root";
            String password = "hsp";

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql1 = "select username,`password` from student ";
            ResultSet rs1 = stmt.executeQuery(sql1);

            /*指针移动到表的最后一行
            再获取最后一行第一二列的值
            将该值作为账户和密码
            */

            rs1.last();
//            rs1.getString(1);

            account.setUsername(rs1.getString(1));
            account.setPassword(rs1.getString(2));

            accounts.add(account);

            rs1.close();
        }

    }
}

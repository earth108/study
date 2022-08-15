import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class Register extends JFrame implements ActionListener {
    JTextField jtf;
    JPasswordField jpf;
    JLabel jl1,jl2;
    JButton jb1;
    JPanel jp1,jp2,jp3;

    public Register() {

        jl1 = new JLabel("账号：");
        jl2 = new JLabel("密码：");
        ButtonGroup buttonGroup = new ButtonGroup();
        jtf = new JTextField(20);
        jpf = new JPasswordField(20);
        jb1 = new JButton("注册");

    }

    public void lunch() {
        jb1.addActionListener(this);
        jl1.setBounds(50, 10, 120, 30);
        jtf.setBounds(90, 10, 250, 30);
        jl2.setBounds(50, 60, 120, 30);
        jpf.setBounds(90, 60, 250, 30);

        jb1.setBounds(130, 140, 100, 30);

        add(jl1);
        add(jl2);
        add(jtf);
        add(jpf);
        add(jb1);
        setLayout(null);
        setTitle("注册页面");
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确认退出？", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false);


    }


// 页面绘制测试
  /* public static void main(String[] args) {
        new Register().lunch();
    }*/


    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("注册")) {

            try {
                String url = "jdbc:mysql://localhost:3306/test_2?useSSL=false";
                String user = "root";
                String password = "hsp";

                Class.forName("com.mysql.jdbc.Driver");

                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);


                String sql =  "insert into student " +"values( "+ "'" +jtf.getText()+ "'" + " , " + "'" + jpf.getText() + "'" + ")";

//                PreparedStatement pstmt = conn.prepareStatement(sql);

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "注册成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
                dispose();

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }
    }
}

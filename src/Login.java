import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame implements ActionListener{
    private JLabel jl1, jl2, jl3;
    private JTextField jtf;
    private JPasswordField jpf;
    private JButton jb1,jb2;
    private JRadioButton radioButton1, radioButton2;

    public static String username;

    public static final void setUsername(String username) {
        Login.username = username;
    }

    public static final String getUsername() {
        return username;
    }


    Login() {
        jl1 = new JLabel("账号：");
        jl2 = new JLabel("密码：");
        jl3 = new JLabel("权限：");
        radioButton1 = new JRadioButton("管理员", true);
        radioButton2 = new JRadioButton("学生");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        jtf = new JTextField(20);
        jpf = new JPasswordField(20);
        jb1 = new JButton("登录");
        jb2 = new JButton("注册");
    }

//      窗口大小的绘制
    public void launch() {
        jb1.addActionListener(this);//this指本身这个对象，这个类会实现监听器这个接口
        jb2.addActionListener(this);

        jl1.setBounds(50, 10, 120, 30);
        jtf.setBounds(90, 10, 250, 30);
        jl2.setBounds(50, 60, 120, 30);
        jpf.setBounds(90, 60, 250, 30);
        jl3.setBounds(50, 100, 50, 30);
        radioButton1.setBounds(90, 100, 70, 30);
        radioButton2.setBounds(160, 100, 100, 30);
        jb1.setBounds(45, 140, 100, 30);
        jb2.setBounds(200,140,100,30);

        add(jl1);
        add(jl2);
        add(jtf);
        add(jpf);
        add(jb1);
        add(jb2);
        add(jl3);
        add(radioButton1);
        add(radioButton2);
        setLayout(null);
        setTitle("登录页面");
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

        //检查登录的状态
    public boolean checkLogin(){

        String radioValue = null;
        if (radioButton1.isSelected()) {
            radioValue = "管理员";
        } else if (radioButton2.isSelected()) {
            radioValue = "学生";
        }
        new Account(radioValue);

        for (int i = 0; i < Account.getAccounts().size(); i++) {
            if (Account.getAccounts().get(i).getUsername().equals(jtf.getText()) &&
                    Account.getAccounts().get(i).getPassword().equals(jpf.getText())) {
                Account.getAccounts().clear();
                Account.setAccounts(Account.getAccounts());
                return true;
            }
        }
        Account.getAccounts().clear();
        Account.setAccounts(Account.getAccounts());
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().equals("登录")) {
//      测试      System.out.println("登录按钮被点击了");
            if (jtf.getText().isEmpty() || jpf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
                jtf.setText("");
                jpf.setText("");
            } else if (checkLogin()) {
                setUsername(jtf.getText());
                JOptionPane.showMessageDialog(null, "登录成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
                dispose();//释放窗体占用的资源
                if (radioButton1.isSelected()){
                    new TeacherMenu(getUsername());
                }else{
                    new StudentMenu();
                }
            } else {
                JOptionPane.showMessageDialog(null, "账号或密码错误！", "提示消息", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getActionCommand().equals("注册")) {
            new Register().lunch();
//            System.out.println("注册按钮被点击了");

        }
    }

}

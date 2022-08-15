import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteTest extends JFrame implements ActionListener {
    private JLabel jl2, jl3, jl4, jl1;
    private JTextField jtf1, jtf2, jtf3, jtf4;
    private JButton jb1, jb2;
    private Container container;
    static String Sno;//学号
    static String Sname; //姓名
    private String name;//进入测试的名字

    public static String getSno() {
        return Sno;
    }

    public static void setSno(String sno) {
        Sno = sno;
    }

    public static String getSname() {
        return Sname;
    }

    public static void setSname(String sname) {
        Sname = sname;
    }

    public CompleteTest(String name) {
        this.name = name;
        container = getContentPane();
        jl1 = new JLabel("姓名：");
        jl2 = new JLabel("学号：");
        jl3 = new JLabel("专业：");
        jl4 = new JLabel("班级：");
        jtf1 = new JTextField();
        jtf2 = new JTextField();
        jtf3 = new JTextField();
        jtf4 = new JTextField();
        jb1 = new JButton("提交");
        jb2 = new JButton("重置");
    }

    public void launch() {
        setTitle("完善资料");
        container.setLayout(null);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        setVisible(true);
        setSize(250, 300);
        jl1.setBounds(30, 10, 40, 30);
        jtf1.setBounds(70, 10, 140, 30);
        jl2.setBounds(30, 60, 40, 30);
        jtf2.setBounds(70, 60, 140, 30);
        jl3.setBounds(30, 110, 40, 30);
        jtf3.setBounds(70, 110, 140, 30);
        jl4.setBounds(30, 160, 40, 30);
        jtf4.setBounds(70, 160, 140, 30);
        jb1.setBounds(30, 210, 80, 30);
        jb2.setBounds(130, 210, 80, 30);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        container.add(jtf1);
        container.add(jtf2);
        container.add(jtf3);
        container.add(jtf4);
        container.add(jl1);
        container.add(jl2);
        container.add(jl3);
        container.add(jl4);
        container.add(jb1);
        container.add(jb2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("提交")) {
            if (jtf1.getText().isEmpty() || jtf2.getText().isEmpty() || jtf3.getText().isEmpty() || jtf4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "信息不能为空！");
            } else {
                setSno(jtf2.getText());
                setSname(jtf1.getText());
                this.setVisible(false);
                new DoTest_Paper(name,this).launch();
            }
        }
        if (e.getActionCommand().equals("重置")) {
            jtf1.setText("");
            jtf2.setText("");
            jtf3.setText("");
            jtf4.setText("");
        }
    }
}

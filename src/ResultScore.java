import javax.swing.*;

public class ResultScore extends JFrame {
    private JLabel jLabel;
    private JTextField jtf;
    private JButton jButton1, jButton2;

    public ResultScore() {
        jLabel = new JLabel("请输入及格分数");
        jtf = new JTextField();
        jButton1 = new JButton("提交");
//        jButton2 = new JButton("重置");
    }

    public void launch() {
        setTitle("分数");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        setLayout(null);
        setSize(400, 120);
        jLabel.setBounds(30, 10, 100, 30);
        jtf.setBounds(130, 10, 200, 30);
        jButton1.setBounds(110, 50, 130, 30);

        add(jLabel);
        add(jtf);
        add(jButton1);

    }
}

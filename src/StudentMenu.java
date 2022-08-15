import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class StudentMenu extends JFrame {
    private Container contentPane;
    private JLabel label1;
    private JButton jButton;



    public StudentMenu() {
        label1 = new JLabel();
        jButton = new JButton("在线聊天");
        initComponents();
    }

    private void initComponents() {
        label1.setText("心理系统学生端");
        label1.setFont(new Font("宋体", Font.BOLD, 25));
        label1.setForeground(Color.CYAN);
        setTitle("学生窗口");
        setResizable(false);
        setVisible(true);
        label1.setBounds(100, 5, 350, 40);
        jButton.setBounds(200,350,100,50);
        contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(label1);
        contentPane.add(jButton);
        new Exam_1(contentPane, this);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }
}

class Exam_1 extends JFrame implements ActionListener {
    static ArrayList<String> arrayList = new ArrayList<>(); //存取文件名
    private ArrayList<JButton> buttons;//把预读的文件设计为按钮的样式点击
    private JScrollPane scrollPane;
    private JPanel jPane;
    static ArrayList<String> names = new ArrayList<>();
    private Container container1;

    public Exam_1(Container container, Container container1) {
        this.container1 = container1;
        buttons = new ArrayList<>();
        scrollPane = new JScrollPane();
        //网格布局
        jPane = new JPanel(new GridLayout(100, 1));
        scrollPane.setViewportView(jPane);
        getButtonName();
        for (int i = 0; i < arrayList.size(); i++) {
            String[] line = arrayList.get(i).split("_");
            String name = line[0] + " 发布人：" + line[1] + "发布时间：" + line[2].replace("-", "/") + " " + line[3].replace("-", ":");
            names.add(name);
            JButton jButton = new JButton(name);
            buttons.add(jButton);
        }
        //传入到监听器
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addActionListener(this);
        }
        //补充在面板上
        for (int i = 0; i < buttons.size(); i++) {
            JPanel panel = new JPanel();
            panel.add(buttons.get(i), BorderLayout.CENTER);
            jPane.add(panel);
        }
        scrollPane.setBounds(50, 50, 400, 380);
        container.add(scrollPane);
    }
//获取文件名
    public void getButtonName() {
        File file = new File("./lib/Examination");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                arrayList.add(files[i].getName());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < names.size(); i++) {
            //学生作答试卷的信息保存位置
            if (e.getActionCommand().equals(names.get(i))) {
                //将作答情况存入该文件
                File file = new File("./lib/Examination/" + arrayList.get(i) + "/" + CompleteTest.getSno() + "_"
                        + CompleteTest.getSname() + ".txt");
                //未作答时，运行作答的程序，反之则提醒已作答
                if (file.exists()) {
                    JOptionPane.showMessageDialog(null, "不允许重复作答！");
                } else {
                    container1.setVisible(false);
                    new CompleteTest(arrayList.get(i)).launch();
                }
            }
        }
    }
}

class DoTest_Paper extends JFrame {
    private JLabel jLabel;
    private String fileName;
    private ArrayList<String[]> lists = new ArrayList<String[]>();
    private Container container;
    private JScrollPane scrollPane;
    private JPanel jPane;
    private ArrayList<JRadioButton[]> radioButtons = new ArrayList<>();
    private JButton jButton;
    private int score = 0;
    private Container compane;
    private static ArrayList<String> commands = new ArrayList<>();//存放分析结果的每一行

    static {
        try {
            readComment();//执行读取分析结果
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    DoTest_Paper(String fileName, Container compane) {
        this.compane = compane;
        this.fileName = fileName;
        container = getContentPane();
        scrollPane = new JScrollPane();
        try {
        jButton = new JButton("提交");
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jLabel = new JLabel("请选择正确的答案（单选）");
        jPane = new JPanel(new GridLayout(200, 1));
    }

    public void launch() {
        scrollPane.setViewportView(jPane);
        setTitle("在线答题");
        setSize(400, 530);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        container.add(jLabel);
        jLabel.setBounds(0, 0, 150, 30);
        scrollPane.setBounds(10, 30, 370, 400);
        container.add(scrollPane);
        jButton.setBounds(100, 450, 150, 30);
        container.add(jButton);
        for (int i = 0; i < lists.size(); i++) {
            JPanel panel = new JPanel();
            JPanel panel1 = new JPanel(new GridLayout(4, 2));
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel(new BorderLayout());
            JLabel jl1 = new JLabel(lists.get(i)[0]);
            JRadioButton radioButton1 = new JRadioButton("A：" + lists.get(i)[1]);
            JRadioButton radioButton2 = new JRadioButton("B：" + lists.get(i)[3]);
            JRadioButton radioButton3 = new JRadioButton("C：" + lists.get(i)[5]);
            JRadioButton radioButton4 = new JRadioButton("D：" + lists.get(i)[7]);
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(radioButton1);
            buttonGroup.add(radioButton2);
            buttonGroup.add(radioButton3);
            buttonGroup.add(radioButton4);
            panel1.add(radioButton1);
            panel1.add(radioButton2);
            panel1.add(radioButton3);
            panel1.add(radioButton4);
            radioButtons.add(new JRadioButton[]{
                    radioButton1, radioButton2, radioButton3, radioButton4}
            );
            panel2.add(jl1, BorderLayout.CENTER);
            panel2.setPreferredSize(new Dimension(280, 50));
            panel.add(panel3);
            panel3.add(panel2, BorderLayout.NORTH);
            panel3.add(panel1, BorderLayout.CENTER);
            jPane.add(panel);
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("提交")) {
                    boolean flag = true;
                    for (int i = 0; i < radioButtons.size(); i++) {
                        if ((!radioButtons.get(i)[0].isSelected()) && (!radioButtons.get(i)[1].isSelected()) &&
                                (!radioButtons.get(i)[2].isSelected()) && (!radioButtons.get(i)[3].isSelected())) {
                            JOptionPane.showMessageDialog(null, "答案不能为空！");
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {

                        scoreCalculation();
                        JOptionPane.showMessageDialog(null, "提交完毕！");
                        try {
                            writeTest();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        score = 0;
                        dispose();
                        Exam_1.names.clear();
                        Exam_1.arrayList.clear();
                        compane.setVisible(false);
                        new StudentMenu();
                    }
                }
            }
        });
    }

    public void writeTest() throws IOException {
        File file = new File("./lib/Examination/" + fileName + "/" + CompleteTest.getSno() + "_" + CompleteTest.getSname() + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");//指定以UTF-8格式写入文件
        String line = null;
        //根据不同成绩，对应录入不同的分析结果
        if (score >= 0 && score <= 30) {
            line = commands.get(2);
        } else if (score > 30 && score <= 80) {
            line = commands.get(1);
        } else if (score > 80 && score <= 100) {
            line = commands.get(0);
        }
        writer.write(CompleteTest.getSname() + " " + line + " " + score);
        writer.close();
    }
//读取试卷信息
    public void readFile() throws IOException {
        File file = new File("./lib/TestPaper/" + fileName + ".txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lines = line.split("\\s+");
            String[] a = new String[9];
            a[0] = lines[0];
            a[1] = lines[1];
            a[2] = lines[2];
            a[3] = lines[3];
            a[4] = lines[4];
            a[5] = lines[5];
            a[6] = lines[6];
            a[7] = lines[7];
            a[8] = lines[8];
            lists.add(a);
        }
    }
    //预读取分析结果
    public static void readComment() throws IOException {
        File file = new File("./lib/comment.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while ((line = br.readLine()) != null) {
            commands.add(line);
        }
    }
//成绩结算
    public void scoreCalculation() {
        for (int i = 0; i < lists.size(); i++) {
            if (radioButtons.get(i)[0].isSelected()) {
                score += Integer.parseInt(lists.get(i)[2]);
            }
            if (radioButtons.get(i)[1].isSelected()) {
                score += Integer.parseInt(lists.get(i)[4]);
            }
            if (radioButtons.get(i)[2].isSelected()) {
                score += Integer.parseInt(lists.get(i)[6]);
            }
            if (radioButtons.get(i)[3].isSelected()) {
                score += Integer.parseInt(lists.get(i)[8]);
            }
        }
    }
}

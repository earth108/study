//import util.EnterTitle;
//import util.GeneratingTestPaper;
//import util.SetUpTestResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class TeacherMenu extends JFrame implements ActionListener {
    private JMenuBar menuBar2;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JLabel label1;
    private Container contentPane;
    private String name;

    public TeacherMenu(String name) {
        this.name = name;
        initComponents();
    }

    private void initComponents() {
        menuBar2 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);
        label1 = new JLabel();
        setTitle("管理员");
        setResizable(false);
        setVisible(true);
        contentPane = getContentPane();
        contentPane.setLayout(null);
        menu1.setText("菜单");
        menuItem1.setText("录入题目");
        menu1.add(menuItem1);
        menuItem2.setText("生成试卷");
        menu1.add(menuItem2);
        menuItem3.setText("测试分数段划分");
        menu1.add(menuItem3);
        menuItem4.setText("在线聊天");
        menu1.add(menuItem4);

        menuBar2.add(menu1);
        setJMenuBar(menuBar2);
        label1.setText("心理系统管理端");
        label1.setFont(new Font("宋体", Font.BOLD, 25));
        label1.setForeground(Color.BLUE);
        contentPane.add(label1);
        label1.setBounds(150, 0, 250, 40);
        new Exam_2(contentPane);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("录入题目")) {
            new EnterTest();

        }
        if (e.getActionCommand().equals("生成试卷")) {

            new GreateTest();

        }
        if (e.getActionCommand().equals("测试分数段划分")) {
            new ResultScore().launch();
        }
    }
}


class Exam_2 implements ActionListener {
    static ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<JButton> buttons;
    private JScrollPane scrollPane;//滚动面板
    private JPanel jPanel;
    private int Y = 10;
    static ArrayList<String> names = new ArrayList<>();

    //将调查分析作为按钮呈现在滚动窗口上
    public Exam_2(Container container) {
        buttons = new ArrayList<>();
        scrollPane = new JScrollPane();
        //采用网格布局
        jPanel = new JPanel(new GridLayout(100,1));
        scrollPane.setViewportView(jPanel);
        getButtonName();
        for (int i = 0; i < arrayList.size(); i++) {
            String[] line = arrayList.get(i).split("_");
            String name = line[0] + " 发布人：" + line[1] + "发布时间：" + line[2].replace("-", "/") + " " + line[3].replace("-", ":");
            names.add(name);
            JButton Button_test = new JButton(name);
            buttons.add(Button_test);
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addActionListener(this);
        }
        for (int i = 0; i < buttons.size(); i++) {
            JPanel panel = new JPanel();
            panel.add(buttons.get(i),BorderLayout.CENTER);
            jPanel.add(panel);
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
            if (e.getActionCommand().equals(names.get(i))) {
                try {
                    new Create_test_report(arrayList.get(i)).launch();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}

class Create_test_report extends JFrame {
    private String name;
    private ArrayList<String[]> lists = new ArrayList<>();
    private JScrollPane scrollPane;
    private JTable table;
    //用来存储单元格对象
    private DefaultTableModel defaultTable;

    public Create_test_report(String name) {
        this.name = name;
        scrollPane = new JScrollPane();
        table = new JTable();
        defaultTable = (DefaultTableModel) table.getModel();
        scrollPane.setViewportView(table);
    }

    public void launch() throws IOException {
        readCase();
        defaultTable.addColumn("测试人");
        defaultTable.addColumn("分数");
        defaultTable.addColumn("测试评价");
        for (int i = 0; i < lists.size(); i++) {
            defaultTable.addRow(lists.get(i));
        }
        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(40);
        tableColumnModel.getColumn(1).setPreferredWidth(30);
        tableColumnModel.getColumn(2).setPreferredWidth(330);
        add(scrollPane);
        setSize(400, 200);
        setTitle("测试情况");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
//访问结果
    public void readCase() throws IOException {
        File file = new File("./lib/Examination/" + name);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\s+");
                String[] a = new String[3];
                a[0] = lines[0];
                a[1] = lines[2];
                a[2] = lines[1];
                lists.add(a);
            }
        }
    }
}
import javax.swing.*;
import java.awt.*;


public class GreateTest extends JFrame {
    public GreateTest() {
        setTitle("生成试卷");
        setBounds(230,150,400,200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JLabel jl=new JLabel("功能暂待后期开发，敬请期待~");
        Container c=getContentPane();
        c.add(jl);
        setVisible(true);
    }
}

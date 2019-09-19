package org.javatribe.calculator.utils;

import org.javatribe.calculator.common.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoRun extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int DELAY = 50;// 转动快慢设置
    //	private final static Long time = (long) 5000;	//窗体关闭事件
    private static Timer timer;    //动画计时器
    private static int x = 0;
    private static int rdoWidth = 100;

    String message = "正在处理，请稍后。。。";
    JPanel jPanel;


    /**
     * 面板构造函数，初始化面板。包括Timer 的场景。
     */
    public DoRun(JPanel jPanel, String message) {
        this.jPanel = jPanel;
        this.message = message;
        start();
    }


    /**
     * 面板构造函数，初始化面板。包括Timer 的场景。
     */
    public void start() {
        timer = new Timer(DELAY, new ReboundListener());
        timer.start();

        setSize(jPanel.getWidth(), jPanel.getHeight());
        setName("do_run_instance");
        jPanel.add(this);

        Container rootP = this.getRootPane().getContentPane();
        rootP.validate();
        rootP.repaint();
    }

    /**
     * 面板构造函数，初始化面板。包括Timer 的场景。
     */
    public DoRun(JPanel jPanel) {
        this.jPanel = jPanel;
        start();
    }

    public void stop(JFrame jFrame) {
        Container rootP = jFrame.getContentPane();
        timer.stop();
        rootP.remove(SwingUtils.searchComponentByName(rootP, "do_run_instance"));
        rootP.validate();
        rootP.repaint();
    }

    public void stop() {
        Container rootP = this.getRootPane().getContentPane();
        timer.stop();
        rootP.remove(SwingUtils.searchComponentByName(rootP, "do_run_instance"));
        rootP.validate();
        rootP.repaint();
    }


    /**
     * 动画效果：不断的更新图像的位置，以达到动画的效果。
     */
    private class ReboundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x < 360) {
                //控制每个DELAY周期旋转的角度，+ 为逆时针  - 为顺时针
                x = x - 5;
            } else {
                x = 0;
            }
            repaint();
        }
    }

    /**
     * 绘出图像在面板中的位置
     */
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        drawArc(page);
    }

    /**
     * 画图形
     */
    private void drawArc(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        //抗锯齿
        //JDK文档：http://tool.oschina.net/uploads/apidocs/jdk-zh/java/awt/RenderingHints.html
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        //设置画笔颜色
        g2d.setColor(Color.BLACK);
        g2d.drawArc(width / 2 - rdoWidth + 40, height / 2 - 110, 10 + rdoWidth, 10 + rdoWidth, 0, 360);
        g2d.setColor(new Color(102, 205, 170));
        g2d.fillArc(width / 2 - rdoWidth + 40, height / 2 - 110, 10 + rdoWidth, 10 + rdoWidth, x, 240);
        g2d.setColor(Color.BLACK);
        g2d.fillArc(width / 2 - rdoWidth + 20 + 40, height / 2 - 90, 10 + rdoWidth - 40, 10 + rdoWidth - 40, 0, 360);

        final FontMetrics metrics = g2d.getFontMetrics(new Font("Courier", Font.PLAIN, 25));
        final int textLength = metrics.stringWidth(message);
        final int textHeight = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
        g2d.drawString(message, Math.abs(width - textLength) / 2 + 70, Math.abs(height + textHeight) / 2 + 15);
        g2d.dispose();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
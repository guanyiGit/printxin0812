package org.javatribe.calculator.common;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopTimingTip extends JComponent {


    private JPanel mainPanel;
    private JTextPane textComponent;
    private TipListener tipListener;
    private Component tipParent;
    private int initTime = 0;
    private int lastTime = 0;
    private int vanishTime = 0;
    private JLayeredPane windowLayer;//窗口的遮罩层，不能随便修改，只作父对象应用
    private Point constPoint = new Point(12, 5);//距离鼠标常量，防止鼠标遮挡
    private Point constPoint2 = new Point(2, 2);//常量2 防止离鼠标太近，触发mouseexit事件
    private Timer tipTimer;
    private TipTimerListener tipTimerListener;
    private static PopTimingTip popTimingTip;
    private JRootPane rootPane;//当前根面板
    private Dimension curTipSize;
    private int curConType;

    /**
     * 单例外部不允许初始化
     */
    private PopTimingTip() {
        super();
        initTip();
    }

    public static PopTimingTip getInstance() {
        if (popTimingTip == null) {
            popTimingTip = new PopTimingTip();
        }
        return popTimingTip;
    }

    private void initTip() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        //this.setBorder(null);
        this.setVisible(false);
        textComponent = new JTextPane();
        textComponent.setContentType("text/html");
        textComponent.setBorder(new LineBorder(Color.BLACK));
        textComponent.setBackground(new Color(245, 245, 245));
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(textComponent, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.CENTER);

        tipTimerListener = new TipTimerListener();
        tipTimerListener.state = 0;

        tipListener = new TipListener();
        tipTimer = new Timer(0, tipTimerListener);
        tipTimer.setRepeats(false);

        curTipSize = new Dimension(0, 0);
    }

    public void showTip() {
        this.setVisible(true);
    }

    /**
     * 为某个组件设置tip
     *
     * @param parent 显示tooltip的对象
     * @param text
     */
    public void showTipText(JComponent parent, String text) {
        if (parent == null) {
            return;
        }
        //如果进入了新的组件，先从旧组件中移除侦听防止泄漏
        if (tipParent != null && tipParent != parent) {
            tipParent.removeMouseListener(tipListener);
            tipParent.removeMouseMotionListener(tipListener);
        }
        tipParent = parent;

        rootPane = parent.getRootPane();
        //防止异常获取不了根面板的情况
        if (rootPane == null) {
            return;
        }

        JLayeredPane layerPane = rootPane.getLayeredPane();
        //先从旧面板中移除tip
        if (windowLayer != null && windowLayer != layerPane) {
            windowLayer.remove(this);
        }
        windowLayer = layerPane;
        //防止还有没有移除侦听的组件
        tipParent.removeMouseListener(tipListener);
        tipParent.removeMouseMotionListener(tipListener);
        layerPane.remove(this);
        //放置tip在遮罩窗口顶层
        layerPane.add(this, JLayeredPane.POPUP_LAYER);
        //窗口遮罩层添加侦听
        tipParent.addMouseMotionListener(tipListener);
        tipParent.addMouseListener(tipListener);
        //测试侦听器数量
        //System.out.println(tipParent.getMouseListeners().length + " " + tipParent.getMouseMotionListeners().length);
        //设置tiptext
        textComponent.setText(text);
        mainPanel.doLayout();
        //this.setPreferredSize(textComponent.getPreferredSize());
        curTipSize = textComponent.getPreferredSize();
        this.setSize(textComponent.getPreferredSize().width, textComponent.getPreferredSize().height);
    }

    /**
     * 初始化toolTip
     *
     * @param contentType 0:html  1:文本类型
     * @param initTime    鼠标进入后等待时间
     *                    //     * @param lastTime    持续时间(未完成)
     *                    //     * @param vanishTime  鼠标移走后消失时间(未完成)
     */
    public void setConfigure(int contentType, int initTime) {
        if (contentType == 0 && curConType != contentType) {
            textComponent.setContentType("text/html");
        } else if (contentType == 1 && curConType != contentType) {
            textComponent.setContentType("text/plain");
        }
        curConType = contentType;
        this.initTime = initTime;
        //this.vanishTime = vanishTime;
        //this.lastTime = lastTime;
    }

    /**
     * 坐标转换，标签跟随鼠标移动
     */
    private void followWithMouse(MouseEvent e) {
        if (windowLayer == null) {
            return;
        }

        Point screenPoint = e.getLocationOnScreen();

        SwingUtilities.convertPointFromScreen(screenPoint, windowLayer);

        int newLocationX = screenPoint.x + constPoint.x;
        int newLocationY = screenPoint.y + constPoint.y;

        Dimension tipSize = textComponent.getPreferredSize();
        if (newLocationX + tipSize.width > rootPane.getWidth()) {
            newLocationX = screenPoint.x - tipSize.width - constPoint2.x;
        }
        if (newLocationY + tipSize.height > rootPane.getHeight()) {
            newLocationY = screenPoint.y - tipSize.height - constPoint2.y;
        }
        this.setLocation(newLocationX, newLocationY);
        //textComponent.getPreferredSize()在html初始化计算的时候有问题，重算一次
        if (!curTipSize.equals(textComponent.getPreferredSize())) {
            this.setSize(textComponent.getPreferredSize().width, textComponent.getPreferredSize().height);
        }
    }

    private void setTipState(int state) {
        tipTimer.stop();//停止上一次的任务
        if (state == 0) {//进入组件，延迟显示
            tipTimerListener.state = 0;
            tipTimer.setInitialDelay(initTime);
            tipTimer.start();
        } else if (state == 1) {//鼠标移出，组件消失
            tipTimerListener.state = 1;
            PopTimingTip.this.setVisible(false);
        }
    }

    private class TipTimerListener implements ActionListener {
        int state;

        public void actionPerformed(ActionEvent e) {
            if (state == 0) {
                PopTimingTip.this.setVisible(true);
            }
        }
    }

    /**
     * 鼠标移除后及时清除侦听防止侦听器溢出
     */
    private void removeTipAndListener() {
        if (tipParent == null) {
            return;
        }
        tipParent.removeMouseListener(tipListener);
        tipParent.removeMouseMotionListener(tipListener);
        if (windowLayer != null) {
            windowLayer.remove(this);
        }
    }

    private class TipListener extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setTipState(0);
            followWithMouse(e);
        }

        /**
         * 鼠标移出对象时，移除对象的侦听和ToolTip
         */
        public void mouseExited(MouseEvent e) {
            setTipState(1);
            followWithMouse(e);
            removeTipAndListener();
        }

        //在组件上移动时触发
        public void mouseMoved(MouseEvent e) {
            setTipState(0);
            followWithMouse(e);
        }

        public void mouseClicked(MouseEvent e) {
            if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {//右键点击，tip消失
                setTipState(1);
                followWithMouse(e);
                removeTipAndListener();
            }
        }
    }

}
package org.javatribe.calculator.common;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadingGlassPane extends JPanel {

    private static final String ICON_PATH = System.getProperty("user.dir") + "/images/loading/loading.gif";

    public LoadingGlassPane() {
        setSize(300,300);
//        setLayout(new BorderLayout());
//        setBounds(Constant.FROM_X, Constant.FROM_Y, Constant.FROM_WIDTH, Constant.FROM_HEIGHT);
        setOpaque(false);
        add(new JLabel(new ImageIcon(ICON_PATH)));
        setName("LoadingGlassPane");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });
    }
}

package org.javatribe.calculator.common;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {
    /**
     * 通过组件名,从父级组件沿着递归找到此名字的组件
     *
     * @param c    父级组件
     * @param name 设置的组件名称
     * @return
     */
    public static Component searchComponentByName(Container c, String name) {//父级组件,设置的组件名称
        Component result = null;
        Component[] components = c.getComponents();
        if (null == result && null != components && components.length > 0) {
            for (Component component : components) {
                String name2 = component.getName();
                if (name2 != null && name2.equals(name)) {
                    result = component;
                    return result;
                } else if (null == result) {//递归调用所有下级组件列表
                    if (component instanceof Container)
                        result = searchComponentByName((Container) component, name);
                }
            }
        }
        return result;

    }

    public static void windowtoCenter(JFrame frame) {
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }

}

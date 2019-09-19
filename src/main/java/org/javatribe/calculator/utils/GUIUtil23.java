package org.javatribe.calculator.utils;

import javax.swing.*;

public class GUIUtil23 {
    public static final String MESSAGE_WARNING = " 警告";
    public static final String MESSAGE_INFORMATION = " 消息";
    public static final String MESSAGE_ERROR = " 错误";

    private GUIUtil23() {
        throw new Error("Don't let anyone instantiate this class.");
    }

    /***
     * Warning boxes
     *
     * @param mesg
     */
    public static void warningDialog(String mesg) {
        JOptionPane
                .showMessageDialog(
                        null,
                        "<html><font color=\"yellow\"  style=\"font-weight:bold;" +
                                "background-color:#666666\" >"
                                + mesg + "</font></html>", MESSAGE_WARNING,
                        JOptionPane.WARNING_MESSAGE);
    }

    /***
     * error
     *
     * @param mesg
     */
    public static void errorDialog(String mesg) {
        JOptionPane
                .showMessageDialog(
                        null,
                        "<html><font color=\"red\"  style=\"font-weight:bold;" +
                                "background-color:white\" >"
                                + mesg + "</font></html>", MESSAGE_ERROR,
                        JOptionPane.ERROR_MESSAGE);
    }

    /***
     * information
     *
     * @param mesg
     */
    public static void infoDialog(String mesg) {
        JOptionPane.showMessageDialog(null,
                "<html><font color=\"green\"  style=\"font-weight:bold;\" >" + mesg
                        + "</font></html>", MESSAGE_INFORMATION,
                JOptionPane.INFORMATION_MESSAGE);
    }
}

package org.javatribe.calculator.page;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.javatribe.calculator.common.Constant;
import org.javatribe.calculator.module.DogAllInfo;
import org.javatribe.calculator.module.DogInfoData;
import org.javatribe.calculator.utils.DoRun;
import org.javatribe.calculator.utils.GUIUtil23;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class Details {
    private Integer dogId;
    private DogAllInfo dogAllInfo = new DogAllInfo();
    private JFrame loginframe;

    //    public JFrame frame;
    public JDialog frame;
    private JTextPane textPane;
    private JLabel lblNewLabel;
    private JLabel label;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JLabel label_3;
    private JLabel label_4;
    private JTextField textField_5;
    private JLabel label_5;
    private JLabel lblNewLabel_1;
    private JLabel label_6;
    private JLabel label_7;
    private JLabel label_8;
    private JTextField textField_6;
    private JTextField textField_7;
    private JLabel label_9;
    private JLabel label_10;
    private JTextField textField_8;
    private JLabel label_11;
    private JTextField textField_9;
    private JTextField textField_10;
    private JLabel label_12;
    private JTextField textField_11;
    private JLabel label_13;
    private JTextPane textPane_1;
    private JLabel label_14;
    private JTextField textField_12;
    private JLabel label_15;
    private JTextField textField_13;
    private JTextPane textPane_2;
    private JLabel label_16;
    private JTextField textField_14;
    private JLabel label_18;
    private JTextField textField_16;
    private JLabel label_19;
    private JTextField textField_17;
    private JLabel label_20;
    private JTextField textField_18;
    private JLabel label_22;
    private JTextField textField_20;
    private JLabel label_23;
    private JTextField textField_21;

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Details window = new Details("aa", 1047, null);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

    ActionListener bt1_ls1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            dogAllInfo = new DogAllInfo();
            frame.setVisible(false);
            loginframe.setVisible(true);
            frame.dispose();
        }
    };

    public void selectDogInfo(String token) {
        new Thread(() -> {
            DoRun doRun = new DoRun((JPanel) frame.getRootPane().getContentPane(), "加载中，请稍后...");
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet(Constant.SERVER_URL + "/biz/dogCard/selectDogCardInfo?dogId=" + dogId);
                httpGet.addHeader(new BasicHeader("dog-cookie", token));
                client = HttpClients.createDefault();
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = JSONObject.fromObject(result);
                DogInfoData dogInfoData = (DogInfoData) JSONObject.toBean(jsonObject, DogInfoData.class);
                dogAllInfo = dogInfoData.getResult();
                initialize();
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
                GUIUtil23.errorDialog("网络错误！");
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                    doRun.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    public void up(Integer id, JFrame loginjf) {
        frame.setVisible(true);
        dogId = id;
        loginframe = loginjf;

        JButton btnNewButton = new JButton("\u8FD4\u56DE");
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 14));
        btnNewButton.setBounds(738, 12, 74, 26);
        btnNewButton.setBackground(new Color(102, 205, 170));
        btnNewButton.addActionListener(bt1_ls1);

        frame.getContentPane().add(btnNewButton);
        selectDogInfo(Constant.token);
    }

    /**
     * Create the application.
     */
    public Details(Integer dogId, String dogName, JFrame fromJF) {
        frame = new JDialog();
        frame.setVisible(true);
        String title = "详情";
        if (dogName != null && dogName.trim().length() > 0) {
            title += "      犬只【" + dogName + "】";
        }
        frame.setTitle(title);
        up(dogId, fromJF);
        frame.setResizable(false);
        frame.setAutoRequestFocus(false);
        frame.setBounds(100, 100, 830, 781);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image image = Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/images/szlogo.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        SwingUtils.windowtoCenter(frame);
        initialize();
    }


    /**
     * Create the application.
     */
//    public Details() {
//        frame = new JFrame("详情");
//        frame.setResizable(false);
//        frame.setVisible(false);
//        frame.setAutoRequestFocus(false);
//        frame.setBounds(100, 100, 830, 781);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Image image = Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/images/szlogo.png"));
//        frame.setIconImage(image);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        SwingUtils.windowtoCenter(frame);
//        initialize();
//    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        textPane = new JTextPane();
        textPane.setBounds(0, 46, 812, 26);
        textPane.setEditable(false);
        textPane.setForeground(new Color(255, 255, 255));
        textPane.setText(" \u72AC\u53EA\u4FE1\u606F");
        textPane.setBackground(new Color(102, 205, 170));
        textPane.setFont(new Font("宋体", Font.BOLD, 16));
        textPane.setToolTipText("");

        lblNewLabel = new JLabel("\u72AC\u540D\uFF1A");
        lblNewLabel.setBounds(22, 90, 48, 16);
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));

        label = new JLabel("\u54C1\u79CD\uFF1A");
        label.setBounds(406, 90, 48, 16);
        label.setFont(new Font("宋体", Font.PLAIN, 15));

        if (dogId != null) {
            textField = new JTextField();
            textField.setText(dogAllInfo.getDogInfo().getDogName());
            textField.setBounds(95, 85, 250, 21);
            textField.setFont(new Font("宋体", Font.PLAIN, 14));
            textField.setEditable(false);
            textField.setColumns(10);
            frame.getContentPane().add(textField);

            textField_1 = new JTextField();
            textField_1.setText(dogAllInfo.getDogInfo().getBreed());
            textField_1.setBounds(480, 85, 250, 21);
            textField_1.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_1.setEditable(false);
            textField_1.setColumns(10);
            frame.getContentPane().add(textField_1);
        }


        if (dogId != null) {
            textField_2 = new JTextField();
            textField_2.setBounds(95, 115, 250, 21);
            Integer val = dogAllInfo.getDogInfo().getDogGender();
            String gender = "";
            if (val != null) {
                if (0 == val) {
                    gender = "雄";
                } else if (1 == val) {
                    gender = "雌";
                }
            }
            textField_2.setText(gender);
            textField_2.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_2.setEditable(false);
            textField_2.setColumns(10);
            frame.getContentPane().add(textField_2);
        }

        JLabel label_1 = new JLabel("\u6027\u522B\uFF1A");
        label_1.setBounds(22, 120, 48, 16);
        label_1.setFont(new Font("宋体", Font.PLAIN, 15));
        label_1.setBackground(Color.WHITE);

        JLabel label_2 = new JLabel("\u72AC\u9F84\uFF1A");
        label_2.setBounds(406, 120, 48, 16);
        label_2.setFont(new Font("宋体", Font.PLAIN, 15));

        if (dogId != null) {
            textField_3 = new JTextField();
            textField_3.setBounds(480, 115, 250, 21);
            Double dogAge = dogAllInfo.getDogInfo().getDogAge();
            String age = "";
            if (dogAge != null) {
                age = dogAge + "岁";
            }
            textField_3.setText(age);
            textField_3.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_3.setEditable(false);
            textField_3.setColumns(10);

            textField_4 = new JTextField();
            textField_4.setBounds(95, 145, 250, 21);
            textField_4.setText(dogAllInfo.getDogInfo().getColor());
            textField_4.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_4.setEditable(false);
            textField_4.setColumns(10);
            frame.getContentPane().add(textField_4);
        }

        label_3 = new JLabel("\u6BDB\u8272\uFF1A");
        label_3.setBounds(22, 150, 78, 16);
        label_3.setFont(new Font("宋体", Font.PLAIN, 15));
        label_3.setBackground(Color.WHITE);

        label_5 = new JLabel("\u7167\u7247\uFF1A");
        label_5.setBounds(22, 180, 48, 16);
        label_5.setFont(new Font("宋体", Font.PLAIN, 15));
        label_5.setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);

        frame.getContentPane().add(textPane);
        frame.getContentPane().add(lblNewLabel);
        frame.getContentPane().add(label);

        frame.getContentPane().add(label_1);
        frame.getContentPane().add(label_2);
        frame.getContentPane().add(label_3);
        frame.getContentPane().add(label_5);
        if (dogId != null) {
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(new URL(Constant.SERVER_URL + dogAllInfo.getDogInfo().getzUrl()));
                icon.setImage(icon.getImage().getScaledInstance(108, 89, Image.SCALE_DEFAULT));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            lblNewLabel_1 = new JLabel(icon);
            lblNewLabel_1.setBounds(95, 175, 108, 89);
            frame.getContentPane().add(lblNewLabel_1);

            ImageIcon icon1 = null;
            try {
                icon1 = new ImageIcon(new URL(Constant.SERVER_URL + dogAllInfo.getDogOwners().getzUrl()));
                icon1.setImage(icon1.getImage().getScaledInstance(108, 89, Image.SCALE_DEFAULT));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            label_6 = new JLabel(icon1);
            label_6.setBounds(95, 439, 108, 89);
            frame.getContentPane().add(label_6);
        }

        label_7 = new JLabel("\u7167\u7247\uFF1A");
        label_7.setFont(new Font("宋体", Font.PLAIN, 15));
        label_7.setBackground(Color.WHITE);
        label_7.setBounds(22, 444, 48, 16);
        frame.getContentPane().add(label_7);

        label_8 = new JLabel("\u6027\u522B\uFF1A");
        label_8.setFont(new Font("宋体", Font.PLAIN, 15));
        label_8.setBackground(Color.WHITE);
        label_8.setBounds(22, 384, 78, 16);
        frame.getContentPane().add(label_8);

        if (dogId != null) {
            textField_6 = new JTextField();
            Integer val2 = dogAllInfo.getDogOwners().getSex();
            String sex = "";
            if (val2 != null) {
                if (0 == val2) {
                    sex = "男";
                } else if (1 == val2) {
                    sex = "女";
                }
            }
            textField_6.setText(sex);
            textField_6.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_6.setEditable(false);
            textField_6.setColumns(10);
            textField_6.setBounds(95, 379, 250, 21);
            frame.getContentPane().add(textField_6);

            textField_7 = new JTextField();
            textField_7.setText(dogAllInfo.getDogOwners().getDistrictName());
            textField_7.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_7.setEditable(false);
            textField_7.setColumns(10);
            textField_7.setBounds(480, 379, 250, 21);
            frame.getContentPane().add(textField_7);
        }


        label_9 = new JLabel("\u73B0\u4F4F\u533A\u53BF\uFF1A");
        label_9.setFont(new Font("宋体", Font.PLAIN, 15));
        label_9.setBounds(406, 384, 75, 16);
        frame.getContentPane().add(label_9);

        label_10 = new JLabel("\u8BC1\u4EF6\u7C7B\u578B\uFF1A");
        label_10.setFont(new Font("宋体", Font.PLAIN, 15));
        label_10.setBackground(Color.WHITE);
        label_10.setBounds(22, 354, 78, 16);
        frame.getContentPane().add(label_10);

        if (dogId != null) {
            textField_8 = new JTextField();
            Integer cardType = dogAllInfo.getDogOwners().getCardType();
            String ct = "";
            if (cardType != null) {
                if (cardType == 0) {
                    ct = "身份证";
                } else if (cardType == 1) {
                    ct = "房产证";
                } else if (cardType == 2) {
                    ct = "营业执照";
                } else if (cardType == 3) {
                    ct = "其他";
                }
            }
            textField_8.setText(ct);
            textField_8.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_8.setEditable(false);
            textField_8.setColumns(10);
            textField_8.setBounds(95, 349, 250, 21);
            frame.getContentPane().add(textField_8);

            label_11 = new JLabel("\u8BC1\u4EF6\u53F7\u7801\uFF1A");
            label_11.setFont(new Font("宋体", Font.PLAIN, 15));
            label_11.setBounds(406, 354, 78, 16);
            frame.getContentPane().add(label_11);

            textField_9 = new JTextField();
            textField_9.setText(dogAllInfo.getDogOwners().getCardNum());
            textField_9.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_9.setEditable(false);
            textField_9.setColumns(10);
            textField_9.setBounds(480, 349, 250, 21);
            frame.getContentPane().add(textField_9);

            textField_10 = new JTextField();
            textField_10.setText(dogAllInfo.getDogOwners().getDogOwnerPhone());
            textField_10.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_10.setEditable(false);
            textField_10.setColumns(10);
            textField_10.setBounds(480, 319, 250, 21);
            frame.getContentPane().add(textField_10);

            label_12 = new JLabel("\u7535\u8BDD\u53F7\u7801\uFF1A");
            label_12.setFont(new Font("宋体", Font.PLAIN, 15));
            label_12.setBounds(406, 324, 78, 16);
            frame.getContentPane().add(label_12);

            textField_11 = new JTextField();
            textField_11.setText(dogAllInfo.getDogOwners().getDogOwnerName());
            textField_11.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_11.setEditable(false);
            textField_11.setColumns(10);
            textField_11.setBounds(95, 319, 250, 21);
            frame.getContentPane().add(textField_11);
        }


        label_13 = new JLabel("\u72AC\u4E3B\u59D3\u540D\uFF1A");
        label_13.setFont(new Font("宋体", Font.PLAIN, 15));
        label_13.setBackground(Color.WHITE);
        label_13.setBounds(22, 324, 78, 16);
        frame.getContentPane().add(label_13);

        textPane_1 = new JTextPane();
        textPane_1.setToolTipText("");
        textPane_1.setText(" \u72AC\u4E3B\u4FE1\u606F");
        textPane_1.setForeground(Color.WHITE);
        textPane_1.setFont(new Font("宋体", Font.BOLD, 16));
        textPane_1.setEditable(false);
        textPane_1.setBackground(new Color(102, 205, 170));
        textPane_1.setBounds(0, 280, 812, 26);
        frame.getContentPane().add(textPane_1);

        label_14 = new JLabel("\u8BE6\u7EC6\u5730\u5740\uFF1A");
        label_14.setFont(new Font("宋体", Font.PLAIN, 15));
        label_14.setBackground(Color.WHITE);
        label_14.setBounds(22, 414, 178, 16);
        frame.getContentPane().add(label_14);

        if (dogId != null) {
            textField_12 = new JTextField();
            textField_12.setText(dogAllInfo.getDogOwners().getAddress());
            textField_12.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_12.setEditable(false);
            textField_12.setColumns(10);
            textField_12.setBounds(95, 409, 635, 21);
            frame.getContentPane().add(textField_12);
        }
        textPane_2 = new JTextPane();
        textPane_2.setToolTipText("");
        textPane_2.setText(" \u8BC1\u4EF6\u4FE1\u606F");
        textPane_2.setForeground(Color.WHITE);
        textPane_2.setFont(new Font("宋体", Font.BOLD, 16));
        textPane_2.setEditable(false);
        textPane_2.setBackground(new Color(102, 205, 170));
        textPane_2.setBounds(1, 550, 812, 26);
        frame.getContentPane().add(textPane_2);

        label_16 = new JLabel("\u514D\u75AB\u8BC1\u53F7\uFF1A");
        label_16.setFont(new Font("宋体", Font.PLAIN, 15));
        label_16.setBackground(Color.WHITE);
        label_16.setBounds(23, 594, 78, 16);
        frame.getContentPane().add(label_16);

        if (dogId != null) {
            textField_14 = new JTextField();
            textField_14.setText(dogAllInfo.getImmuneCard().getImmuneCardNum());
            textField_14.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_14.setEditable(false);
            textField_14.setColumns(10);
            textField_14.setBounds(96, 589, 250, 21);
            frame.getContentPane().add(textField_14);
        }
        label_18 = new JLabel("\u7B7E\u53D1\u65E5\u671F\uFF1A");
        label_18.setFont(new Font("宋体", Font.PLAIN, 15));
        label_18.setBackground(Color.WHITE);
        label_18.setBounds(23, 624, 78, 16);
        frame.getContentPane().add(label_18);

        if (dogId != null) {
            textField_16 = new JTextField();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strdate = format.format(dogAllInfo.getImmuneCard().getLssueTime());
            textField_16.setText(strdate);
            textField_16.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_16.setEditable(false);
            textField_16.setColumns(10);
            textField_16.setBounds(96, 619, 250, 21);
            frame.getContentPane().add(textField_16);
        }
        label_19 = new JLabel("\u7B7E\u53D1\u673A\u6784\uFF1A");
        label_19.setFont(new Font("宋体", Font.PLAIN, 15));
        label_19.setBounds(407, 624, 78, 16);
        frame.getContentPane().add(label_19);
        if (dogId != null) {
            textField_17 = new JTextField();
            textField_17.setText(dogAllInfo.getImmuneCard().getOrgName());
            textField_17.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_17.setEditable(false);
            textField_17.setColumns(10);
            textField_17.setBounds(481, 619, 250, 21);
            frame.getContentPane().add(textField_17);
        }
        label_20 = new JLabel("\u72AC\u724C\u53F7\uFF1A");
        label_20.setFont(new Font("宋体", Font.PLAIN, 15));
        label_20.setBackground(Color.WHITE);
        label_20.setBounds(23, 654, 78, 16);
        frame.getContentPane().add(label_20);

        if (dogId != null) {
            textField_18 = new JTextField();
            textField_18.setText(dogAllInfo.getDogCard().getDevicenum());
            textField_18.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_18.setEditable(false);
            textField_18.setColumns(10);
            textField_18.setBounds(96, 649, 250, 21);
            frame.getContentPane().add(textField_18);
        }
        label_22 = new JLabel("\u7B7E\u53D1\u65E5\u671F\uFF1A");
        label_22.setFont(new Font("宋体", Font.PLAIN, 15));
        label_22.setBackground(Color.WHITE);
        label_22.setBounds(23, 684, 78, 16);
        frame.getContentPane().add(label_22);

        if (dogId != null) {
            textField_20 = new JTextField();
            SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
            String strdate1 = formatt.format(dogAllInfo.getDogCard().getLssueTime());
            textField_20.setText(strdate1);
            textField_20.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_20.setEditable(false);
            textField_20.setColumns(10);
            textField_20.setBounds(96, 679, 250, 21);
            frame.getContentPane().add(textField_20);
        }
        label_23 = new JLabel("\u6709\u6548\u671F\u81F3\uFF1A");
        label_23.setFont(new Font("宋体", Font.PLAIN, 15));
        label_23.setBounds(407, 684, 75, 16);
        frame.getContentPane().add(label_23);

        if (dogId != null) {
            textField_21 = new JTextField();
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            String strdate2 = format2.format(dogAllInfo.getDogCard().getEndTime());
            textField_21.setText(strdate2);
            textField_21.setFont(new Font("宋体", Font.PLAIN, 14));
            textField_21.setEditable(false);
            textField_21.setColumns(10);
            textField_21.setBounds(481, 682, 250, 21);
            frame.getContentPane().add(textField_21);
        }
    }

    private static class __Tmp {
        private static void __tmp() {
            JPanel __wbp_panel = new JPanel();
        }
    }
}

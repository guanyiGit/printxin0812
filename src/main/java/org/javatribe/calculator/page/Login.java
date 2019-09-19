package org.javatribe.calculator.page;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.javatribe.calculator.common.Constant;
import org.javatribe.calculator.common.SwingUtils;
import org.javatribe.calculator.module.Data;
import org.javatribe.calculator.module.TRoles;
import org.javatribe.calculator.module.UserInfo;
import org.javatribe.calculator.utils.GUIUtil23;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Login {
    private static String token = null;
    UserInfo userInfo = null;
    public static CardList cardList = new CardList();
//    public static Details details = new Details();

    public JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login window = new Login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 处理点击事件
     * 1.登陆按钮点击事件，判断账号密码是否正确，若正确，弹出监测信息界面
     * 否则，无响应（暂时无响应）
     * ：后可在登陆界面添加一个logLabel提示用户是否用户密码正确
     * 2.退出按钮，直接退出程序
     */
    //登陆点击事件
    ActionListener bt1_ls = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            String admin = textField.getText();
            char[] password = passwordField.getPassword();
            String str = String.valueOf(password); //将char数组转化为string类型
            admin = admin.trim();
            if (admin == null || admin.equals("")) {
                JOptionPane.showMessageDialog(frame, "用户名不能为空！", "登陆提示", JOptionPane.WARNING_MESSAGE);
            } else if (str == null || str.equals("")) {
                JOptionPane.showMessageDialog(frame, "密码不能为空！", "登陆提示", JOptionPane.WARNING_MESSAGE);
            } else if (admin != null && admin != "" && str != null && str != "") {
                CloseableHttpClient client = null;
                CloseableHttpResponse response = null;
                try {
                    // 创建一个提交数据的容器
                    List<BasicNameValuePair> parames = new ArrayList<>();
                    parames.add(new BasicNameValuePair("username", admin));
                    parames.add(new BasicNameValuePair("password", getMD5(str)));

                    HttpPost httpPost = new HttpPost(Constant.SERVER_URL + "/biz/login");
                    httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));

                    client = HttpClients.createDefault();
                    response = client.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    Data data = (Data) JSONObject.toBean(jsonObject, Data.class);
                    if (data.getStatus() == 200 && data.getData() != null) {
                        userInfo = data.getData();
                        Integer size = userInfo.gettRoles().size();
                        if (size <= 0) {
                            JOptionPane.showMessageDialog(frame, "请用制卡方账号登陆！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                        } else if (size > 0) {
                            List<TRoles> list = userInfo.gettRoles();
                            JSONObject obj = JSONObject.fromObject(list.get(0));
                            TRoles roles = (TRoles) JSONObject.toBean(obj, TRoles.class);
                            Integer roleid = roles.getRoleId();
                            if (roleid == 13) {
//									Header[] headers = response.getHeaders("token");
//									token = headers[0].getValue();
//									frame.setVisible(false);
////									Details details = new Details(token,10102091,frame);
////									details.frame.setVisible(true);
//									CardList app = new CardList(token);
//									app.card_frame.setVisible(true);

                                Header[] headers = response.getHeaders("token");
                                token = headers[0].getValue();
                                Constant.token = token;
                                frame.setVisible(false);
                                cardList.up();
                            } else {
                                JOptionPane.showMessageDialog(frame, "请用制卡方账号登陆！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "请用制卡方账号登陆！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (data.getStatus() == 511) {
                        JOptionPane.showMessageDialog(frame, "账号不存在！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                    } else if (data.getStatus() == 512) {
                        JOptionPane.showMessageDialog(frame, "账号未启用！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                    } else if (data.getStatus() == 513) {
                        JOptionPane.showMessageDialog(frame, "账号或密码错误！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "网络信号差！", "登陆提示", JOptionPane.WARNING_MESSAGE);
                    }
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    /**
     * Create the application.
     */
    public Login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("制卡客户端登录");
        frame.setResizable(false);
        frame.setAutoRequestFocus(false);
        frame.setAlwaysOnTop(true);
        frame.setBounds(100, 100, 830, 570);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        Image image = Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/images/szlogo.png"));
        frame.setIconImage(image);
        SwingUtils.windowtoCenter(frame);

        textField = new JTextField();
        textField.setFont(new Font("宋体", Font.PLAIN, 14));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setToolTipText("\u8BF7\u8F93\u5165\u7528\u6237\u540D");
        textField.setBounds(300, 208, 230, 40);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("宋体", Font.PLAIN, 14));
        passwordField.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField.setToolTipText("\u8BF7\u8F93\u5165\u5BC6\u7801");
        passwordField.setBounds(300, 259, 230, 40);
        frame.getContentPane().add(passwordField);

        btnNewButton = new JButton("\u767B\u5F55");
        btnNewButton.setFont(new Font("宋体", Font.BOLD, 18));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setBounds(300, 335, 230, 40);
        frame.getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(bt1_ls);
        //给登陆界面添加背景图片
        ImageIcon bgim = new ImageIcon(Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/images/login.jpg")));
        JLabel lbBg = new JLabel();
        lbBg.setIcon(bgim);
        lbBg.setBounds(0, 0, 830, 570);
        frame.getContentPane().add(lbBg);
    }


    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package org.javatribe.calculator.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.javatribe.calculator.common.*;
import org.javatribe.calculator.domain.TabCellEditor;
import org.javatribe.calculator.domain.TabCheckBoxRenderer;
import org.javatribe.calculator.service.ConvertServer;
import org.javatribe.calculator.utils.DoRun;
import org.javatribe.calculator.utils.GUIUtil23;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.javatribe.calculator.common.Constant.FROM_WIDTH;

public class CardList {

    private int START_X = 10;
    private int START_Y = 3;
    int cellHeight = 40;


    private enum LabType {
        FINISHED, UN_FINISHED;
        private static Map<LabType, Object[]> HANDERS;

        static {
            HANDERS = new HashMap<>();
            HANDERS.put(FINISHED, new String[]{"犬牌号", "犬只名称", "犬只品种", "犬主姓名", "电话号码", "办证时间", "办证人", "办证机构", "制卡时间",
                    "制卡次数", "单个打印", "操作"});
            HANDERS.put(UN_FINISHED,
                    new Object[]{Boolean.FALSE, "犬牌号", "犬只名称", "犬只品种", "犬主姓名", "电话号码", "办证时间", "办证人", "办证机构", "操作"});
        }

        private LabType() {
        }

        public Object[] getHanders(LabType labType) {
            return HANDERS.get(labType);
        }

    }

    private LabType labType = LabType.UN_FINISHED;
    public JFrame card_frame;
    private JTable jTab;
    private final int pageSize = 20;
    private List<JSONObject> table_data;
    public static final String SELECT_KEY = "checkd";
    //    private final int tabHeight = 730;
    private String searchKey;
    private int pageNum = 1;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public List<JSONObject> select_options = new ArrayList<>();

    private void initLabs() {
        try {
            Map<String, String> headers = new HashMap<>();
            if (Constant.token != null) {
                headers.put("Cookie", "dog-cookie=" + Constant.token);
            }
            Map<String, String> paramsObj = new HashMap<>();
            HttpResult result = HttpClientUtil.executeHttpParams(Constant.SERVER_URL + "/biz/dogCard/selectUserIds",
                    "GET", headers, paramsObj);
            System.out.println(result);
            if (result != null && result.getStatusCode() == 200) {
                JSONObject json = JSON.parseObject(result.getContent());
                if (json != null && json.getJSONArray("data") != null) {
                    select_options = json.getJSONArray("data").stream()
                            .map(x -> {
                                JSONObject o = (JSONObject) x;
                                o.put(SELECT_KEY, false);
                                return o;
                            }).collect(Collectors.toList());
                }
            } else {
                GUIUtil23.warningDialog("网络繁忙！");
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            GUIUtil23.warningDialog("网络错误！");
            e1.printStackTrace();
        }
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CardList window = new CardList();
                    window.up();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CardList() {
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void up() {
        card_frame.setVisible(true);
        initLabs();
        generateTable(1, null, null);
    }


    /**
     * @param pageNum
     * @param query
     * @param userId
     * @return
     */
    public JTable generateTable(int pageNum, String query, Integer userId) {
        jTab = new JTable();
        jTab.setName("jTab");

        Optional<Component> component = Arrays.stream(card_frame.getContentPane().getComponents())
                .filter(x -> "lab_pal".equals(x.getName())).findFirst();
        if (!component.isPresent()) {
            return jTab;
        }
        component.ifPresent(x -> {
            Container pane = card_frame.getContentPane();
            pane.remove(x);
            pane.add(x);
        });
        if (Constant.token == null) {
            return jTab;
        }
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int c) {
                Object value = getValueAt(0, c);
                if (value != null)
                    return value.getClass();
                else
                    return super.getClass();
            }
        };

        JTableHeader th = jTab.getTableHeader();
        th.setBackground(new Color(102, 205, 170));
        th.setPreferredSize(new Dimension(th.getWidth(), 30));
        tableModel.setColumnIdentifiers(labType.getHanders(labType));
        jTab.setModel(tableModel);

        jTab.setRowHeight(cellHeight);


        Component scrollPane = SwingUtils.searchComponentByName(card_frame, "scrollPane");
        if (scrollPane != null) {
            JScrollPane jsp = (JScrollPane) scrollPane;
            lab_pal(jsp);
            jsp.setViewportView(jTab);
        }

        new Thread(() -> {
            TableColumn lastCell;
            JPanel jpl = (JPanel) card_frame.getRootPane().getContentPane();

            DoRun doRun = new DoRun(jpl, "数据加载中，请稍后。。。");

            try {
                Object[][] rowDatas = generateData(labType, pageNum, query, userId);

                if (rowDatas != null)
                    Stream.of(rowDatas).forEach(x -> {
                        if (x.length != labType.getHanders(labType).length) {
                            System.out.println("参数错误");
                        } else {
                            try {
                                JTable jTab = (JTable) SwingUtils.searchComponentByName(jpl, "jTab");
                                ((DefaultTableModel) jTab.getModel()).addRow(x);
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    });


                if (LabType.UN_FINISHED.equals(labType)) {
                    final TabCheckBoxRenderer check = new TabCheckBoxRenderer();
                    jTab.getColumnModel().getColumn(0).setHeaderRenderer(check);
                    jTab.getColumnModel().getColumn(0).setMaxWidth(40);

                    jTab.getTableHeader().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (jTab.getColumnModel().getColumnIndexAtX(e.getX()) == 0) {// 点击hander首个
                                JCheckBox Checkbox = (JCheckBox) check;
                                boolean b = !check.isSelected();
                                check.setSelected(b);
                                jTab.getTableHeader().repaint();
                                for (int i = 0; i < jTab.getRowCount(); i++) {
                                    jTab.getModel().setValueAt(b, i, 0);// 切换状态
                                }
                            }
                        }
                    });
                    lastCell = jTab.getColumnModel().getColumn(LabType.UN_FINISHED.getHanders(LabType.UN_FINISHED).length - 1);
                } else {
                    lastCell = jTab.getColumnModel().getColumn(LabType.FINISHED.getHanders(LabType.FINISHED).length - 1);
                    TableColumn printCell = jTab.getColumnModel().getColumn(LabType.FINISHED.getHanders(LabType.FINISHED).length - 2);
                    JButton jButton = new JButton("打印");
                    jButton.setEnabled(true);
                    printCell.setMaxWidth(100);
                    printCell.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> jButton);
                    printCell.setCellEditor(new TabCellEditor(this, jButton, card_frame, table_data));
                    //            jTab.getColumnModel().getColumn(0).setMaxWidth(40);
                    //            jTab.getColumnModel().getColumn(9).setMaxWidth(40);
                }
                JButton jButton = new JButton("查详情");
                jButton.setEnabled(true);
                jButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(e);
                    }
                });

                TableCellRenderer btnRenderer = new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                   boolean hasFocus, int row, int column) {
                        return jButton;
                    }
                };

                lastCell.setMaxWidth(100);
                lastCell.setCellRenderer(btnRenderer);
                lastCell.setCellEditor(new TabCellEditor(this, jButton, card_frame, table_data));
            } finally {
                doRun.stop(card_frame);
                card_frame.validate();
                card_frame.repaint();
            }
        }).start();

        card_frame.validate();
        card_frame.repaint();

        return jTab;
    }


    private void tab_pal(JScrollPane scrollPane) {
        JPanel table_pal = new JPanel();
        table_pal.setName("table_pal");
//        table_pal.setBounds(10, 76, FROM_WIDTH - 30, Constant.FROM_HEIGHT);
        table_pal.setBounds(10, 76, FROM_WIDTH - 30, Constant.FROM_HEIGHT - 155);
        card_frame.getContentPane().add(table_pal);
        table_pal.setLayout(null);

        table_pal.add(scrollPane);

        if (Constant.token != null)
            generateTable(1, null, null);
    }

    private Object[][] generateData(LabType labType, int pageNum, String query, Integer userId) {

        try {
            if (searchKey != null && searchKey.trim().length() > 0) {
                query = searchKey;
            }
            Optional<JSONObject> first = select_options.stream().filter(x -> x.getBoolean(SELECT_KEY)).findFirst();
            if (first.isPresent()) {
                userId = first.get().getInteger("userId");
            }
        } catch (Exception e) {
        }
        Map<String, String> headers = new HashMap<>();
        if (Constant.token != null) {
            headers.put("Cookie", "dog-cookie=" + Constant.token);
        }

        Map<String, String> paramsObj = new HashMap<>();

        paramsObj.put("num", String.valueOf(pageNum));
        paramsObj.put("size", String.valueOf(pageSize));
        if (query != null && query.trim().length() > 0) {
            paramsObj.put("input", query);
        }
        if (userId != null && userId > 0) {
            paramsObj.put("userId", String.valueOf(userId));
        }
        int cardmakingStatus = 1;
        if (LabType.UN_FINISHED.equals(labType)) {
            cardmakingStatus = 0;
        }
        paramsObj.put("cardmakingStatus", String.valueOf(cardmakingStatus));

        try {
            int total = 0;
            int cur_page = 1;
            try {
                HttpResult result = HttpClientUtil.executeHttpParams(
                        Constant.SERVER_URL + "/biz/dogCard/selectCardMakingInfo", "GET", headers, paramsObj);
                System.out.println(result);
                if (result != null && result.getStatusCode() == 200) {
                    JSONObject json = JSON.parseObject(result.getContent());
                    if (json != null && json.containsKey("page")) {
                        cur_page = json.getInteger("page");
                        total = json.getInteger("total");
                        System.out.println("total 》》》》》》:" + total);
                        JSONArray list = json.getJSONArray("rows");
                        list.stream().forEach(System.out::println);
                        if (list != null) {
                            List<Object[]> data_list = list.stream().map(x -> {
                                JSONObject obj = (JSONObject) x;
                                Object lssueTime = obj.getDate("lssueTime");
                                Object cardmakingTime = obj.getDate("cardmakingTime");
                                try {
                                    lssueTime = sdf.format(obj.getDate("lssueTime"));
                                    cardmakingTime = sdf.format(obj.getDate("cardmakingTime"));
                                } catch (Exception e) {
                                }
                                if (LabType.UN_FINISHED.equals(labType)) {
                                    return new Object[]{Boolean.FALSE, obj.getString("dogCardNum"), obj.getString("dogName"),
                                            obj.getString("dogVarieties"), obj.getString("dogOwnerName"),
                                            obj.getString("dogOwnerPhone"), lssueTime,
                                            obj.getString("lssuerName"), obj.getString("lssueOrgName"), null};
                                } else {
                                    return new Object[]{obj.getString("dogCardNum"), obj.getString("dogName"),
                                            obj.getString("dogVarieties"), obj.getString("dogOwnerName"),
                                            obj.getString("dogOwnerPhone"), lssueTime,
                                            obj.getString("lssuerName"), obj.getString("lssueOrgName"),
                                            cardmakingTime, obj.getString("makeNum"), null, null};
                                }
                            }).collect(Collectors.toList());

                            if (data_list != null) {
                                table_data = list.stream().map(x -> (JSONObject) x)
                                        .collect(Collectors.toCollection(ArrayList::new));

                                Object[][] rowDatas = data_list.toArray(new Object[data_list.size()][10]);

                                return rowDatas;
                            }
                        }
                    }
                } else {
                    GUIUtil23.warningDialog("网络繁忙！");
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                GUIUtil23.warningDialog("网络错误！");
            } finally {
                // page
                JPanel page_pal = page_pal(total);
                JPanel page_ctl = page_ctl(cur_page, total);
                page_pal.add(page_ctl);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JScrollPane scrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("scrollPane");
//        scrollPane.setBounds(0, 0, Constant.FROM_WIDTH - 30, cellHeight * 14);
        scrollPane.setBounds(0, 0, Constant.FROM_WIDTH - 30, Constant.FROM_HEIGHT - 155);
        return scrollPane;
    }

    private JPanel page_pal(int totalCount) {
        String page_pal_name = "page_pal";
        JPanel page_pal = new JPanel();
        page_pal.setName(page_pal_name);
        Optional<Component> old_page_pal = Arrays.stream(card_frame.getContentPane().getComponents()).filter(x -> page_pal_name.equals(x.getName())).findFirst();
        if (old_page_pal.isPresent()) {
            card_frame.getContentPane().remove(old_page_pal.get());
        }

//        page_pal.setBounds(10, FROM_WIDTH + 80, FROM_WIDTH - 30, 31);
        page_pal.setBounds(10, Constant.FROM_HEIGHT - 70, Constant.FROM_WIDTH - 30, 31);
        card_frame.getContentPane().add(page_pal);
        page_pal.setLayout(null);

        JLabel page_show_info = new JLabel("每页显示             条 共                条");
        page_show_info.setBounds(1, 0, 240, 32);
        page_pal.add(page_show_info);

        JSpinner page_size = new JSpinner();
        page_size.setEnabled(false);
        page_size.setModel(new SpinnerNumberModel(pageSize, pageSize, pageSize * 2, 5));
        page_size.setBounds(51, 6, 38, 22);
        page_pal.add(page_size);

        JTextField page_total = new JTextField();
        page_total.setEnabled(false);
        page_total.setText(String.valueOf(totalCount));
        page_total.setBounds(122, 6, 45, 22);
        page_pal.add(page_total);
        page_total.setColumns(10);

        return page_pal;
    }

    private JPanel page_ctl(int cur_page, int total) {
        int icon_width = 49;
        int num_width = 80;

        int icon_total = 3;
//        int total_page = (int) Math.ceil(total / pageSize);
        int total_page = (total + pageSize - 1) / pageSize;

        JPanel page_ctl = new JPanel();
        page_ctl.setBounds(FROM_WIDTH - (num_width * 3 + icon_width * 4 + 6) - 31, 0, num_width * 3 + icon_width * 4 + 6, 31);
        page_ctl.setLayout(null);
        page_ctl.setName("page_ctl");

        JButton first = new JButton("<<");
        first.setBounds(0, 0, icon_width, 31);
        page_ctl.add(first);

        JButton prev = new JButton("<");
        prev.setBounds(icon_width, 0, icon_width, 31);
        page_ctl.add(prev);

        if (cur_page > 1) {
            first.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    generateTable(1, null, null);
                }
            });
            prev.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    generateTable(cur_page - 1, null, null);
                }
            });
        } else {
            first.setEnabled(false);
            prev.setEnabled(false);
        }

        int btn_start = icon_width * 2 + 3;

        for (int i = (cur_page - 2 >= 1 ? cur_page - 1 : 1); i <= (cur_page - 2 >= 1 ? cur_page - 1 : 1) + 2; i++) {
            JButton page = new JButton(String.valueOf(i));
            page.setMnemonic(i);
            boolean enabled = true;
            page.setBounds(btn_start, 0, num_width, 31);
            if (cur_page == i) {
                System.err.println("page to:" + cur_page);
                pageNum = cur_page;
                enabled = false;
                page.setBackground(new Color(102, 205, 170));
                page.setForeground(Color.WHITE);
            } else {
                int num = i;
                page.addActionListener(new AbstractAction() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generateTable(num, null, null);
                    }
                });
            }
//            if (icon_total - total_page >= i - 1) {
            if (icon_total - total_page >= i) {
                enabled = false;
            }
            if(cur_page-1 == i){
                enabled = true;
            }
            if (i > total_page) {
                enabled = false;
                page.setText(" ");
            }
            page.setEnabled(enabled);
            page_ctl.add(page);
            System.out.println("enabled:" + enabled + "  ---" + i);
            btn_start += num_width;
        }
        btn_start += 3;
        JButton next = new JButton(">");
        next.setBounds(btn_start, 0, icon_width, 31);
        page_ctl.add(next);

        JButton last = new JButton(">>");
        btn_start += icon_width;
        last.setBounds(btn_start, 0, icon_width, 31);
        page_ctl.add(last);

        if (total_page > cur_page) {
            next.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    generateTable(cur_page + 1, null, null);
                }
            });
            last.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    generateTable(total_page, null, null);
                }
            });
        } else {
            next.setEnabled(false);
            last.setEnabled(false);
        }

        return page_ctl;
    }

    private JPanel title_pal() {
        JPanel title_pal = new JPanel();
//        title_pal.setBounds(START_X, START_Y + 32, FROM_WIDTH - 30, 32);
        title_pal.setBounds(START_X, START_Y, FROM_WIDTH - 30, 32);
        Component oldTp = SwingUtils.searchComponentByName(card_frame, "title_pal");
        if (oldTp != null) {
            card_frame.getContentPane().remove(oldTp);
        }
        card_frame.getContentPane().add(title_pal);
        title_pal.setBackground(SystemColor.scrollbar);
        title_pal.setName("title_pal");
        title_pal.setLayout(null);

        final JToggleButton tle_toHome = new JToggleButton("首页");
        tle_toHome.setBounds(0, 1, 90, 31);
        title_pal.add(tle_toHome);

        final JToggleButton tle_toCardPage = new JToggleButton("制卡管理");
        tle_toCardPage.setSelected(true);
        tle_toCardPage.setBounds(90, 1, 90, 31);
        title_pal.add(tle_toCardPage);

        tle_toHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tle_toHome.setSelected(true);
                tle_toCardPage.setSelected(false);


            }
        });
        tle_toCardPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tle_toCardPage.setSelected(true);
                tle_toHome.setSelected(false);
                System.out.println(e);
            }
        });

        return title_pal;
    }

    private JPanel lab_pal(final JScrollPane scrollPane) {
        JPanel lab_pal = new JPanel();
//        lab_pal.setBounds(10, 40, FROM_WIDTH - 30, 31);
        lab_pal.setBounds(START_X, START_Y + 33, FROM_WIDTH - 30, 31);
        Component oldPl = SwingUtils.searchComponentByName(card_frame.getContentPane(), "lab_pal");
        if (oldPl != null) {
            if (select_options.stream().noneMatch(x -> x.getBoolean(SELECT_KEY))) {
                card_frame.getContentPane().remove(oldPl);
            } else {
                lab_pal = (JPanel) oldPl;
            }
        }
        card_frame.getContentPane().add(lab_pal);
        lab_pal.setName("lab_pal");
        lab_pal.setLayout(null);

        final JToggleButton un_card_lab = new JToggleButton("未制卡");
        un_card_lab.setBounds(0, 1, 75, 30);
        lab_pal.add(un_card_lab);

        final JToggleButton card_lab = new JToggleButton("已制卡");
        card_lab.setBounds(75, 1, 75, 30);
        lab_pal.add(card_lab);

        if (labType.equals(LabType.UN_FINISHED)) {
            un_card_lab.setSelected(true);
            card_lab.setSelected(false);
        } else {
            un_card_lab.setSelected(false);
            card_lab.setSelected(true);
        }

        un_card_lab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labType = LabType.UN_FINISHED;
                select_options.stream().forEach(x -> x.put(SELECT_KEY, false));
                generateTable(1, null, null);
            }
        });
        card_lab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labType = LabType.FINISHED;
                select_options.stream().forEach(x -> x.put(SELECT_KEY, false));
                generateTable(1, null, null);
            }
        });


        JComboBox<String> select_type = new JComboBox<String>();
        String[] options = new String[]{"全部"};

        select_type.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ItemSelectable itemSelectable = e.getItemSelectable();
                    String select_str = (String) itemSelectable.getSelectedObjects()[0];
                    Integer userId = select_options.stream().filter(x -> select_str.equals(x.getString("name")))
                            .map(x -> x.getInteger("userId")).findFirst().orElseGet(() -> Integer.valueOf(0));

                    select_options.stream().filter(x -> select_str.equals(x.getString("name"))).forEach(x -> {
                        x.put(SELECT_KEY, true);
                        select_type.setSelectedItem(x.getString("name"));
                    });
                    select_options.stream().filter(x -> !select_str.equals(x.getString("name"))).forEach(x -> {
                        x.put(SELECT_KEY, false);
                    });
                    select_options.stream().forEach(System.out::println);
                    generateTable(1, null, userId);
                }
            }
        });

        try {
            Optional<JSONObject> opt = select_options.stream().filter(x -> x.getBoolean(SELECT_KEY)).findFirst();
            opt.ifPresent(x -> {
                select_type.setSelectedItem(x.getString("name"));
            });
        } catch (Exception e) {
        }

        try {
            LinkedList<String> temp = select_options.stream().map(x -> x.getString("name")).collect(Collectors.toCollection(LinkedList::new));
            temp.addFirst("全部");
            options = temp.toArray(new String[temp.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }


        select_type.setModel(new DefaultComboBoxModel(options));
        select_type.setToolTipText("请选择类型");
        select_type.setName("select_type");
        select_type.setBounds(172, 1, 150, 30);
        lab_pal.add(select_type);

        if (LabType.UN_FINISHED.equals(labType)) {
            JButton printer_btn = new JButton("批量打印");
            printer_btn.setForeground(new Color(102, 205, 170));
            printer_btn.setBounds(350, 1, 94, 30);
            lab_pal.add(printer_btn);
            printer_btn.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
//                    for (int i = 0; i < jTab.getModel().getRowCount(); i++) {
                    for (int i = 0; i < table_data.size(); i++) {
                        Object bl = jTab.getModel().getValueAt(i, 0);
                        if (Boolean.class.isInstance(bl)) {
                            table_data.get(i).put(SELECT_KEY, (Boolean) bl);
                        }
                    }
                    if (table_data.stream().noneMatch(x -> x.get(SELECT_KEY) instanceof Boolean && x.getBoolean("checkd"))) {
                        JOptionPane.showMessageDialog(card_frame, "没有勾选选中需要打印项！", "打印提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        new Thread(() -> {
                            long oldCount = table_data.stream().filter(x -> x.get(SELECT_KEY) instanceof Boolean && x.getBoolean(SELECT_KEY)).count();
                            DoRun doRun = new DoRun((JPanel) card_frame.getRootPane().getContentPane(), "选择" + oldCount + "项，正在制卡...(0/" + oldCount + ")");
                            try {
                                long successCount = 0;
                                int count = 0;
                                for (int j = 0; j < table_data.size(); j++) {
                                    JSONObject entity = table_data.get(j);
                                    if (entity.get(SELECT_KEY) instanceof Boolean && entity.getBoolean(SELECT_KEY)) {
                                        try {
                                            doRun.setMessage("选择" + oldCount + "项，正在制卡...(" + ++count + "/" + oldCount + ")");
                                            if (ConvertServer.convertCardInfo(entity)) {
                                                successCount++;
                                            }
                                            System.err.println(entity);
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                    card_frame.validate();
                                    card_frame.repaint();
                                }

                                generateTable(1, null, null);
                                if (oldCount == successCount) {
                                    JOptionPane.showMessageDialog(card_frame, successCount + " 条记录制卡成功！", "制卡提示", JOptionPane.QUESTION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(card_frame, oldCount - successCount + " 条记录制卡失败！", "制卡提示", JOptionPane.WARNING_MESSAGE);
                                }
                            } finally {
                                doRun.stop(card_frame);
                            }
                        }).start();
                    }
                }
            });
        }

        Component oldInput = SwingUtils.searchComponentByName(card_frame, "search_input");
        JTextField search_input = searchKey != null && oldInput != null ? (JTextField) oldInput : new JTextField();

        search_input.setBounds(FROM_WIDTH - 30 - 75 - 260, 1, 260, 30);
        search_input.setColumns(10);
        if (searchKey != null)
            search_input.setText(searchKey);
        search_input.setName("search_input");
        lab_pal.add(search_input);

        JButton search_btn = new JButton("搜索");
        search_btn.setBounds(FROM_WIDTH - 30 - 75, 1, 75, 30);
        lab_pal.add(search_btn);
        search_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(search_input.getText());
                String input = search_input.getText().trim();
                searchKey = input;
                generateTable(1, input, null);
            }
        });

        return lab_pal;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        card_frame = new JFrame();
        card_frame.setGlassPane(new LoadingGlassPane());
        card_frame.setResizable(false);
        card_frame.setVisible(false);
        card_frame.setBackground(SystemColor.activeCaptionBorder);
        card_frame.setTitle("制卡管理");
        card_frame.setBounds(Constant.FROM_X, Constant.FROM_Y, FROM_WIDTH, Constant.FROM_HEIGHT);
        card_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        card_frame.getContentPane().setLayout(null);
        SwingUtils.windowtoCenter(card_frame);

        Image image = Toolkit.getDefaultToolkit().getImage(card_frame.getClass().getResource("/images/szlogo.png"));
        card_frame.setIconImage(image);

        title_pal();

        JScrollPane scrollPane = scrollPane();
        lab_pal(scrollPane);

        tab_pal(scrollPane);

        card_frame.validate();
        card_frame.repaint();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}

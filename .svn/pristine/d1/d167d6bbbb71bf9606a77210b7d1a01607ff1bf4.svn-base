package org.javatribe.calculator.domain;

import com.alibaba.fastjson.JSONObject;
import org.javatribe.calculator.exception.GlobalException;
import org.javatribe.calculator.page.CardList;
import org.javatribe.calculator.page.Details;
import org.javatribe.calculator.service.ConvertServer;
import org.javatribe.calculator.utils.DoRun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TabCellEditor extends DefaultCellEditor {

    private JButton jButton;
    private CardList cardList;

    private List<JSONObject> table_data;
    JFrame card_frame;

    public TabCellEditor(CardList cardList, JButton jButton, JFrame cardframe, List<JSONObject> tabledata) {
        super(new JTextField());
        this.setClickCountToStart(1);
        this.jButton = jButton;
        this.cardList = cardList;
        card_frame = cardframe;
        table_data = tabledata;
    }

    public TabCellEditor(JCheckBox checkBox) {
        super(checkBox);
        // TODO Auto-generated constructor stub
    }

    public TabCellEditor(JComboBox comboBox) {
        super(comboBox);
        // TODO Auto-generated constructor stub
    }

    public TabCellEditor(JTextField textField) {
        super(textField);
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String text = jButton.getText();
        jButton = new JButton();
        jButton.setText(text);
        jButton.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getColumnCount() - 1 == column) {
                    JSONObject rowData = table_data.get(row);
                    Integer dogid = rowData.getInteger("dogId");
                    String dogName = rowData.getString("dogName");
//                    Details details = new Details(Constant.token, dogid, card_frame);
//                    card_frame.setVisible(false);
                    new Details(dogid,dogName, card_frame);
                    System.err.println(table_data.get(row).get("dogId"));
                } else {
                    Arrays.stream(card_frame.getContentPane().getComponents()).forEach(System.err::println);
                    JSONObject entity = table_data.get(row);
                    System.out.println("entity:" + entity);
                    new Thread(() -> {
                        DoRun doRun = new DoRun((JPanel) card_frame.getRootPane().getContentPane(), "正在制卡，请稍后。。。");
                        try {
                            boolean bl = ConvertServer.convertCardInfo(entity);

                            Integer userId = null;
                            if (bl) {
                                JOptionPane.showMessageDialog(card_frame, "制卡成功！", "制卡提示", JOptionPane.QUESTION_MESSAGE);
                                System.out.println("entity:" + entity);
                                try {
                                    Optional<JSONObject> opt = cardList.select_options.stream().filter(x -> x.getBoolean(cardList.SELECT_KEY)).findFirst();
                                    if (opt.isPresent()) {
                                        userId = entity.getInteger("userId");
                                    }
                                } catch (Exception e2) {
                                }
                                cardList.generateTable(cardList.getPageNum(), cardList.getSearchKey(), userId);
                            } else {
                                JOptionPane.showMessageDialog(card_frame, "一条记录制卡失败!", "制卡提示", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (GlobalException ex) {
                            JOptionPane.showMessageDialog(card_frame, ex.getMessage(), "制卡提示", JOptionPane.WARNING_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(card_frame, "一条记录制卡失败!", "制卡提示", JOptionPane.WARNING_MESSAGE);
                            ex.printStackTrace();
                        }finally {
                            doRun.stop(card_frame);
                        }

                    }).start();
                }
            }


        });
        return jButton;
    }


}
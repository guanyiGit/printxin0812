package org.javatribe.calculator.service;

import cn.hutool.core.img.ImgUtil;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.common.card.containers.GraphicsInfo;
import com.zebra.sdk.common.card.containers.JobStatusInfo;
import com.zebra.sdk.common.card.enumerations.CardSide;
import com.zebra.sdk.common.card.enumerations.GraphicType;
import com.zebra.sdk.common.card.enumerations.OrientationType;
import com.zebra.sdk.common.card.enumerations.PrintType;
import com.zebra.sdk.common.card.exceptions.ZebraCardException;
import com.zebra.sdk.common.card.graphics.ZebraCardGraphics;
import com.zebra.sdk.common.card.graphics.ZebraCardImage;
import com.zebra.sdk.common.card.graphics.ZebraCardImageI;
import com.zebra.sdk.common.card.graphics.enumerations.ImagePosition;
import com.zebra.sdk.common.card.graphics.enumerations.RotationType;
import com.zebra.sdk.common.card.jobSettings.ZebraCardJobSettingNames;
import com.zebra.sdk.common.card.printer.ZebraCardPrinter;
import com.zebra.sdk.common.card.printer.ZebraCardPrinterFactory;
import com.zebra.sdk.common.card.printer.discovery.ZebraCardPrinterFilter;
import com.zebra.sdk.printer.discovery.DiscoveredUsbPrinter;
import com.zebra.sdk.printer.discovery.UsbDiscoverer;
import com.zebra.sdk.settings.SettingsException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javatribe.calculator.domain.printer.PrintDataInfo;
import org.javatribe.calculator.domain.printer.TrackData;
import org.javatribe.calculator.exception.GlobalException;
import org.javatribe.calculator.utils.GUIUtil23;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.smartcardio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class PrinterService {
    private static final Logger log = LoggerFactory.getLogger(PrinterService.class);
    private static int graphics_max_width = 2048;
    private static int graphics_max_height = 1024;
    private static int img_width = 0;
    private static int img_height = 0;
    private static int CARD_FEED_TIMEOUT = 60000;

//    public static Connection conn;

    static {
        getZXPPrint();
    }

//    private static void getConn() {
//        try {
//            if (conn == null)
//                for (DiscoveredUsbPrinter printer : UsbDiscoverer.getZebraUsbPrinters(new ZebraCardPrinterFilter())) {
//                    conn = printer.getConnection();
//                }
//        } catch (ConnectionException e) {
//            GUIUtil23.errorDialog("没有发现可用的打印机！");
//            e.printStackTrace();
//        }
//    }

    private static ZebraCardPrinter zXPPrint;

    private static ZebraCardPrinter getZXPPrint() {
        try {
            if (zXPPrint != null) {
                return zXPPrint;
            } else {
                for (DiscoveredUsbPrinter printer : UsbDiscoverer.getZebraUsbPrinters(new ZebraCardPrinterFilter())) {
                    try {
                        Connection conn = printer.getConnection();
                        conn.open();
                        zXPPrint = ZebraCardPrinterFactory.getInstance(conn);
                        return zXPPrint;
                    } catch (ConnectionException e) {
                    }
                }
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } finally {
            if (zXPPrint == null)
                GUIUtil23.errorDialog("没有发现可用的打印机,请检查打印机状态或重新拔插usb连线！");
        }
        return null;
    }

    public static boolean print(List<PrintDataInfo> printDataInfos, TrackData trackData) throws
            ConnectionException, SettingsException, ZebraCardException, IOException, InterruptedException {
//        if (conn == null) {
//            getConn();
//            if (conn == null) {
//                GUIUtil23.errorDialog("无法连接打印机！");
//                throw new GlobalException("无法连接打印机！");
//            }
//        }
//        conn.open();
//        ZebraCardPrinter printer = ZebraCardPrinterFactory.getInstance(conn);

        ZebraCardPrinter printer = getZXPPrint();

        List<GraphicsInfo> gs = printDataInfos.stream().map(x -> {
            try {
                ZebraCardGraphics graphics = new ZebraCardGraphics(printer);
                Graphics g = x.getBfImg().getGraphics();

                byte[] input = x.getInput();
                ImageIcon imgIcon = new ImageIcon(input);
                Image theImg = imgIcon.getImage();
//                int width = theImg.getWidth(null) * 2;
//                int height = theImg.getHeight(null) * 2;
                int width = 2048;
                int height = 1024;

                PrintType printType = x.getPrintType();
                graphics.initialize(width, height, OrientationType.Landscape, printType, g.getColor());
                graphics.drawImage(input, 0, 0, width, height, RotationType.RotateNoneFlipNone);

                CardSide side = x.getSide();
                GraphicsInfo grInfo = new GraphicsInfo();

                grInfo.side = side;
                grInfo.printType = printType;
                grInfo.graphicType = GraphicType.BMP;
                grInfo.graphicData = graphics.createImage(null);
                graphics.clear();
                return grInfo;
            } catch (Exception e) {
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        Map<String, String> jobSettings = new HashMap<>();
        String cardSource = "AutoDetect";//Feeder ATM Internal AutoDetect
        jobSettings.put(ZebraCardJobSettingNames.CARD_SOURCE, cardSource);
        jobSettings.put(ZebraCardJobSettingNames.CARD_DESTINATION, "Hold"); //Eject  Hold

        jobSettings.put(ZebraCardJobSettingNames.SMART_CARD_CONTACTLESS, "MIFARE");// MIFARE no
        printer.setJobSettings(jobSettings);

        long startTime = System.currentTimeMillis();
        if (trackData != null && trackData.getTrack1Data() != null) {
            Integer taskId = 0;
            try {
                taskId = printer.smartCardEncode(1);
                write(trackData);
                printer.resume();
                return jobStatus(gs, printer, startTime, taskId);
            } catch (Exception e) {
                e.printStackTrace();
                zXPPrint.getJobList().forEach(x -> {
                    try {
                        zXPPrint.cancel(x.jobId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                printer.destroy();
                GUIUtil23.errorDialog("制卡中途失败，拔掉usb并重启软件后重试！");
                zXPPrint = null;
            } finally {
            }
        }
        return false;
    }

    private static void write(TrackData trackData) {
        TerminalFactory factory = TerminalFactory.getDefault();

        Provider provider = factory.getProvider();
        System.out.println("provider info:" + provider);

        CardTerminals terminals = factory.terminals();
        try {
            List<CardTerminal> terminalList = terminals.list();
            System.out.println("terminalList: " + terminalList);

            CardTerminal terminal = terminalList.get(0);

            if (!terminal.waitForCardPresent(1000)) {
                throw new GlobalException("卡片未就緒");
            }
            Card card = terminal.connect("*");
            System.out.println("card: " + card);

            CardChannel channel = null;

            List<CardTerminal> readers = PC_SC.getReaders();
            if (readers != null && readers.size() > 0) {
                channel = PC_SC.getCardChannel(readers.get(0));
            }

            PC_SC.getUID(channel);


            PC_SC.loadPWD_A(channel);
            Byte block = new Byte("4");
            PC_SC.checkPWD_A(channel, block);
            PC_SC.writeBlack(channel, block, trackData.getTrack1Data());
            PC_SC.readBlack(channel, block);

            card.disconnect(false);
        } catch (CardException e) {
            e.printStackTrace();
            throw new GlobalException("卡片未就緒");
        }
    }


    public static void main(String[] args) {
        TrackData trackData = new TrackData();
        trackData.setTrack1Data("1111");
        write(trackData);
    }

    private static boolean jobStatus(List<GraphicsInfo> gs, ZebraCardPrinter printer, long startTime, int jobId) throws ConnectionException, ZebraCardException, InterruptedException, SettingsException {
        try {
            while (true) {
                JobStatusInfo jobStatus = printer.getJobStatus(jobId);
                System.out.println(ReflectionToStringBuilder.toString(jobStatus, ToStringStyle.MULTI_LINE_STYLE));
                if (jobStatus.printStatus.equals("done_ok")) {
                    if (gs != null && gs.size() > 0) {
                        System.err.println(gs);
                        return jobStatus(null, printer, startTime, printer.print(1, gs));
                    } else {
                        return true;
                    }
                } else if (jobStatus.printStatus.equals("ejecting_eject")) {
                    return true;
                } else if (jobStatus.printStatus.equals("done_error")) {
                    throw new GlobalException("作业出错！");
                } else if (jobStatus.printStatus.contains("cancelled")) {//取消
                    printer.cancel(jobId);
                    throw new GlobalException("作业已经被取消！");
                } else if (jobStatus.alarmInfo.value > 0) {//告警
                    System.out.println(ReflectionToStringBuilder.toString(jobStatus, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println(ReflectionToStringBuilder.toString(jobStatus.errorInfo, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println(ReflectionToStringBuilder.toString(jobStatus.magneticEncoding, ToStringStyle.MULTI_LINE_STYLE));
                    printer.cancel(jobId);
                    throw new GlobalException("作业出错！");
                } else if (jobStatus.errorInfo.value > 0) {//出錯
                    printer.cancel(jobId);
                    throw new GlobalException("作业出错！");
                } else if (jobStatus.cardPosition.contains("feeding") && System.currentTimeMillis() > startTime + CARD_FEED_TIMEOUT) {
                    printer.cancel(jobId);
                    throw new GlobalException("作业超时！");
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<GraphicsInfo> genertImgs(String path, String name, String cardNo) throws ConnectionException, ZebraCardException, IOException {
        //=================写卡，写名字和身份证号 START=================
        List<GraphicsInfo> graphicsData = new ArrayList<GraphicsInfo>();

        BufferedImage img = null;
        GraphicsInfo grInfo = new GraphicsInfo();


//        conn.open();
        ZebraCardPrinter printer = getZXPPrint();

        ZebraCardGraphics graphics = new ZebraCardGraphics(printer);
        graphics.initialize(0, 0, OrientationType.Landscape, PrintType.MonoK, Color.WHITE);

        // Back MonoK    反面
        graphics.initialize(0, 0, OrientationType.Landscape, PrintType.MonoK, Color.WHITE);

        grInfo = new GraphicsInfo();
        grInfo.side = CardSide.Back;
        grInfo.printType = PrintType.MonoK;
        grInfo.graphicType = GraphicType.BMP;

        img = getBufferedImage(path, name, cardNo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "bmp", baos);

        graphics.drawImage(baos.toByteArray(), 0, 0, 0, 0, RotationType.RotateNoneFlipNone);
        grInfo.graphicData = graphics.createImage(null);
        graphics.clear();

        graphicsData.add(grInfo);
        return graphicsData;

    }


    public static BufferedImage getBufferedImage(String path, String name, String cardNo) {
        BufferedImage bimage = null;
        try {
            ImageIcon imgIcon = new ImageIcon(path);
            Image theImg = imgIcon.getImage();
            int width = theImg.getWidth(null) == -1 ? 1023 : theImg.getWidth(null);
            int height = theImg.getHeight(null) == -1 ? 639 : theImg.getHeight(null);
            bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, 1023, 639);
            g.drawImage(theImg, 0, 0, null);
            g.setColor(Color.black);
            g.setFont(new Font("黑体", Font.BOLD, 60));
            g.drawString(name, 130, 260);   //姓名

            g.setColor(Color.black);
            g.setFont(new Font("黑体", Font.BOLD, 60));
            g.drawString(cardNo, 600, 260);    //卡号

            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }


    private static GraphicsInfo addImage(ZebraCardImageI zebraCardImage, CardSide side, PrintType printType) throws
            IOException {
        GraphicsInfo graphicsInfo = new GraphicsInfo();
        if (zebraCardImage != null) {
            graphicsInfo.fillColor = -1;
            graphicsInfo.graphicData = zebraCardImage;
            graphicsInfo.graphicType = GraphicType.BMP;
        } else {
            graphicsInfo.fillColor = 1;
            graphicsInfo.graphicType = GraphicType.NA;
        }

        graphicsInfo.side = side;
        graphicsInfo.printType = printType;
        return graphicsInfo;
    }

    private static ZebraCardImageI getZebraCardGraphics(byte[] input, ZebraCardPrinter zebraCardPrinter) throws
            ConnectionException, ZebraCardException, IOException {
        ZebraCardGraphics graphics = new ZebraCardGraphics(zebraCardPrinter);
        graphics.initialize(graphics_max_width, graphics_max_height, OrientationType.Landscape, PrintType.Color, Color.WHITE);
        graphics.drawImage(input, ImagePosition.UpperLeft, img_width, img_height, 0f, RotationType.RotateNoneFlipNone);
        return graphics.createImage();
    }


    private static ZebraCardImageI getZebraCardImage(byte[] input) throws
            ConnectionException, ZebraCardException, IOException {
        ZebraCardImage cardImage = new ZebraCardImage(input);
        return cardImage;
    }


    private static void cleanUpQuietly(ZebraCardPrinter zebraCardPrinter, Connection connection) {
        try {
            if (zebraCardPrinter != null) {
                zebraCardPrinter.destroy();
            }
        } catch (ZebraCardException e) {
        } finally {
            zebraCardPrinter = null;
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (ConnectionException e) {
        }
    }
}

package org.javatribe.calculator.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.common.card.enumerations.CardSide;
import com.zebra.sdk.common.card.exceptions.ZebraCardException;
import com.zebra.sdk.settings.SettingsException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javatribe.calculator.common.Constant;
import org.javatribe.calculator.common.HttpClientUtil;
import org.javatribe.calculator.common.HttpResult;
import org.javatribe.calculator.domain.printer.CardBackInfo;
import org.javatribe.calculator.domain.printer.CardFrontInfo;
import org.javatribe.calculator.domain.printer.PrintDataInfo;
import org.javatribe.calculator.domain.printer.TrackData;
import org.javatribe.calculator.utils.GUIUtil23;
import org.javatribe.calculator.utils.PicUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class ConvertServer {

    private static final int WIDTH = 86;
    private static final int HEIGHT = 54;
    private static final String IMG_TYPE = "png";

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private float cardBorder = 10;

    public static boolean convertCardInfo(JSONObject entity) throws IOException, URISyntaxException, ConnectionException, SettingsException, ZebraCardException, InterruptedException {
        ConvertServer convertServer = new ConvertServer();
        CardBackInfo cardBackInfo = new CardBackInfo();
        cardBackInfo.setDogName(entity.getString("dogName"));
        cardBackInfo.setDogBreed(entity.getString("dogVarieties"));
        try {
            cardBackInfo.setGender(entity.getInteger("gender") == 0 ? "雄" : entity.getInteger("gender") == 1 ? "雌" : "");
        } catch (Exception ex) {
        }
        cardBackInfo.setDogCardNum(entity.getString("dogCardNum"));
        cardBackInfo.setAddress(entity.getString("address"));
        cardBackInfo.setImmuneCardNum(entity.getString("immuneCardNum"));
        cardBackInfo.setLssueOrgName(entity.getString("lssueOrgName"));
        cardBackInfo.setDogOwnerName(entity.getString("dogOwnerName"));

        try {
            cardBackInfo.setLssueDate(new SimpleDateFormat("yyyy年MM月dd日").format(Calendar.getInstance().getTime()));
        } catch (Exception ex) {
        }

        String path = entity.getString("dogUrl");
        try {
            if (new File(path).exists()) {
                cardBackInfo.setDogImgPath(path);
            } else {
                try {
                    URL url = new URL(Constant.SERVER_URL + path);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setConnectTimeout(500);
                    if ("OK".equalsIgnoreCase(((HttpURLConnection) urlConnection).getResponseMessage())) {
                        cardBackInfo.setDogImgPath(Constant.SERVER_URL + path);
                    }
                } catch (IOException e) {
                    URL url = new URL(path);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setConnectTimeout(500);
                    if ("OK".equalsIgnoreCase(((HttpURLConnection) urlConnection).getResponseMessage())) {
                        cardBackInfo.setDogImgPath(path);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("图片加载错误");
        }
        BufferedImage frontIBF = convertServer.generateCardFrontAuto4();
        BufferedImage backIBF = convertServer.generateCardBackAuto4(cardBackInfo);

        TrackData trackData = new TrackData();
        trackData.setTrack1Data(entity.getString("dogCardNum"));
        if (convertServer.generateAutoAndPrinter(frontIBF, backIBF, trackData)) {
            Map<String, String> headers = new HashMap<>();
            if (Constant.token != null) {
                headers.put("Cookie", "dog-cookie=" + Constant.token);
            }
            String cardmakingTime = df.format(Calendar.getInstance().getTime());
            int cardmakingStatus = 1;
            Map<String, String> paramsObj = new HashMap<>();
            paramsObj.put("seqNum", String.valueOf(entity.getLongValue("seqNum")));
            paramsObj.put("cardmakingTime", cardmakingTime);
            paramsObj.put("cardmakingStatus", String.valueOf(cardmakingStatus));

            try {
                HttpResult httpResult = HttpClientUtil.executeHttpParams(Constant.SERVER_URL + "/biz/dogCard/editDogCardNum", null, null, headers, paramsObj, null);
                if (httpResult != null && httpResult.getContent() != null && httpResult.getStatusCode() == 200) {
                    JSONObject r = JSON.parseObject(httpResult.getContent());
                    if (100 != r.getInteger("status")) {
                        throw new RuntimeException("修改制卡信息失败");
                    }
                    entity.put("makeNum", entity.getInteger("makeNum") + 1);
                    entity.put("cardmakingTime", new Date());
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                GUIUtil23.errorDialog("网络错误！");
            }
        }
        return false;
    }


    public BufferedImage generateCardBackAuto4(CardBackInfo cardBackInfo) {
        BufferedImage imgBf;
        try {
            String dogImgPath = cardBackInfo.getDogImgPath();
            cardBackInfo.setDogImgPath(dogImgPath);

            String zhangPath = "/images/v3/zhang-v1.png";
            zhangPath = Constant.ROOT + zhangPath;
            cardBackInfo.setZhangPath(zhangPath);

            String qrCodePath = "/images/icon.png";
            qrCodePath = Constant.ROOT + qrCodePath;
            cardBackInfo.setQrCodePath(qrCodePath);

            String outPath = "/images/out/back-4.png";
            outPath = Constant.ROOT + outPath;
            cardBackInfo.setOutPath(outPath);

            String panelPath = "/images/v3/panel.png";
            panelPath = Constant.ROOT + panelPath;
            cardBackInfo.setPanelPath(panelPath);
            imgBf = generateCardBack4(cardBackInfo);
            imgBf = PicUtils.imgRadius(imgBf, cardBorder);

            ImageIO.write(imgBf, IMG_TYPE, new File(cardBackInfo.getOutPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return imgBf;
    }

    public BufferedImage generateCardBack4(CardBackInfo cardBackInfo) throws IOException {
        Image fromImg = ImgUtil.read(cardBackInfo.getPanelPath());
        try {
            String fromImgPath = cardBackInfo.getDogImgPath();
            if (fromImgPath != null) {
                BufferedImage fromImgBf = null;
                if (new File(fromImgPath).exists()) {
                    fromImgBf = ImageIO.read(new File(fromImgPath));
                } else {
                    try {
                        fromImgBf = ImageIO.read(new URL(fromImgPath));
                    } catch (IOException e) {
                    }

                }
                if (fromImgBf != null) {
                    fromImgBf = PicUtils.imgRadius(fromImgBf, 30);
                    Image dogImg = fromImgBf.getScaledInstance(230, 270, Image.SCALE_SMOOTH);
                    fromImg = markImg(fromImg, dogImg, 620, 34, 1.0f);
                } else {
                    fromImgBf = ImageIO.read(new File(cardBackInfo.getPanelPath()));
                }
            }
            Image zhangImg = ImgUtil.read(cardBackInfo.getZhangPath()).getScaledInstance(205, 200, Image.SCALE_SMOOTH);
            fromImg = markImg(fromImg, zhangImg, 650, 320, 1f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int lineStartPostion = 55;
        int lineInterval = 45;
        int rowStartPostion = 30;
        int rowInterval = 125;

        int rowStep = 250;

        Color keyColor = Color.BLACK;
        Color valColor = Color.BLACK;

        Font keyFontStyle = new Font("黑体", Font.BOLD, 30);
        Font valFontStyle = new Font("黑体", Font.BOLD, 30);
        String dogName = cardBackInfo.getDogName() == null ? "" : cardBackInfo.getDogName();
        fromImg = markText(fromImg, "犬  名：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, dogName, valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);

        lineStartPostion += lineInterval;
        String dogBreed = cardBackInfo.getDogBreed() == null ? "" : cardBackInfo.getDogBreed();
        fromImg = markText(fromImg, "品  种：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, dogBreed, valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);

        String dogGender = cardBackInfo.getGender() == null ? "" : cardBackInfo.getGender();
        fromImg = markText(fromImg, "性  别：", keyColor, keyFontStyle, rowStartPostion + rowInterval + rowStep, lineStartPostion, 1f);
        fromImg = markText(fromImg, dogGender, valColor, valFontStyle, rowStartPostion + rowInterval + rowStep + rowInterval, lineStartPostion, 1f);


        lineStartPostion += lineInterval;
        String dogCardNum = cardBackInfo.getDogCardNum() == null ? "" : cardBackInfo.getDogCardNum();
        fromImg = markText(fromImg, "犬证号：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, dogCardNum, valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);

        lineStartPostion += lineInterval;
        String immuneCardNum = cardBackInfo.getImmuneCardNum() == null ? "" : cardBackInfo.getImmuneCardNum();
        fromImg = markText(fromImg, "免疫证：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, immuneCardNum, valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);


        lineStartPostion += lineInterval;
        String dogOwnerName = cardBackInfo.getDogOwnerName() == null ? "" : cardBackInfo.getDogOwnerName();
        fromImg = markText(fromImg, "犬  主：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, dogOwnerName, valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);


        lineStartPostion += lineInterval;
        int lineMaxFontNum = 14;
        String address = cardBackInfo.getAddress() == null ? "" : cardBackInfo.getAddress();

        fromImg = markText(fromImg, "住  址：", keyColor, keyFontStyle, rowStartPostion, lineStartPostion, 1f);
        fromImg = markText(fromImg, address.substring(0, Integer.min(lineMaxFontNum, address.length())), valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);

        lineStartPostion += lineInterval - 4;
        if (address.length() > lineMaxFontNum) {
            fromImg = markText(fromImg, address.substring(lineMaxFontNum), valColor, valFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);
        }


        QrConfig config = new QrConfig(175, 175);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(0);
        // 设置前景色
        config.setForeColor(Color.BLACK.getRGB());
        // 设置背景色
        config.setBackColor(Color.WHITE.getRGB());
        //icon
        config.setImg(cardBackInfo.getQrCodePath());
        //分辨率
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        BufferedImage qrCodeImg = QrCodeUtil.generate(cardBackInfo.getDogCardNum(), config);

        lineStartPostion += 12;
        fromImg = markImg(fromImg, qrCodeImg.getScaledInstance(-1, -1, Image.SCALE_SMOOTH), rowStartPostion, lineStartPostion, 1f);

        rowStartPostion += 70;
        lineStartPostion += lineInterval + 50;
        String lssueOrgName = cardBackInfo.getLssueOrgName() == null ? "" : cardBackInfo.getLssueOrgName();
        fromImg = markText(fromImg, "签发单位：", keyColor, keyFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);
        fromImg = markText(fromImg, lssueOrgName, valColor, valFontStyle, rowStartPostion + rowInterval + 150, lineStartPostion, 1f);

        lineStartPostion += lineInterval;
        String lssueDate = cardBackInfo.getLssueDate() == null ? "" : cardBackInfo.getLssueDate();
        fromImg = markText(fromImg, "签发日期：", keyColor, keyFontStyle, rowStartPostion + rowInterval, lineStartPostion, 1f);
        fromImg = markText(fromImg, lssueDate, valColor, valFontStyle, rowStartPostion + rowInterval + 150, lineStartPostion, 1f);

        return ImgUtil.toBufferedImage(fromImg);
    }


    /**
     * 给图片添加文字水印<br>
     * 此方法并不关闭流
     *
     * @param pressText 水印文字
     * @param color     水印的字体颜色
     * @param font      {@link Font} 字体相关信息
     * @param x         修正值。 默认在中间，偏移量相对于中间偏移
     * @param y         修正值。 默认在中间，偏移量相对于中间偏移
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return 处理后的图像
     */
    public BufferedImage markText(Image srcI, String pressText, Color color, Font font, Integer x, Integer y, float alpha) {
        BufferedImage bufferedImageBf = ImgUtil.toBufferedImage(srcI);
        final Graphics2D g = bufferedImageBf.createGraphics();

        if (null == font) {
            // 默认字体
            font = new Font("Courier", Font.PLAIN, (int) (bufferedImageBf.getHeight() * 0.75));
        }
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font);
        // 透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 在指定坐标绘制水印文字
        final FontMetrics metrics = g.getFontMetrics(font);
        final int textLength = metrics.stringWidth(pressText);
        final int textHeight = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
        g.drawString(pressText, x == null ? Math.abs(srcI.getWidth(null) - textLength) >> 1 : x, y == null ? Math.abs(srcI.getHeight(null) - textHeight) >> 1 : y);

        g.dispose();

        return bufferedImageBf;
    }

    public BufferedImage markImg(Image srcImage, Image pressImg, int x, int y, float alpha) {
        final int pressImgWidth = pressImg.getWidth(null);
        final int pressImgHeight = pressImg.getHeight(null);

        Rectangle rectangle = new Rectangle(x, y, pressImgWidth, pressImgHeight);
        // 修正图片位置从背景的中心计算
        rectangle.setLocation(x, y);

        BufferedImage backgroundImg = ImgUtil.toBufferedImage(srcImage);
        final Graphics2D g = backgroundImg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.drawImage(pressImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null); // 绘制切割后的图
        g.dispose();

        return backgroundImg;
    }

    public BufferedImage generateCardFrontAuto4() throws IOException {
        Image imgBf;
        try {
            CardFrontInfo cardFrontInfo = new CardFrontInfo();
            String panelPath = "/images/v3/panel.png";
            panelPath = Constant.ROOT + panelPath;
            String backPath = "/images/v3/front-v4.png";
            backPath = Constant.ROOT + backPath;
            String outPath = "/images/out/front-4.png";
            outPath = Constant.ROOT + outPath;
            cardFrontInfo.setOutPath(outPath);

//            String iconPath = "/images/v3/logo.png";
//            iconPath = ROOT + iconPath;
//            cardFrontInfo.setIconPath(iconPath);
//            cardFrontInfo.setTitile("宿州市犬只准养证");
//            cardFrontInfo.setMake("宿州市公安局监制");

            cardFrontInfo.setPanelPath(panelPath);
            cardFrontInfo.setBackPath(backPath);

            imgBf = generateCardFront4(cardFrontInfo);
            imgBf = PicUtils.imgRadius(ImgUtil.toBufferedImage(imgBf), cardBorder);

            ImgUtil.write(imgBf, new File(outPath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return imgBf != null ? ImgUtil.toBufferedImage(imgBf) : null;
    }

    public Image generateCardFront4(CardFrontInfo cardFrontInfo) throws IOException {

        System.out.println("cardFrontInfo:" + ReflectionToStringBuilder.toString(cardFrontInfo, ToStringStyle.MULTI_LINE_STYLE));
        BufferedImage panelImg = ImgUtil.read(cardFrontInfo.getPanelPath());

        BufferedImage bacImg = ImgUtil.read(cardFrontInfo.getBackPath());
        bacImg = ImgUtil.toBufferedImage(bacImg.getScaledInstance(panelImg.getWidth() + 10, panelImg.getHeight() + 6, Image.SCALE_SMOOTH));

        Image outImg = markImg(panelImg, bacImg, -4, -3, 1.0f);

        Color color = Color.WHITE;
        String iconPath = cardFrontInfo.getIconPath();
        if (iconPath != null) {
            BufferedImage iconImg = ImgUtil.read(iconPath);
            outImg = PicUtils.mergeImages(iconImg, ImgUtil.toBufferedImage(outImg), 330, 30, 200, 200);
        }
        String getTitile = cardFrontInfo.getTitile();
        if (getTitile != null) {
            outImg = ImgUtil.pressText(outImg, getTitile, color, new Font("华文楷体", Font.BOLD, 80), 0, 0, 1f);
        }
        String make = cardFrontInfo.getMake();
        if (make != null) {
            outImg = ImgUtil.pressText(outImg, make, color, new Font("华文楷体", Font.BOLD, 40), 0, 100, 1f);
        }
        return outImg;
    }

    public boolean generateAutoAndPrinter(BufferedImage frontIBF, BufferedImage backIBF, TrackData trackData) throws ConnectionException, SettingsException, ZebraCardException, InterruptedException, IOException {
        ByteArrayOutputStream output = null;
        try {
            List<PrintDataInfo> printDataInfos = new ArrayList<>();

            output = new ByteArrayOutputStream();

            PrintDataInfo printFront = new PrintDataInfo();
            ImageIO.write(frontIBF, "png", output);
            printFront.setInput(output.toByteArray());
            printFront.setSide(CardSide.Front);
            printFront.setBfImg(frontIBF);
            printDataInfos.add(printFront);
            output.reset();

            PrintDataInfo printBack = new PrintDataInfo();
            ImageIO.write(backIBF, "png", output);
            printBack.setInput(output.toByteArray());
            printBack.setSide(CardSide.Back);
            printBack.setBfImg(backIBF);
            printDataInfos.add(printBack);

            return PrinterService.print(printDataInfos, trackData);
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ConnectionException, IOException, URISyntaxException, ZebraCardException, SettingsException {
//        String path = "D:\\txyWork\\printxin\\src\\main\\resources\\images\\v2\\dog10.jpg";
//        JSONObject entity = new JSONObject();
//        entity.put("cardmakingTime", new Date());
//        entity.put("dogUrl", path);
//        entity.put("dogName", "小毛毛");
//        entity.put("dogVarieties", "德国牧羊犬");
//        entity.put("dogCardNum", "12012");
//        entity.put("address", "安徽省宿州市埇桥区宿州市公安局埇桥分局朱仙庄派出所");
//        entity.put("immuneCardNum", "ME123A11");
//        entity.put("lssueOrgName", "宿州市犬只办");
//        entity.put("dogOwnerName", "张天");
//        entity.put("gender", 1);
//        entity.put("makeNum", 1);
//
//        boolean b = ConvertServer.convertCardInfo(entity);
//        System.err.println(b);
//        System.in.read();

        try {
            String path = "/pic/group1/M00/00/0E/wKgAUF0r3-WASNEYAADcobuJEGA889.jpg";
            path = Constant.SERVER_URL + path;
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(500);
            System.out.println(ReflectionToStringBuilder.toString(urlConnection, ToStringStyle.MULTI_LINE_STYLE));
        } catch (IOException e) {
            System.out.println("aaa");
        }
    }
}

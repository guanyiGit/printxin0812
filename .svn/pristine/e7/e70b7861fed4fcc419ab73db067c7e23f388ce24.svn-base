package com.shl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.javatribe.calculator.common.Constant;
import org.javatribe.calculator.utils.PicUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UsbDiscovererExample {
//    private static final String ROOT = System.getProperty("user.dir") + "/src/main/resources";


    public static void main(String[] args) {
        System.out.println(Constant.ROOT);
//        try {
//            for (DiscoveredUsbPrinter printer : UsbDiscoverer.getZebraUsbPrinters(new ZebraCardPrinterFilter())) {
//                System.out.println(printer);
//            }
//        } catch (ConnectionException e) {
//            System.out.println("Error discovering local printers: " + e.getMessage());
//        }
//        System.out.println("Done discovering local printers.");
    }

    @Test
    public void testPic() throws IOException {
        String QrCodePath = "/images/icon.png";
        QrCodePath = Constant.ROOT + QrCodePath;
        QrConfig config = new QrConfig(90, 90);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(0);
        // 设置前景色
        config.setForeColor(Color.BLACK.getRGB());
        // 设置背景色
        config.setBackColor(Color.WHITE.getRGB());
        //icon
        config.setImg(QrCodePath);
        //分辨率
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        BufferedImage qrCodeImg = QrCodeUtil.generate("12345", config);

        BufferedImage img1 = ImageIO.read(new File(QrCodePath));

        String panelPath = "/images/v2/panel-v2.png";
        panelPath = Constant.ROOT + panelPath;
        BufferedImage img2 = ImageIO.read(new File(panelPath));

        BufferedImage bufferedImage = PicUtils.mergeImages(img1, img2, 0, 0, img1.getWidth(), img1.getHeight());


        String outPath = "/images/out/test3.png";
        outPath = Constant.ROOT + outPath;
//        ImageIO.write(bufferedImage,"png",new File(outPath));

//        ByteArrayOutputStream oos = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage,"png",oos);
//
//        float rowStartPostion = 100;
//        float lineStartPostion  =  30;
//        Color valColor = Color.BLACK;
//        String text = "这是一段文字内容";
//        String keyFontStyle="宋体";
//        Integer fontSize = 15;
//        BufferedImage bufferedImage1 = Pic.markImageText(oos.toByteArray(), "签发单位：", rowStartPostion, lineStartPostion, valColor, keyFontStyle, fontSize, false);
//
//        ImageIO.write(bufferedImage1, "png", new File(outPath));


        BufferedImage img11 = ImgUtil.read(QrCodePath);
        BufferedImage img22 = ImgUtil.read(panelPath);
        Image image = ImgUtil.pressImage(img22, img11, 1, 1, 1.0f);
        BufferedImage bufferedImage1 = ImgUtil.toBufferedImage(image);
        ImgUtil.write(image, new File(outPath));

    }
}
 
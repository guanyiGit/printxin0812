package org.javatribe.calculator.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * 把两张图片合并
 *
 * @author lizhiyong
 * @version $Id: Pic.java, v 0.1
 * 2015-6-3 下午3:21:23 1111 Exp $
 */
public class PicUtils {


    private Font font = new Font("宋体", Font.PLAIN, 12); // 添加字体的属性设置

    private Graphics2D g = null;

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;


    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.orange);//设置字体颜色    
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标    
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr, int x, int y,
                                     boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标    
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length() * this.fontsize / 2 + 5;// 重新计算文本输出位置    
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;// 重新计算文本输出位置    
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     * <p>
     * 时间:2007-10-8
     *
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.blue);//设置字体颜色    
            if (this.font != null)
                g.setFont(this.font);
            g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

        try {
            int w = b.getWidth();
            int h = b.getHeight();

            g = d.createGraphics();
            g.drawImage(b, 100, 20, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }


    /**
     * 在图片1中画图片2
     *
     * @param x          X坐标位置
     * @param y          Y坐标位置
     * @param destPath   新图片存储位置
     * @param formatName 图片格式
     * @param img1       图片1
     * @param img2       图片2
     * @return
     * @throws IOException
     */
    public static boolean drawNewImageInImage1(BufferedImage img1, BufferedImage img2,
                                               int x, int y, String destPath, String formatName) throws IOException {
        boolean isSuccess;
        Graphics img1Graphics = null;
        try {
            img1Graphics = img1.getGraphics();
            img1Graphics.drawImage(img2, x, y, img2.getWidth(), img2.getHeight(), null);
            isSuccess = ImageIO.write(img1, formatName, new File(destPath));
        } finally {
            if (img1Graphics != null) {
                img1Graphics.dispose();
            }
        }
        return isSuccess;
    }

    /**
     * 判断是否需要重新画最大边框的图片背景
     *
     * @param img1 图片1
     * @param img2 图片2
     * @param x    图片2起始X坐标
     * @param y    图片2起始Y坐标
     * @return
     */
    private static boolean needReptain(BufferedImage img1, BufferedImage img2,
                                       int x, int y) {
        if (img1 == null || img2 == null || img1.getWidth() <= 0 || img1.getHeight() <= 0
                || img2.getWidth() <= 0 || img2.getHeight() <= 0) {
            throw new IllegalArgumentException("图片信息不正确");
        }
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
        return x < 0 || y < 0 || w1 - x < w2 || h1 - y < h2;
    }


    /**
     * 添加文字
     *
     * @param outPath     输出图片路径
     *                    字体颜色等在函数内部实现的
     * @param filePath    源图片路径
     * @param markContent 图片中添加内容
     * @return
     */
    //给jpg添加文字

    /**
     * 添加文字
     *
     * @param fileData    源图片
     * @param markContent 图片文字
     * @param x           文字x坐标
     * @param y           文字y坐标
     * @param fontColor   文字颜色
     * @param fontStyle   文字底色
     * @param fontSize    文字大小
     * @return 图片信息
     */
    public static BufferedImage markImageText(byte[] fileData, String markContent, Float x, Float y, Color fontColor, String fontStyle, Integer fontSize, boolean isBold) {
        ImageIcon imgIcon = new ImageIcon(fileData);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);

        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(fontColor == null ? Color.red : fontColor);
        g.setBackground(Color.WHITE);
        g.drawImage(theImg, 0, 0, null);

        g.setFont(new Font(fontStyle == null ? "宋体" : fontStyle, isBold ? Font.BOLD : Font.PLAIN, fontSize == null ? 50 : fontSize)); //字体、字型、字号
        markContent = markContent == null ? "" : markContent;
        g.drawString(markContent, x == null ? width >> 1 : x, y == null ? height >> 1 : y); //画文字
        g.dispose();
        g.setClip(0, 0, 300, 300);

        return bimage;
    }


    /**
     * 添加文字
     *
     * @param fileData    源图片
     * @param markContent 图片文字
     * @param x           文字x坐标
     * @param y           文字y坐标
     * @param fontColor   文字颜色
     * @param fontStyle   文字底色
     * @param fontSize    文字大小
     * @return 图片信息
     */
    public static byte[] markImageTextToBytes(byte[] fileData, String markContent, Float x, Float y, Color fontColor, String fontStyle, Integer fontSize, boolean isBold) {
        BufferedImage bfImg = markImageText(fileData, markContent, x, y, fontColor, fontStyle, fontSize, isBold);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bfImg, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }


    /**
     * 合并图片
     *
     * @param fromImgPath 图片1
     * @param toImgPath   图片2
     * @param x           坐标x
     * @param y           坐标y
     * @return
     */
    public static BufferedImage mergeImages(String fromImgPath, String toImgPath, Integer x, Integer y, Integer width, Integer height) {
        try {
            BufferedImage fromImg = ImageIO.read(new File(fromImgPath));
            BufferedImage toImgImg = ImageIO.read(new File(toImgPath));
            return mergeImages(fromImg, toImgImg, x, y, width, height);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static BufferedImage imgRadius(BufferedImage inImg, float cornerRadius) {
        int width = inImg.getWidth();
        int height = inImg.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(inImg, 0, 0, null);
        g2.dispose();
        return outputImage;
    }


    /**
     * 合并图片
     *
     * @param fromImg 图片1
     * @param toImg   图片2
     * @param x       坐标x
     * @param y       坐标y
     * @return
     */
    public static BufferedImage mergeImages(BufferedImage fromImg, BufferedImage toImg, Integer x, Integer y, Integer width, Integer height) {
        try {
            int w = fromImg.getWidth();
            int h = fromImg.getHeight();
            Graphics2D g = toImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(fromImg, x == null ? toImg.getWidth() - w >> 1 : x, y == null ? toImg.getHeight() - h >> 1 : y, width == null ? w : width, height == null ? h : height, null);
            g.dispose();
            return toImg;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 合并图片
     *
     * @param fromImg 图片1
     * @param toImg   图片2
     * @param x       坐标x
     * @param y       坐标y
     * @return
     */
    public static byte[] mergeImagesToBytes(BufferedImage fromImg, BufferedImage toImg, Integer x, Integer y, Integer width, Integer height) {
        BufferedImage bfImg = mergeImages(fromImg, toImg, x, y, width, height);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bfImg, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }


    /**
     * 合并图片
     *
     * @param fromImgPath 图片1
     * @param toImgPath   图片2
     * @param x           坐标x
     * @param y           坐标y
     * @return
     */
    public static byte[] mergeImagesToBytes(String fromImgPath, String toImgPath, Integer x, Integer y, Integer width, Integer height, int cornerRadius) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage fromImg = null;
            try {
                fromImg = ImageIO.read(new File(fromImgPath));
            } catch (IOException e) {
                fromImg = ImageIO.read(new URL(fromImgPath));
            }
            if (cornerRadius > 0) {
                fromImg = imgRadius(fromImg, cornerRadius);
            }
            ImageIO.write(mergeImages(fromImg, ImageIO.read(new File(toImgPath)), x, y, width, height), "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }


    /**
     * 添加文字
     *
     * @param filePath    源图片路径
     * @param markContent 图片中添加内容
     * @param outPath     输出图片路径
     *                    字体颜色等在函数内部实现的
     */
    //给jpg添加文字
    public static boolean createStringMark(String filePath, String markContent, String outPath) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
        System.out.println(width);
        System.out.println(height);
        System.out.println(theImg);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color mycolor = Color.red;
        g.setColor(mycolor);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);
        g.setFont(new Font("宋体", Font.PLAIN, 50)); //字体、字型、字号
        g.drawString(markContent, width / 2, height / 2); //画文字
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outPath); //先用一个特定的输出文件名
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(100, true);  //
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws IOException {
        //85.5 * 54
        String fromImgPath = "f:/dog.jpg";
        String toImgPath = "f:/meinv.jpg";
        String distPath = "f:/dsct.png";
        String qrcodePath = "f:/qrcode.png";

        BufferedImage imgBf = mergeImages(fromImgPath, toImgPath, null, null, null, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(imgBf, "png", bos);
        byte[] imgByte = bos.toByteArray();

        imgBf = markImageText(imgByte, "测试文字", null, null, null, null, null, false);
        ImageIO.write(imgBf, "png", new File(distPath));
        bos.close();

//
//        QrConfig config = new QrConfig(300, 300);
//        // 设置边距，既二维码和背景之间的边距
//        config.setMargin(3);
//        // 设置前景色，既二维码颜色（青色）
//        config.setForeColor(Color.CYAN.getRGB());
//        // 设置背景色（灰色）
//        config.setBackColor(Color.GRAY.getRGB());
//        //icon
//        config.setImg(fromImgPath);
//        //分辨率
//        config.setErrorCorrection(ErrorCorrectionLevel.H);
//        QrCodeUtil.generate("https://hutool.cn/", config, new File(qrcodePath));

    }
}  
package org.javatribe.calculator.service;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.javatribe.calculator.exception.GlobalException;

import javax.smartcardio.*;
import java.util.Arrays;
import java.util.List;

public class PC_SC {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int a = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            sb.append("0x");
            sb.append(HEX_CHAR[a / 16]);
            sb.append(HEX_CHAR[a % 16]);
            sb.append(" ");
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] formData(String str, int dataLen) {
        int maxLen = 16;
        String fillVal = "0";
        if (str == null || str.length() > maxLen * 2) {
            throw new GlobalException("输入数据格式有误");
        }
        int len = str.length();
        StringBuilder fillVals = new StringBuilder();
        if (len < dataLen) {
            for (int i = 0; i < (dataLen - len); i++) {
                fillVals.append(fillVal);
            }
            str = fillVals + str;
        }
        len = str.length();
        if (len < maxLen * 2) {
            fillVals.replace(0, fillVals.length(), "");
            for (int i = 0; i < (maxLen * 2 - len); i++) {
                fillVals.append(fillVal);
            }
            str += fillVals;
        }
        System.out.println(Arrays.toString(str.toCharArray()));
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
            throw new GlobalException("输入数据格式有误");
        }
    }


    public static byte[] byteMerger(byte[] arr1, byte[] arr2) {
        byte[] dscArr = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, dscArr, 0, arr1.length);
        System.arraycopy(arr2, 0, dscArr, arr1.length, arr2.length);
        return dscArr;
    }


    public static List<CardTerminal> getReaders() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> readers = factory.terminals().list();
        System.out.println("readers:" + readers);
        return readers;
    }

    public static CardChannel getCardChannel(CardTerminal terminal) throws CardException {
        terminal.waitForCardPresent(0L);//等待放置卡片
        CardChannel channel = terminal.connect("*").getBasicChannel();
        System.out.println("channel:" + channel);
        return channel;
    }

    /**
     * 读取卡片序列号
     * 读 IC卡的0扇区0区
     * 1. 获取“已经建立连接的 PICC”的序列号
     * UINT8 GET_UID[5]={FFh, CAh, 00h, 00h, 04h};
     * 2. 获取“已经建立连接的 ISO 14443-A PICC”的 ATS  不支持6d00
     * UINT8 GET_ATS[5]={ FFh, CAh, 01h, 00h, 04h};
     *
     * @param channel
     * @return
     * @throws CardException
     */
    public static byte[] getUID(CardChannel channel) throws CardException {
        CommandAPDU getUID = new CommandAPDU(0xFF, 0xCA, 0x00, 0x00, 0x04);// 中文API第12页
        ResponseAPDU resp = channel.transmit(getUID);// 发送getUID指令
        System.out.println("getUID:" + resp.toString() + "  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("获取卡信息失败");
        }
        return resp.getData();
    }

    /**
     * 加载认证密钥
     * <p>
     * 根据文档，返回0x90 0x00 即为操作成功。
     *
     * @param channel
     * @return
     * @throws CardException
     */
    public static byte[] loadPWD_B(CardChannel channel) throws CardException {
        byte[] header = {(byte) 0xFF, (byte) 0x82, (byte) 0x00, (byte) 0x61, (byte) 0x06};
        byte[] pwd = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        byte[] input = byteMerger(header, pwd);
        CommandAPDU loadPWD = new CommandAPDU(input);// 构造加载认证密钥APDU指令 ，中文API第13页
        ResponseAPDU resp = channel.transmit(loadPWD);// 发送loadPWD指令
        System.out.println("loadPWD_B:" + resp.toString() + "  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("加载秘钥失败");
        }
        return resp.getData();
    }


    /**
     * 加载认证密钥
     * <p>
     * 根据文档，返回0x90 0x00 即为操作成功。
     *
     * @param channel
     * @return
     * @throws CardException
     */
    public static byte[] loadPWD_A(CardChannel channel) throws CardException {
        byte[] header = {(byte) 0xFF, (byte) 0x82, (byte) 0x00, (byte) 0x60, (byte) 0x06};
        byte[] pwd = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
//        byte[] pwd = {(byte) 0x13, (byte) 0x86, (byte) 0x80, (byte) 0x81, (byte) 0x15, (byte) 0x15};

        byte[] input = byteMerger(header, pwd);
        CommandAPDU loadPWD = new CommandAPDU(input);// 构造加载认证密钥APDU指令 ，中文API第13页
        ResponseAPDU resp = channel.transmit(loadPWD);// 发送loadPWD指令
        System.out.println("loadPWD_A:" + resp.toString() + "  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("加载秘钥失败");
        }
        return resp.getData();
    }

   /*
    4、状态字节
        状态字节SW1 SW2表示命令结束时卡设备的状态。它们的值由ISO/IEC 7816-4 指定。
        注   ISO/IEC7816-4 强制6个状态字节值的定义。
        ‘9000’    命令正常结束；
        ‘6E00’     CLA不支持；
        ‘6D00’     CLA支持，但INS不支持；
        ‘6B00’     CLA  INS 均支持，但是P1 P2错误；
        ‘6700’     CLA  INS  P1  P2均支持，但是P3错误；
        ‘6F00’     命令不支持，但是没有找出精确的诊断；
        根据定义，在传入的SW2字节的前沿12etu后，命令结束。
     */

    /**
     * 认证密钥
     * <p>
     * 16个扇区/4个区块/16k
     * 区块从00递增
     * 每个扇区的第三区块是密码和控制字存储的区块，不能作为数据存储使用。 0扇区的0区块，存储的是卡片的序列号，不可更改。
     * 每个扇区只需认证一次密钥即可对三个数据块随意读写。
     * 出厂默认的控制字FF078069表示KEYA 或者KEYB都可以随意读写。
     * <p>
     * 根据文档，返回0x90 0x00即为认证成功。
     *
     * @return
     */
    public static byte[] checkPWD_B(CardChannel channel, Byte block) throws CardException {
        block = block == null ? 0x4 : block;
        byte[] header = {(byte) 0xFF, (byte) 0x86, (byte) 0x00, (byte) 0x00, (byte) 0x05};
        byte[] pwd = {(byte) 0x01, (byte) 0x00, block, (byte) 0x61, (byte) 0x61};

        byte[] input = byteMerger(header, pwd);
        CommandAPDU authPWD = new CommandAPDU(input);// 构造加载认证密钥APDU指令 ，中文API第13页
        ResponseAPDU resp = channel.transmit(authPWD);// 发送loadPWD指令
        System.out.println("checkPWD_B:" + resp.toString() + "    block:【" + block + "】  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("校验block:【" + block + "】失败");
        }
        return resp.getData();
    }

    /**
     * 认证密钥
     * <p>
     * 16个扇区/4个区块/16k
     * 区块从00递增
     * 每个扇区的第三区块是密码和控制字存储的区块，不能作为数据存储使用。 0扇区的0区块，存储的是卡片的序列号，不可更改。
     * 每个扇区只需认证一次密钥即可对三个数据块随意读写。
     * 出厂默认的控制字FF078069表示KEYA 或者KEYB都可以随意读写。
     * <p>
     * 根据文档，返回0x90 0x00即为认证成功。
     *
     * @return
     */
    public static byte[] checkPWD_A(CardChannel channel, Byte block) throws CardException {
        block = block == null ? 0x4 : block;
        byte[] header = {(byte) 0xFF, (byte) 0x86, (byte) 0x00, (byte) 0x00, (byte) 0x05};
        byte[] pwd = {(byte) 0x01, (byte) 0x00, block, (byte) 0x60, (byte) 0x60};

        byte[] input = byteMerger(header, pwd);
        CommandAPDU authPWD = new CommandAPDU(input);// 构造加载认证密钥APDU指令 ，中文API第13页
        ResponseAPDU resp = channel.transmit(authPWD);// 发送loadPWD指令
        System.out.println("checkPWD_A:" + resp.toString() + "    block:【" + block + "】  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("校验block:【" + block + "】失败");
        }
        return resp.getData();
    }


    /**
     * 读区块
     * <p>
     * 读区块前必须完成密钥认证
     * 读写同一扇区不同区块只需验证一次密码~
     * <p>
     * 根据文档，返回0x90 0x00即为认证成功。
     *
     * @return
     */
    public static byte[] readBlack(CardChannel channel, Byte block) throws CardException {
        block = block == null ? 0x4 : block;
        // 读区块
        CommandAPDU getData4 = new CommandAPDU(0xFF, 0xB0, 0x00, block, 0x10);// 构造 读区块APDU指令，中文API第17页
        ResponseAPDU resp = channel.transmit(getData4);// 发送 读区块指令
        System.out.println("readBlack:" + resp.toString() + "    block:【" + block + "】  data:" + bytesToHexString(resp.getData()));
        if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
            throw new GlobalException("读取block:【" + block + "】失败");
        }
        return resp.getData();
    }

    /**
     * 写区块
     * <p>
     * 读区块前必须完成密钥认证
     * 读写同一扇区不同区块只需验证一次密码~
     * <p>
     * 根据文档，返回0x90 0x00即为认证成功。1234567890
     * 1234567890
     *
     * @return
     */
    public static byte[] writeBlack(CardChannel channel, Byte block, String input) {
        block = block == null ? 0x4 : block;
        try {
            // 写区块
            byte[] up = formData(input, 7);
//            byte[] up = formData(input,16);
            CommandAPDU upData = new CommandAPDU(0xFF, 0xD6, 0x00, block, up, 0, up.length);//构造 写区块APDU指令，中文API第18页
            ResponseAPDU resp = channel.transmit(upData);// 发送写块指令
            System.out.println("writeBlack:" + resp.toString() + "    block:【" + block + "】  data:" + bytesToHexString(up));
            if (!"9000".equals(Integer.toHexString(resp.getSW()))) {
                throw new GlobalException("写入block:【" + block + "】失败");
            }
            return resp.getData();
        } catch (CardException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws DecoderException, CardException {
        getReaders();
        String data = "36864G";
        System.out.println(Arrays.toString(formData(data, 7)));
    }


}

package org.javatribe.calculator.domain.printer;

import com.zebra.sdk.common.card.enumerations.CardSide;
import com.zebra.sdk.common.card.enumerations.PrintType;
import lombok.Data;

import java.awt.image.BufferedImage;

public class PrintDataInfo {


    byte[] input;

    private BufferedImage bfImg;

    /**
     * 打印的正反面 Front/back
     */
    private CardSide side;

    /**
     * 图像数据的打印类型 Color
     */
    private PrintType printType = PrintType.Color;

    public BufferedImage getBfImg() {
        return bfImg;
    }

    public void setBfImg(BufferedImage bfImg) {
        this.bfImg = bfImg;
    }

    public byte[] getInput() {
        return input;
    }

    public void setInput(byte[] input) {
        this.input = input;
    }

    public CardSide getSide() {
        return side;
    }

    public void setSide(CardSide side) {
        this.side = side;
    }

    public PrintType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }
}

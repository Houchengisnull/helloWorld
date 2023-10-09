package org.hc.tool.print;

/**
 * 在控制台打印花里胡哨的字體
 * 僅能在控制臺顯示
 * 于CMD窗口暫無效
 */
public final class Colorfuls {

    public enum Color {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PINK,
        LIGHTBLUE,
        GREY,
        RED_IN_BLACK,
        GREEN_IN_RED,
        YELLOW_IN_GREEN,
        BLUE_IN_YELLOW,
        PINK_IN_BLUE,
        LIGHTBLUE_IN_PINK,
        GREY_IN_LIGHTBLUE,
        BLACK_IN_GREY
    }

    private static String COLOR_END = "\033[0m";

    public static String toBlackInGrey(String value) {
        return drawTo(Color.BLACK_IN_GREY, value);
    }

    public static String toBlackInGrey(String value, String keyWords) {
        return drawInSection(Color.BLACK_IN_GREY, value, keyWords);
    }

    public static String toBlue (String value) {
        return drawTo(Color.BLUE, value);
    }

    public static String toBlue (String value, String keyWord) {
        return drawInSection(Color.BLUE, value, keyWord);
    }

    /**
     * 下劃線
     * @return 添加下劃線的字符串
     */
    public static String toBlack (String value) {
        return drawTo(Color.BLACK, value);
    }

    public static String toBlack (String value, String keyWords) {
        return drawInSection(Color.BLACK, value, keyWords);
    }

    public static String toRed(String value){
        return drawTo(Color.RED, value);
    }
    /**
     * 局部變紅
     * @param value 內容
     * @param keyWords 關鍵字
     * @return 返回關鍵字變紅的內容
     */
    public static String toRed(String value, String keyWords){
        return drawInSection(Color.RED, value, keyWords);
    }

    /**
     * 局部字體變色
     */
    private static String drawInSection(Color color, String value, String keyWords) {
        return value.replace(keyWords, drawTo(color, keyWords));
    }

    /**
     * 全部字體變色
     * @param color 顏色
     * @param value 內容
     * @return 更新后的字符串
     */
    private static String drawTo(Color color, String value) {
        String colorValue = getColorValue(color);
        return colorValue + value + (colorValue != null ? COLOR_END : null);
    }

    private static String getColorValue(Color color){
        switch (color) {
            case BLACK : return "\033[30;4m";
            case RED : return "\033[31;4m";
            case GREEN : return "\033[32;4m";
            case YELLOW : return "\033[33;4m";
            case BLUE : return "\033[34;4m";
            case PINK : return "\033[35;4m";
            case LIGHTBLUE : return "\033[36;4m";
            case GREY : return "\033[37;4m";
            case RED_IN_BLACK : return "\033[40;31;4m";
            case GREEN_IN_RED : return "\033[41;32;4m";
            case YELLOW_IN_GREEN : return "\033[42;33;4m";
            case BLUE_IN_YELLOW : return "\033[43;34;4m";
            case PINK_IN_BLUE : return "\033[44;35;4m";
            case LIGHTBLUE_IN_PINK : return "\033[45;36;4m";
            case GREY_IN_LIGHTBLUE : return "\033[46;37;4m";
            case BLACK_IN_GREY : return "\033[47;4m";
            default:return null;
        }
    }
}

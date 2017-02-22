package com.jock.tbshopcar.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {

    /**
     * ��Ǯ�˷�
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).toString();
    }

    /**
     * ��Ǯ�˷�
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.multiply(b2).toString();
    }

    /**
     * �˷�
     *
     * @param v1    ����
     * @param v2    ������
     * @param scale С���㱣��λ��
     * @return
     */
    public static String multiplyWithScale(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal result = b1.multiply(b2);
        result = result.setScale(scale);
        return result.toString();
    }

    /**
     * ��Ǯ����
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String divide(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2).toString();
    }

    /**
     * ��������ϣ���������ѭ��С������ô�ͻ��׳��쳣������������Ƕ�С������λ��������
     *
     * @param v1
     * @param v2 С��ģʽ ��DOWN����ʾֱ�Ӳ����������룬�ο�{@link RoundingMode}
     * @return
     */
    public static String divideWithRoundingDown(String v1, String v2) {
        return divideWithRoundingMode(v1, v2, RoundingMode.DOWN);
    }

    /** ѡ��С�����ִ���ʽ */
    public static String divideWithRoundingMode(String v1, String v2,
                                                RoundingMode roundingMode) {
        return divideWithRoundingModeAndScale(v1, v2, RoundingMode.DOWN, 0);
    }

    /**
     * ѡ��С����󲿷ִ���ʽ���Լ�������λС��
     *
     * @param v1           ����
     * @param v2           ������
     * @param roundingMode С������ģʽ
     * @param scale        ������λ
     * @return
     */
    public static String divideWithRoundingModeAndScale(String v1, String v2,
                                                        RoundingMode roundingMode, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, roundingMode).toString();
    }

    /**
     * ��Ǯ����
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.subtract(b2).toString();
    }


}

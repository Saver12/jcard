package com.saver12;

public class Test {

    public static void main(String[] args) {

        short x = 260;

        byte[] arr = new byte[]{(byte) (x >>> 8), (byte) (x & 0xFF)};


        System.out.println(Integer.toBinaryString(x));
        System.out.println((byte)x);
        System.out.println(x);

        short res = (short) ((arr[0] << 8) | (arr[1] & 0xff));

        System.out.println(res);
    }
}

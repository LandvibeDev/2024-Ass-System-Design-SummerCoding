package org.landvibe.ass1.dummy;

public class Dummy {
    private byte[] data;

    /**
     * count MB 크기의 Dummy 객체를 생성합니다.
     * @param count 생성할 Dummy 객체의 크기(MB 단위)
     */
    public Dummy(int count){
        data = new byte[1024 * 1024 * count];
    }
}

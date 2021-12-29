package com.redare.nio;

import java.nio.IntBuffer;

/**
 * @author 田昆
 * @date 2021/12/27 11:09
 **/
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i*2);
        }
    }
}

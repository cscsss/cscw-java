package com.buffer;

import java.nio.ByteBuffer;

/**
 * @Author: csc
 * @Date: 2020/7/2
 */
public class TestBuffer {

    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printBuffer(buffer);

        buffer.put((byte)'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'0');
        printBuffer(buffer);
        System.out.println("-------");
        //position = 0 limit = position
        buffer.flip();
        System.out.println((char)buffer.get()+""+(char)buffer.get());
        buffer.compact();
        buffer.clear();
        printBuffer(buffer);

    }

    public static void  printBuffer(ByteBuffer buffer){
        System.out.println("初始化position : " + buffer.position());
        //输出初始化收limit的值
        System.out.println("初始化limit : " + buffer.limit());
        //输出初始化后capacity的值
        System.out.println("初始化capacity : " + buffer.capacity());
        //输出初始化后ByteBuffer内容
        printBuffer2(buffer);

    }

    public static void printBuffer2(ByteBuffer buffer){

        //记录当前位置
        int position = buffer.position();
        //指针移动到0
        buffer.position(0);
        //循环输出每个字节内容
        for(int i = 0;i < buffer.limit();i++){
            //读取操作,指针会自动移动
            byte b = buffer.get();
            System.out.print(Integer.toHexString(b)+"|");
        }
        //指针再移动到标记位置
        buffer.position(position);
        System.out.println();
    }

}

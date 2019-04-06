/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.hsm;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author Dell
 */
public class HsmTransactor {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 9998);
        
        if(socket.isConnected())
        {
            System.out.println("Connected On "+ socket.getInetAddress());
        }

        String command = "0006303030304e43";

        // the following line converts the hex command string to a byte array
        byte[] bytes = ByteBuffer.allocate(8).putLong(Long.parseLong(command, 16)).array();

        OutputStream out = socket.getOutputStream();
        BufferedOutputStream bufferedOut = new BufferedOutputStream(out, 1024);
        bufferedOut.write(bytes);
        bufferedOut.flush();

        InputStream in = socket.getInputStream();
        int result;
        while ((result = in.read()) != -1) {
            System.out.print((char) result);
        }
        socket.close();
    }

}

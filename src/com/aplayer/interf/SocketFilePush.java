package com.aplayer.interf;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketFilePush implements OnPushListener{
	
	private Socket socket;
	private String address;
	private int port;
	
	public SocketFilePush(String address,int port) throws IOException {
		this.address = address;
		this.port = port;
	}

	@Override
	public void onPush(File file, File root) throws IOException {
		String absPath = file.getAbsolutePath();
		String path = absPath.replace(root.getAbsolutePath(), "");
		socket = new Socket(address, port);
		System.out.println("send:"+path);
		OutputStream os = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
        FileInputStream fis = new FileInputStream(file);
        int length = 0;
        String head = "password=123456&action=push&path="+path;
        byte[] empty = new byte[255-head.length()];
        dos.write(head.getBytes(), 0, head.length());
        dos.write(empty,0,empty.length);
        byte[] sendBytes = new byte[1024];
        while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
            dos.write(sendBytes, 0, length);
        }
        dos.flush();
        dos.close();
        fis.close();
        System.out.println("send complate");
	}

}

package com.aplayer.main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.aplayer.bean.Config;
import com.aplayer.interf.ADBFilePush;
import com.aplayer.interf.SocketFilePush;
import com.aplayer.util.CMDExcutor;
import com.aplayer.util.FileUtil;


public class Main {

	public static void main(String[] args) throws Exception {
		try{
			inputConfig();
			while(true){
		        System.out.print(">>");
				excuteArgCommond(new Scanner(System.in).nextLine());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void excuteArgCommond(String code) throws Exception{
		if("push".equals(code)){
			File root = new File(Config.originPath);
			FileUtil fu = new FileUtil(root);
			fu.setPushListener(Config.pushListener);
			fu.pushFile();
			return;
		}
		CMDExcutor cmde = new CMDExcutor();
		if("startx".equals(code)){
			cmde.execute("adb shell am start -n com.polyvi.xFacePlayer/com.polyvi.xface.XPlayerActivity");
		}else if("stopx".equals(code)) {
			cmde.execute("adb shell am force-stop  com.polyvi.xFacePlayer");
		}else if("screencap".equals(code)){
			String[] codes = {"adb shell /system/bin/screencap -p /sdcard/screencap.png;",
					"adb pull /sdcard/screencap.png " + System.getProperty("user.dir")
					+ File.separator + "screencap.png"};
			cmde.execute(codes);
		}
	}
	
	private static void inputConfig() throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("请选择连接方式. 1:ADB  2:socket 3:ftp");
		int choose = sc.nextInt();
		if(choose == 1){
			Config.pushListener = new ADBFilePush();
			new CMDExcutor().execute("adb devices");
		}else if(choose == 2){
			System.out.println("请输入目标设备地址");
			String[] address = sc.nextLine().split(":");
			Config.hostUrl = address[0];
			if(address.length>1)Config.port = Integer.parseInt(address[1]);
			Config.pushListener = new SocketFilePush(Config.hostUrl,Config.port);
		}else {
			System.out.println("未实现");
		}
	}
}

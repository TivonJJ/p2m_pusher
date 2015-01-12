package com.aplayer.interf;

import java.io.File;

import com.aplayer.bean.Config;
import com.aplayer.util.CMDExcutor;

public class ADBFilePush implements OnPushListener{
	
	private CMDExcutor cmde = new CMDExcutor();

	@Override
	public void onPush(File file, File root) {
		String absPath = file.getAbsolutePath();
		String path = absPath.replace(root.getAbsolutePath(), "");
		String pushCode = "adb push \"" + absPath + "\" \""+Config.targetPath+(path.replace("\\", "/"))+"\"";
		cmde.execute(pushCode);
		System.out.println(pushCode);
	}

}

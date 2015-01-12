package com.aplayer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.aplayer.interf.OnFileListener;
import com.aplayer.interf.OnPushListener;

public class FileUtil {
	
	private File logFile = new File(System.getProperty("user.dir")+File.separator + "push.plg");
	private Properties prop;
	private File root;
	private OnPushListener pushListener;
	
	public FileUtil(File root) throws FileNotFoundException, IOException {
		initFileDate(root);
	}
	
	public void initFileDate(File root) throws FileNotFoundException, IOException{
		this.root = root;
		if(!logFile.exists()){
			logFile.createNewFile();
		}
		prop = new Properties();
		prop.load(new FileInputStream(logFile));
//		final String rp = root.getAbsolutePath();
//		prop.clear();
//		eachFile(root,new OnFileListener() {
//			@Override
//			public void onFile(File file) {
//				String path = file.getAbsolutePath().replace(rp, "");
//				path = CoderUtils.string2ASCII(path);
//				prop.setProperty(path, Long.toString(file.lastModified()));
//			}
//		});
//		OutputStream os = new FileOutputStream(logFile);
//		prop.store(os, "");
//		os.flush();
//		os.close();
	}

	public void eachFile(File dir,OnFileListener onFileListener){
		File[] files = dir.listFiles();
		if(null == files || files.length<=0)return;
		for (File file : files) {
			if (file.isDirectory()) {
				eachFile(file,onFileListener);
			} else {
				onFileListener.onFile(file);
			}
		}
	}
	
	public void pushFile() throws Exception{
		final String rp = root.getAbsolutePath();
		final ArrayList<File> pushFiles = new ArrayList<File>();
		eachFile(root, new OnFileListener() {
			@Override
			public void onFile(File file) {
				String path = file.getAbsolutePath().replace(rp, "");
				String key = CoderUtils.string2ASCII(path);
				if(prop.containsKey(key)){
					long lastTime = Long.parseLong(prop.getProperty(key));
					if(file.lastModified() != lastTime){
						pushFiles.add(file);
					}
				}else{
					pushFiles.add(file);
				}
			}
		});
		System.out.println("changes:"+pushFiles);
		for(int i=0;i<pushFiles.size();i++){
			File file = pushFiles.get(i);
			String absPath = file.getAbsolutePath();
			String path = absPath.replace(rp, "");
//			String pathExName = path.substring(0,path.length()-file.getName().length());
			pushListener.onPush(file, root);
			path = CoderUtils.string2ASCII(path);
			prop.setProperty(path, Long.toString(file.lastModified()));
		}
		OutputStream os = new FileOutputStream(logFile);
		prop.store(os, "update"+new Date().getTime());
		os.flush();
		os.close();
	}

	public File getLogFile() {
		return logFile;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public OnPushListener getPushListener() {
		return pushListener;
	}

	public void setPushListener(OnPushListener pushListener) {
		this.pushListener = pushListener;
	}

}

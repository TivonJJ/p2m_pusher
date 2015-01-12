package com.aplayer.util;


public class CMDExcutor {

	private Runtime runTime = Runtime.getRuntime();
	private Process process = null;
	
	public void execute(String cmdCode){
		 String[] cmd = new String[] {"cmd.exe","/K",cmdCode};
		try {
			process = runTime.exec(cmd);
			
			new Thread(new StreamDrainer(process.getInputStream())).start();
            new Thread(new StreamDrainer(process.getErrorStream())).start();
            process.getOutputStream().close();

//            int exitValue = process.waitFor();
//            System.out.println("∑µªÿ÷µ£∫" + exitValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void execute(String[] codes){
		for(int i=0;i<codes.length;i++){
			this.execute(codes[i]);
		}
	}
	
	public void destoryProcess(){
		if(null!=process)process.destroy();
	}

	public Runtime getRunTime() {
		return runTime;
	}

	public Process getProcess() {
		return process;
	}
	
	
}

/*
 * ==============================================
 * ks ±à¼­Æ÷
 * ==============================================
 *
 * Project Info: ks ±à¼­Æ÷;
 *
 */

package org.ksb;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import org.ksb.frame.MainFrame;
import org.ksb.util.IdePrintStream;
import org.ksb.util.KsTool;

/**
 * IDEÖ÷Àà.
 */
public class KsbAppMain {
	
	/**
	 * Ö÷º¯Êý.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		UIUtils.setPreferredLookAndFeel();  
		JFrame.setDefaultLookAndFeelDecorated(true); 
		JDialog.setDefaultLookAndFeelDecorated(true);
		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {  
	    public void run() {
	    	try {
	    		KsTool.setRunType(KsTool.RUN_COMMAND);
					MainFrame mainFrame = new MainFrame();
					// ByteArrayOutputStream bos = new ByteArrayOutputStream();
					IdePrintStream ips = new IdePrintStream(System.out, mainFrame);
					System.setOut(ips);
					mainFrame.setVisible(true);
	      } catch (Exception e) {
					e.printStackTrace();
				}
    	}  
		});  
		NativeInterface.runEventPump();
	}
	
}

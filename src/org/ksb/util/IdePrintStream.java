/*
 * ==============================================
 * ks ±à¼­Æ÷
 * ==============================================
 *
 * Project Info: ks ±à¼­Æ÷;
 *
 */

package org.ksb.util;

import java.io.PrintStream;

import org.ksb.frame.MainFrame;

/**
 * ks ideÆ½Ì¨´òÓ¡.
 *
 */
public class IdePrintStream extends PrintStream {
	public MainFrame mf;
	private PrintStream out;
	
	public IdePrintStream(PrintStream out, MainFrame mf) {
    super(out);
    this.mf = mf;
    this.out = out;
  }

	@Override
	public void println(String msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(Object msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(int msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(long msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(float msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(double msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
	
	@Override
	public void println(boolean msg) {
		mf.txtConsole.append("\n" + msg);
		mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());
		out.println(msg);
	}
}

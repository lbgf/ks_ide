/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
 *
 */

package org.ksb.util;

import java.util.LinkedList;

/**
 * �����ȳ�����
 *
 */
public class IdeQueue {
	private LinkedList list = new LinkedList();

	public void put(Object t) {
		list.addFirst(t);
	}

	public Object get() {
		return list.removeLast();
	}

	public boolean isEmpt() {
		return list.isEmpty();
	}

	public int getSize() {
		return list.size();
	}
}
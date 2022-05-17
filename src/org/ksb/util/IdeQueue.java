/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.ksb.util;

import java.util.LinkedList;

/**
 * 先入先出队列
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
/*
 * Source code of the book of Thinking in Java Component Design
 * ����������Java ������
 * ����: �׵���
 * Email: kshark2008@gmail.com
 * Date: 2008-12
 * Copyright 2008-2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kongdesheng_component_thread;

import java.util.*;

public class Queue {
	// 队列的最大长度
	private int queueMaxLength = Integer.MAX_VALUE;
	// 用LinkedList存放队列元素
	private LinkedList list = new LinkedList();
	
	public Queue() {
	}
	
	public Queue(int queueMaxLength) {
		this.queueMaxLength = queueMaxLength;
	}
	
	public int getQueueMaxLength() {
		return queueMaxLength;
	}

	public void setQueueMaxLength(int queueMaxLength) {
		this.queueMaxLength = queueMaxLength;
	}

	// 获得队列中当前的元素数量
	public int getQueueSize() {
		return list.size();
	}

	// 把对象放入队列尾部，返回true为成功，返回false表示队列已满无法放入
	public synchronized void put(Object obj) {
		while(list.size()>=queueMaxLength){
			waitWhen(500);
		}
		list.add(obj);
		notifyAll();
	}

	// 把对象放入队列头部，返回true为成功，返回false表示队列已满无法放入
	public synchronized void putHead(Object obj) {
		while(list.size()>=queueMaxLength){
			waitWhen(-1);
		}
		list.addFirst(obj);
		notifyAll();
	}

	// 获取队列的头部元素，并从队列中移除。如果队列为空，则会一直阻塞
	public synchronized Object poll() {
		while(list.size()<1){
			waitWhen(-1);
		}
		notifyAll();
		return list.poll();
	}

	// 获取队列的头部元素，并从队列中移除。如果队列为空，则会阻塞,直至超时
	// timeout : 超时时间，单位毫秒。如果为0，表示不阻塞等待，直接返回。
	public synchronized Object poll(int timeout) {
		while(list.size()<1){
			waitWhen(timeout);
		}
		notifyAll();
		return list.poll();
	}

	// 获取队列的头部元素，不从队列中移除。如果队列为空，则会一直阻塞
	public synchronized Object peek() {
		while(list.size()<1){
			waitWhen(-1);
		}
		notifyAll();
		return list.peek();
	}

	// 获取队列的头部元素，不从队列中移除。如果队列为空，则会阻塞,直至超时
	// timeout : 超时时间，单位毫秒。如果为0，表示不阻塞等待，直接返回。
	public synchronized Object peek(int timeout) {
		while(list.size()<1){
			waitWhen(timeout);
		}
		notifyAll();
		return list.peek();
	}

	// 等待
	private void waitWhen(int timeout) {
		try {
			if (timeout < 0)		// 小于0，表示无限等下去
				wait();
			else if (timeout > 0)	// 大于0时，只等待指定的时间. timeout=0时不等待
				wait(timeout);
		} catch (Exception e) {}
	}
}


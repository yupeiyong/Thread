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

public class SynchronizeDemo {
	public static void main(String[] args) {
		Queue queue = new Queue(20);
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		
		producer.start();
		consumer.start();
		
		BaseThread.sleep(10000);
		
		producer.stopThread();
		consumer.stopThread();
		try {
			producer.join(20000);
			consumer.join(20000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

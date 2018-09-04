

package kongdesheng_component_thread;

public class Producer extends BaseThread{
	private Queue queue;
	private int productNo = 0;

    public Producer(Queue queue) {
		this.queue = queue;
	}
	
	public void run() {
		while (canRun()) {
            Integer product = newProduct();
            System.out.println("Produce product, id = " + product);
            queue.put(product);
            Thread.yield();
        }
	}

	private Integer newProduct() {
		Integer product = new Integer(++productNo);
		sleep(100);
		return product;
	}
}


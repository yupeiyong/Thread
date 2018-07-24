package login.first.yong.thread;

import java.util.LinkedList;

//生产者消费者模式
public class Produce {
    public static void main(String []args){
        Queue queue=new Queue();
        queue.set_queueMaxLength(5);
        ProduceThread producer=new ProduceThread(queue);
        producer.start();
        ConsumerThread consumer=new ConsumerThread(queue);
        consumer.start();

        try{
            Thread.sleep(10000);
            producer.stopThred();
            consumer.stopThred();

            producer.join();
            consumer.join();

            System.out.println("结束");
        }
       catch (InterruptedException e){
            e.printStackTrace();
       }

    }
}

class Queue{
    private int _queueMaxLength=Integer.MAX_VALUE;

    private LinkedList list=new LinkedList();

    public void set_queueMaxLength(int maxLength){
        this._queueMaxLength=maxLength;
    }

    public int get_queueMaxLength(){
        return _queueMaxLength;
    }

    public synchronized boolean put(Object value){
        //System.out.println("放入队列时数量："+list.size());
        while(list.size()>=_queueMaxLength){
            try{
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        list.add(value);
        this.notify();
        return true;
    }

    public synchronized Object poll(){
        //System.out.println("取出队列时数量："+list.size());
        while(list.size()<=0){
            try{
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Object value=list.poll();
        this.notify();
        return value;
    }
}

class ProduceThread extends Thread{
    private Queue queue;
    private boolean canRun=true;
    public void stopThred(){
        canRun=false;
    }
    private int productNo=0;
    public ProduceThread(Queue q){
        this.queue=q;
        System.out.println("生产者线程结束");
    }
    public void run(){
        while (canRun){
            ++productNo;
            System.out.println("生产者："+productNo);
            queue.put(productNo);
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class ConsumerThread extends Thread{
    private Queue queue;
    public ConsumerThread(Queue q){
        this.queue=q;
    }
    private boolean canRun=true;
    public void stopThred(){
        canRun=false;
        System.out.println("消费者线程结束");
    }
    public void run(){
        while(canRun){
            Object value=queue.poll();
            System.out.println("消费者："+value);
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

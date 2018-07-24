package login.first.yong.thread;

public class PublicBox {
    private int count=0;
    public synchronized void increace(){
        while(count>=5){
            try{
                System. out .println("生成时等待");
                //this.wait();
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        count++;
        System. out .println("生成苹果成功！现有苹果"+count );
        notify();
    }

    public synchronized int decreace(){
        while(count<=0){
            try{
                System. out .println("消费时等待");
                //this.wait();
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        count--;
        System. out .println("消费苹果成功！现有苹果"+count );
        notify();
        return count;
    }

    public static void main(String []args){
        PublicBox box=new PublicBox();
        Producer producer=new Producer(box);
        Consumer consumer=new Consumer(box);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Producer implements Runnable{
    private PublicBox box;
    public Producer(PublicBox box){
        this.box=box;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try{
                Thread.sleep(1000);
                System. out .println("Pro: i " +i);
                box.increace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Consumer  implements Runnable{
    private PublicBox box;
    public Consumer(PublicBox box){
        this.box=box;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try{
                Thread.sleep(3000);
                System. out .println("Con: i " +i);
                box.decreace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

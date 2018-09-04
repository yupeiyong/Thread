package login.first.yong.thread;

public class Notify {
    public static void main(String[]args){
        final Store store=new Store();
        Thread t1=new Thread(){
            public void run(){
                for(int i=0;i<20;i++){
                    System.out.println("生产产品："+i);
                    store.produce(i);
                }
            }
        };
        Thread t2=new Thread(){
            public void run(){
                for(int i=0;i<20;i++){
                    int value=store.consume();
                    System.out.println("消费产品：----"+value);
                }
            }
        };
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
class Store{
    private int value;
    //表示可用缓冲
    private  int s1=1;
    //表示生产产品数量
    private int s2=0;
    public void produce(int value){
        synchronized (this){
            s1--;
            while(s1<0){
                try{
                    this.wait();
                    System.out.println("收到通知");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            this.value=value;
            s2++;
            if(s2<=0){
                this.notifyAll();
                System.out.println("生产产品通知");

                try{
                    Thread.sleep(2000);
                    System.out.println("生产产品通知后休眠");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int consume(){
        synchronized (this){
            s2--;
            while(s2<0){
                try{
                    this.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            int product=value;
            value=0;
            s1++;
            if(s1<=0){
                this.notifyAll();
                System.out.println("消费产品通知");

                try{
                    Thread.sleep(2000);
                    System.out.println("消费产品通知后休眠");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return product;
        }
    }
}

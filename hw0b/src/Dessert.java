public class Dessert {
    public int flavor;
    public int price;
    public static int numDesserts=0;
    public Dessert(int flavor,int price){//注意，构造器没有方法类型，不能写void
        this.flavor=flavor;
        this.price=price;
        numDesserts=numDesserts+1;
        return;
    }
    public void printDessert(){
        System.out.println(this.flavor+" "+this.price+" "+Dessert.numDesserts);
        return;
    }
    public static void main(String[]args){
        System.out.println("I love dessert!");
    }
}

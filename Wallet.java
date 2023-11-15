public class Wallet {
    private int money = 100 ;
    private int speed = 0 ;
    private int level = 1 ;

    public Wallet(){
        speed = 100 ;
    }

    //set method
    public void setMoney(int inputMoney){
        this.money = inputMoney ;
    }
    public void setSpeed(int inputSpeed){
        this.speed = inputSpeed ;
    }

    //get method
    public int getMoney(){
        return this.money ;
    }
    public int getSpeed(){
        return this.speed ;
    }
    public int getLevel(){
        return this.level ;
    }

    
    public void spend(int price){
        this.money -= price ;
    }
    public void makeMoney(){
        this.money += this.speed ;
    }
    public void addMoney(int newMoney){
        this.money += newMoney ;
    }
    public void accelerate(){
        this.speed += 50 ;
        this.level += 1 ;
    }
}

public class Role{
    protected int ID ;
    protected String name = "" ;
    protected int blood ;
    protected int speed ;
    protected int attackPower ;
    protected int life ;
    protected int price ;
    protected int location ;
    protected int age ; 
    protected String fileName = "" ;
    
    public Role(int inputID, String inputName, int inputBlood, int inputSpeed, int inputAttackPower, int inputLife, int inputPrice, String inputFileName, int inputMapID)
    {
        this.ID = inputID ;
        this.name = inputName ; 
        this.blood = inputBlood ;
        this.speed = inputSpeed ;
        this.attackPower = inputAttackPower ;
        this.life = inputLife ;
        this.price = inputPrice ;
        this.fileName = inputFileName ;
        this.location = 0 ;
        this.age = 0 ;
    }
    
    //set method
    public void setID(int i){
        this.ID = i ;
    }
    public void setName(String s){
        this.name = s ;
    }
    public void setBlood(int i){
        this.blood = i ;
    }
    public void setSpeed(int i){
        this.speed = i ;
    }
    public void setAttackPower(int i){
        this.attackPower = i ;
    }
    public void setLife(int i){
        this.life = i ;
    }
    public void setPrice(int i){
        this.price = i ;
    }
    public void setLocation(int i){
        this.location = i ;
    }
    public void setFileName(String i){
        this.fileName = i ;
    }

    //get method
    public int getID(){
        return this.ID ;
    }
    public String getName(){
        return this.name ;
    }
    public int getBlood(){
        return this.blood ;
    }
    public int getSpeed(){
        return this.speed ;
    }
    public int getAttackPower(){
        return this.attackPower ;
    }
    public int getLife(){
        return this.life ;
    }
    public int getPrice(){
        return this.price ;
    }
    public int getLocation(){
        return this.location ;
    }
    public int getAge(){
        return this.age ;
    }
    public String getFileName(){
        return this.fileName ;
    }

    //move 
    public void move(){
        this.location += this.speed ;
    }

    //attack 
    public int attack(){
        return this.attackPower ;
    }

    //be attacked
    public void beAttacked(int enemyAttackPower){
        this.blood -= enemyAttackPower ;
    }

}
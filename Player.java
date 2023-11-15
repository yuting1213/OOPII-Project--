import java.util.ArrayList;

public class Player {

    private ArrayList<PlayerRole> armymember = new ArrayList<PlayerRole>();
    private ArrayList<PlayerRole> movingArmy = new ArrayList<PlayerRole>();
    private int warfrontLocation = 0 ;
    private PlayerRole castle ;
    private int mapID = 0 ;
    private int enemyWarfrontLocation = 10;

    public Player(int mapID){
        castle = new PlayerRole(0, "castle", 2000, 0, 0, 10000, 0, "101.png", 1) ; // speed = 0
    }

    public void setArmymember(ArrayList<PlayerRole> inputArmymember){
        this.armymember = inputArmymember ;
    }
    public void setMovingArmy(ArrayList<PlayerRole> inputMovingArmy){
        this.movingArmy = inputMovingArmy ;
    }    
    public void setWarfrontLocation(int inputWarfrontLocation){
        this.warfrontLocation = inputWarfrontLocation ; 
    }
    public void setCastle(PlayerRole inputCastle){
        this.castle = inputCastle ;
    }

    public ArrayList<PlayerRole> getArmymember(){
        return this.armymember ;
    }
    public ArrayList<PlayerRole> getMovingArmy(){
        return this.movingArmy ;
    }
    public int getWarfrontLocation(){
        return this.warfrontLocation ;
    }
    public PlayerRole getCastle(){
        return this.castle ;
    }

    public void call(int newAddRoleID){
        for(PlayerRole role : armymember){
            if ( role.ID == newAddRoleID ){
                PlayerRole newRole = new PlayerRole(role.getID(), role.getName(), role.getBlood(), role.getSpeed(), role.getAttackPower(), role.getLife(), role.getPrice(), role.getFileName(), mapID);
                this.movingArmy.add(newRole);
            }
        }
    }
    
    public void move(int enemyWarfrontLocation){
    
        this.enemyWarfrontLocation = enemyWarfrontLocation ;
        for ( PlayerRole role : this.movingArmy ){
            if ( role.getLocation() < this.enemyWarfrontLocation )
                role.move();
        }
        judgeWarfrontlocation();
    }

    public int fight(){
        int allAttackPower = 0 ;
        for ( PlayerRole role : this.movingArmy ){
            if ( role.getLocation() == this.enemyWarfrontLocation  )
                allAttackPower += role.attack() ;
        }
        return allAttackPower ;
    }

    public void beattacked(int beattackedPower){

        int eachBeattackerPower = beattackedPower ;
        for ( PlayerRole role : movingArmy ){
            if ( role.getLocation() == this.enemyWarfrontLocation ) 
                role.blood -= eachBeattackerPower ;
        }
        cleanArmy();
    }

    public void castleBeattacked(int beattackedPower){
        int blood = this.castle.getBlood() ;
        blood -= beattackedPower ;
        this.castle.setBlood(blood);
    }

    //to clean the died role
    public void cleanArmy(){

        for ( int i = 0 ; i < this.movingArmy.size() ; i++){
            if ( this.movingArmy.get(i).getBlood() <= 0 ){
                this.movingArmy.remove(i);
            }
        }        
    }
    //to judge warfrontLocation
    public void judgeWarfrontlocation(){
        this.warfrontLocation = 0 ;
        for ( PlayerRole role : this.movingArmy ){
            if ( role.location > this.warfrontLocation )
                this.warfrontLocation = role.location ;
        }
    }

}

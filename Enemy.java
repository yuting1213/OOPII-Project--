import java.util.ArrayList;

public class Enemy {

    private ArrayList<EnemyRole> armymember =new ArrayList<EnemyRole>();
    private ArrayList<EnemyRole> movingArmy = new ArrayList<EnemyRole>();
    private int warfrontLocation = 10 ;
    private EnemyRole castle ;
    private int playerWarfrontLocation = 0 ;
    private int mapID = 0 ;

    public Enemy(int mapID){
        this.castle = new EnemyRole(0, "castle", 2000, 0, 0, 10000, 0, "pyramid.png", 1) ;
    }

    public void setArmymember(ArrayList<EnemyRole> inputArmymember){
        this.armymember = inputArmymember ;
    }
    public void setMovingArmy(ArrayList<EnemyRole> inputMovingArmy){
        this.movingArmy = inputMovingArmy ;
    }    
    public void setWarfrontLocation(int inputWarfrontLocation){
        this.warfrontLocation = inputWarfrontLocation ; 
    }
    public void setCastle(EnemyRole inputCastle){
        this.castle = inputCastle ;
    }

    public ArrayList<EnemyRole> getArmymember(){
        return this.armymember ;
    }
    public ArrayList<EnemyRole> getMovingArmy(){
        return this.movingArmy ;
    }
    public int getWarfrontLocation(){
        return this.warfrontLocation ;
    }
    public EnemyRole getCastle(){
        return this.castle ;
    }

    public void call(int newAddRoleID){
        for(EnemyRole role : armymember){
            if ( role.ID == newAddRoleID ){
                EnemyRole newRole = new EnemyRole(role.getID(), role.getName(), role.getBlood(), role.getSpeed(), role.getAttackPower(), role.getLife(), role.getPrice(), role.getFileName(), mapID);
                this.movingArmy.add(newRole);
            }
        }
    }
    
    public void move(int playerWarfrontLocation){
        //move
        this.playerWarfrontLocation = playerWarfrontLocation ;
        for ( EnemyRole role : this.movingArmy ){
            if ( role.getLocation() > this.playerWarfrontLocation )
                role.move();
        }
        judgeWarfrontlocation();
    }

    public int fight(){
        int allAttackPower = 0 ;
        for ( Role role : this.movingArmy ){
        if ( role.getLocation() == this.playerWarfrontLocation  )
            allAttackPower += role.attack() ;
        }

        return allAttackPower ;
    }

    public int beattacked(int beattackedPower){

        int reward = 0 ;
        int eachBeAttackerPower = beattackedPower ;
        for( EnemyRole role : movingArmy ){
            if ( role.getLocation() == this.playerWarfrontLocation ) 
                role.blood -= eachBeAttackerPower ;
        }
        reward = cleanArmy();
        
        return reward ;
    }

    public void castleBeattacked(int beattackedPower){
        int blood = this.castle.getBlood() ;
        blood -= beattackedPower ;
        this.castle.setBlood(blood);
    }

    //to clean the died role
    public int cleanArmy(){
        int reward = 0 ;
        for ( int i = 0 ; i < this.movingArmy.size() ; i++ ){
            EnemyRole role = this.movingArmy.get(i) ;
            if ( role.getBlood() <= 0 ){
                reward += role.getPrice() ;
                this.movingArmy.remove(i);
            }
        }
        return reward ;
    }

    //to judge warfrontLocation
    public int judgeWarfrontlocation(){
        this.warfrontLocation = 10 ;
        for ( EnemyRole role : this.movingArmy ){
            if ( role.location < this.warfrontLocation )
                this.warfrontLocation = role.location ;
        }
        return this.warfrontLocation ;
    }

    public void beusedLocationSkill(){
        for ( int i = 0 ; i < movingArmy.size() ; i ++ ){
            movingArmy.get(i).location += 2 ;
        }
    }

    public void beuseAttcckSkill(){
        for ( int i = 0 ; i < movingArmy.size() ; i ++ ){
            movingArmy.get(i).blood -= 300 ;
        }
    }
}

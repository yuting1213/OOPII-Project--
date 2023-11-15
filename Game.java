import java.util.Random;

public class Game {

    Enemy enemy ;
    Player player ; 
    int mapID = 1 ;
    Wallet wallet ;
    Random random = new Random() ;
    boolean isFinished = false ; // to judge if the fight is finished
    boolean toCallEnemy = true ;//to judge if the round is odd/even, if the round is odd, call new enemy role
    boolean isArrived = false ;// to judge if one army arrive castle
    String[] enemyMemberStingArray ;
    InfoFileReader readEnemyInfo ;
    InfoFileReader readPlayerInfo ; 

    public Game(String playerArmy,int mapID){

        this.mapID = mapID ;
        this.wallet = new Wallet();
        this.enemy = new Enemy(mapID);
        this.player = new Player(mapID);
        

        //get the armymembers of player and enemy 
        //get enemy member
        readEnemyInfo = new InfoFileReader(mapID);
        enemy.setArmymember( readEnemyInfo.getEnemyMemberList() );
        //get player member
        readPlayerInfo = new InfoFileReader(mapID, playerArmy);
        player.setArmymember( readPlayerInfo.getPlayerMemberList() );

        enemyMemberStingArray = readEnemyInfo.getMembeListString().split(";") ;
    }
    public void callRole(int index){
        PlayerRole role = player.getArmymember().get(index) ;
        if ( wallet.getMoney() >= role.getPrice()){
            player.call(role.getID());
            wallet.spend(role.getPrice());
        }
    }
    public boolean run(){
        /*call new enemy role */
        //(odd round =>call new enemy role,even round => don't call new enemy role)
        if ( toCallEnemy ){
            int enemyIndex = random.nextInt(enemy.getArmymember().size());
            enemy.call(Integer.valueOf( enemyMemberStingArray[enemyIndex]) );
            toCallEnemy = false ;
        }   
        else 
            toCallEnemy = true ;

        /*judge if one army is arrived castle*/
        if ( !isArrived ){
            if ( player.getWarfrontLocation() == 10 || enemy.getWarfrontLocation() == 0 ){
                isArrived = true ;
            }
        }
        
        /*move*/
        int enemyWarfrontLocation = enemy.getWarfrontLocation() ;
        player.move(enemyWarfrontLocation);
        int playerWarfrontLocation = player.getWarfrontLocation() ;
        enemy.move(playerWarfrontLocation);

        /*attack*/
        int playerBeAttackPower = enemy.fight() ;
        int enemyBeAttackPower = player.fight() ;
 
        //if one army arrived castle => attack castle
        if ( isArrived ){
            if ( enemyWarfrontLocation == 0 )
                player.castleBeattacked(playerBeAttackPower);
            else if ( playerWarfrontLocation == 10 )
                enemy.castleBeattacked(enemyBeAttackPower);
        }else{
            //count player's be attacked power
            if ( player.getMovingArmy().size() != 0 )
                playerBeAttackPower = enemy.fight()/player.getMovingArmy().size() ;
            //count enemy's be attacked power
            if ( enemy.getMovingArmy().size() != 0 )
                enemyBeAttackPower = player.fight()/enemy.getMovingArmy().size() ;
            int reward = enemy.beattacked(enemyBeAttackPower) ;
            player.beattacked(playerBeAttackPower);

            wallet.addMoney(reward);
        }
        
        //judge if one army's castle is dead
        if ( player.getCastle().getBlood() <=0 || enemy.getCastle().getBlood() <= 0 ){
            isFinished = true ;
        }
        wallet.makeMoney();

        return isFinished ;
    }
    public void useAttackSkill(){
        wallet.spend(1000);
        enemy.beuseAttcckSkill();
    }
    public void useLocationSkill(){
        wallet.spend(500);
        enemy.beusedLocationSkill();
    }
    public void accelerateWallet(){
        wallet.spend(200);
        wallet.accelerate();
    }
}

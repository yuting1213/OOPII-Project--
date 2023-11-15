public class EnemyRole extends Role{
    public EnemyRole(int inputID, String inputName, int inputBlood, int inputSpeed, int inputAttackPower, int inputLife, int inputPrice, String inputFileName, int inputMapSize){
        super(inputID, inputName, inputBlood, inputSpeed, inputAttackPower, inputLife, inputPrice, inputFileName, inputMapSize);
        super.location = 10 ;
    }

    @Override
    public void move(){
        super.location -= super.speed ;
    }
}
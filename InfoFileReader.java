import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.ArrayList;

public class InfoFileReader {

    private int mapID = 1 ;
    private int mapSize ;
    private String memberListString = "" ;
    private ArrayList<PlayerRole> playerMemberList = new ArrayList<PlayerRole>();
    private ArrayList<EnemyRole> enemyMemberList = new ArrayList<EnemyRole>();
    private ArrayList<String> animalInfo = new ArrayList<>();

    public InfoFileReader(int mapID){
        this.mapID = mapID ;
        this.readMapFile(mapID);
        this.readAnimalFile();
    }

    public InfoFileReader(int mapID, String memberListString){
        this.mapID = mapID ;
        this.readAnimalFile(memberListString);
    }

    public InfoFileReader(){
        this.readAnimalInfo("1;2;3;4;5;6;7;8;9;10;11;12");
    }
    
    //get method
    public int getMapID(){
        return this.mapID ;
    }
    public int getMapSize(){
        return this.mapSize ;
    }
    public String getMembeListString(){
        return this.memberListString ;
    }
    public ArrayList<PlayerRole> getPlayerMemberList(){
        return this.playerMemberList ;
    }
    public ArrayList<EnemyRole> getEnemyMemberList(){
        return this.enemyMemberList ;
    }
    public ArrayList<String> getAnimalInfo(){
        return this.animalInfo;
    }

    //set method
    public void setMapID(int mapID){
        this.mapID = mapID ;
    }
    public void setMapSize(int mapSize){
        this.mapSize = mapSize ;
    }
    public void setMemberListString(String memberListString){
        this.memberListString = memberListString ;
    }
    public void setPlayerMemberList(ArrayList<PlayerRole> playerMemberList){
        this.playerMemberList = playerMemberList ;
    }
    public void setEnemyMemberList(ArrayList<EnemyRole> enemyMemberList){
        this.enemyMemberList = enemyMemberList ;
    }
    public void setAnimalInfo(ArrayList<String> animalInfo){
        this.animalInfo = animalInfo ;
    }


    public void readMapFile(int mapID){
    
        try{
            File file = new File("info/map_info.csv") ;

            BufferedReader br = new BufferedReader(new FileReader(file)) ;
            String line = br.readLine() ; //read the first row(ID,habitat,enemey_ID,...)
                    
            while( (line = br.readLine()) != null ){
                String[] eachInfo = line.split(",") ;
                if ( Integer.valueOf(eachInfo[0]) == mapID ){
                    this.memberListString = eachInfo[2] ; 
                }
            }
            br.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    //to get the player's role list
    public void readAnimalFile(String inputMemberListString){

        String[] animalListArray = inputMemberListString.split(";");

        try{
            File file = new File("info/Animals_info.csv") ;
            if ( !file.exists() ){
                System.exit(0);
            }
            BufferedReader br = new BufferedReader(new FileReader(file)) ;
            String line = br.readLine() ; //read the first row(name,ID,habitat,...)
                    
            while( (line = br.readLine()) != null ){
                    
                String[] eachInfo = line.split(",") ;
                for( String animalID : animalListArray ){
                    if ( eachInfo[1].equals(animalID) ){
                        PlayerRole eachAnimalInfo = new PlayerRole(Integer.valueOf(eachInfo[1]), eachInfo[0], Integer.valueOf(eachInfo[7]), Integer.valueOf(eachInfo[5]), Integer.valueOf(eachInfo[9]), Integer.valueOf(eachInfo[9]), Integer.valueOf(eachInfo[12]), eachInfo[11], mapID);
                        this.playerMemberList.add(eachAnimalInfo);
                    }
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    //to get the enemy's role list
    public void readAnimalFile(){

        String[] animalListArray = this.memberListString.split(";");

        try{
            File file = new File("info/Animals_info.csv") ;
            if ( !file.exists() ){
                System.exit(0);
            }
            BufferedReader br = new BufferedReader(new FileReader(file)) ;
            String line = br.readLine() ; //read the first row(name,ID,habitat,...)
                    
            while( (line = br.readLine()) != null ){
                    
                String[] eachInfo = line.split(",") ;
                for( String animalID : animalListArray ){
                    if ( eachInfo[1].equals(animalID) ){
                        EnemyRole eachAnimalInfo = new EnemyRole(Integer.valueOf(eachInfo[1]), eachInfo[0], Integer.valueOf(eachInfo[7]), Integer.valueOf(eachInfo[5]), Integer.valueOf(eachInfo[9]), Integer.valueOf(eachInfo[9]), Integer.valueOf(eachInfo[12]), eachInfo[11], mapID);
                        this.enemyMemberList.add(eachAnimalInfo);
                    }
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void readAnimalInfo(String ID){
        String[] animalListArray = ID.split(";");
        try{
            File file = new File("info/Animals_info.csv") ;
            if ( !file.exists() ){
                System.exit(0);
            }
            BufferedReader br = new BufferedReader(new FileReader(file)) ;
            String line = br.readLine() ; //read the first row(name,ID,habitat,...)
                    
            while( (line = br.readLine()) != null ){
                for(String animalID : animalListArray )
                {
                    String[] eachInfo = line.split("\"");
                    String[] name= eachInfo[0].split(",") ;
                    if(name[1].equals(animalID))
                    {
                        animalInfo.add(eachInfo[3]);
                    }
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

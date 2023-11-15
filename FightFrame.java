import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

public class FightFrame extends JFrame implements ActionListener{
    Game game ;

    boolean exit = false;
    boolean ready = false;

    JPanel topPanel;
    JPanel midPanel ;
    JPanel buttonPanel;
    JPanel playerCastlePanel;
    JPanel enemyCastlePanel;

    JButton dontCallRoleButton ;
    JButton[] buttons ;
    JButton attackButton ;
    JButton locationButton ;
    JButton accelerateWalletButton ;

    ArrayList<Integer> animalLocation  = new ArrayList<>();
    HashMap<Integer,Image> animalImg = new HashMap<>();
    HashMap<Integer, Icon> animalImageMap = new HashMap<Integer, Icon>(); 

    JLabel topLabel ;
    Object button;
    int mapID = 1 ;
    int count = 0 ;
    String mapLevel ;
    String money;
    String enemyCastleBlood;
    String playerCastleBlood;
    String textString;

    Image playerCastleImg = new ImageIcon("picture/101.png").getImage();
    Image enemyCastleImg = new ImageIcon("picture/pyramid.png").getImage();
    Icon readyImage = new ImageIcon(getClass().getResource("picture/ready.png"));
    Image fightImage = new ImageIcon("picture/Fight.png").getImage();

    MusicPlay BGMPlay ;
    MusicPlay musicPlay = new MusicPlay("music/ReadyFight!.wav",false);

    ArrangeFrame arrangeFrame ;
    MapFrame mapFrame ;

    public FightFrame(String playerArmy,int mapID,ArrangeFrame arrangeFrame,MapFrame mapFrame){
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 560);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.mapID = mapID ;
        this.arrangeFrame = arrangeFrame ;
        this.mapFrame = mapFrame ;

        musicPlay.startMusic();
        game = new Game(playerArmy,mapID) ;
        buttons = new JButton[game.player.getArmymember().size()+1];
        BGMPlay = new MusicPlay("music/BGM2.wav",false);
        BGMPlay.startMusic();

        Icon walletImg = new ImageIcon(getClass().getResource("picture/wallet.png"));
        
        money = "<p>Lv."+game.wallet.getLevel()+"</p>Wallet:"+game.wallet.getMoney();
        enemyCastleBlood = "Castle blood:"+game.enemy.getCastle().getBlood() ;
        playerCastleBlood = "Castle blood:"+game.player.getCastle().getBlood() ;
        textString = "<html><p></p><p></p>"+money+"<p></p>"+playerCastleBlood+"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp "+enemyCastleBlood+"</html>";
        topLabel = new JLabel(textString);
        topLabel.setIcon(walletImg);
        this.add(topLabel, BorderLayout.NORTH);

        BackgroundPanel backgroundPanel = new BackgroundPanel((new ImageIcon("picture/Background"+mapID+".jpg")).getImage());
        backgroundPanel.setBounds(0,0,2400,365);
        this.add(backgroundPanel);


        midPanel = new JPanel();
        midPanel.setOpaque(false);
        midPanel.setSize(1000,200);
        midPanel.setLayout(new BorderLayout());

        JPanel castlePanel = new JPanel();
        castlePanel.setLayout(new BorderLayout());
        //print castle picture and blood
        //print player's
        JLabel playerCastleLabel = new JLabel("Castle blood:"+String.valueOf(playerCastleBlood));
        castlePanel.add(playerCastleLabel,BorderLayout.WEST);
        //print enemy's
        JLabel enemyCastleLabel = new JLabel("Castle blood:"+String.valueOf(enemyCastleBlood));
        castlePanel.add(enemyCastleLabel,BorderLayout.EAST);
        midPanel.add(castlePanel,BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);

        //button panel
        buttonPanel = new JPanel() ;
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.setBackground(Color.BLACK);
        this.add(buttonPanel, BorderLayout.SOUTH);

        JPanel skillButtonPanel = new JPanel() ;
        skillButtonPanel.setOpaque(false);
        skillButtonPanel.setLayout(new FlowLayout());
        buttonPanel.add(skillButtonPanel);

        attackButton = new JButton("Attack Skill: $ 1000") ;
        attackButton.setOpaque(false);
        attackButton.setPreferredSize(new Dimension(160,50));
        attackButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if ( game.wallet.getMoney() >= 1000 )
                {
                    game.useAttackSkill();
                    run();
                }
            }
        });
        skillButtonPanel.add(attackButton);

        locationButton = new JButton("Location Skill: $500") ;
        locationButton.setOpaque(false);
        locationButton.setPreferredSize(new Dimension(160,50));
        locationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if ( game.wallet.getMoney() >= 500 )
                {
                    game.useLocationSkill();
                    run();
                }
            }
        });
        skillButtonPanel.add(locationButton);

        accelerateWalletButton = new JButton("Accelerate Wallet: $200") ;
        accelerateWalletButton.setOpaque(false);
        accelerateWalletButton.setPreferredSize(new Dimension(250,50));
        accelerateWalletButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if ( game.wallet.getLevel() != 6 ){
                    game.accelerateWallet();
                    run();
                }
            }
        });
        skillButtonPanel.add(accelerateWalletButton);


        JPanel roleButtonPanel = new JPanel() ;
        roleButtonPanel.setOpaque(false);
        roleButtonPanel.setLayout(new FlowLayout());
        buttonPanel.add(roleButtonPanel);
        
        for(int i=0;i<game.player.getArmymember().size();i++)
        {
            PlayerRole role = game.player.getArmymember().get(i);
            String roleFileName = role.getFileName();
            ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/"+roleFileName));
            animalImageMap.put(i, roleImage);
            Image image = roleImage.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            roleImage.setImage(image);
            buttons[i] = new JButton("$:"+role.getPrice(),roleImage);
            buttons[i].setOpaque(false);
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(150,70));
            roleButtonPanel.add(buttons[i]);
        }

        dontCallRoleButton = new JButton("Don't want to call");
        dontCallRoleButton.setOpaque(false);
        dontCallRoleButton.setPreferredSize(new Dimension(150,70));
        dontCallRoleButton.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent event){
                run();
            }
        });
        buttons[buttons.length-1] = dontCallRoleButton ;
        roleButtonPanel.add(dontCallRoleButton);

        updateButton(game.wallet.getMoney());
    }

    @Override 
    public void actionPerformed(ActionEvent e){
        button = e.getSource();
        for (int i = 0 ; i < buttons.length ; i++){
            if ( button == buttons[i] ){
                game.callRole(i);
                run();
            }
        }
    }

    public void updateButton(int money){
        if ( money < 1000 )
            attackButton.setEnabled(false);
        else 
            attackButton.setEnabled(true);

        if ( money < 500 )
            locationButton.setEnabled(false);
        else 
            locationButton.setEnabled(true);

        if ( money < 200 || game.wallet.getLevel() == 6 )
            accelerateWalletButton.setEnabled(false);
        else 
            accelerateWalletButton.setEnabled(true);

        for ( int i = 0 ; i < game.player.getArmymember().size() ; i++ ){
            if ( money < game.player.getArmymember().get(i).getPrice() )
                buttons[i].setEnabled(false);
            else
                buttons[i].setEnabled(true);
        }
    }

    public void updateInformation(){
        if ( game.wallet.getLevel() == 6 )
            money = "<p>Lv."+game.wallet.getLevel()+"--MAX--"+"</p>Wallet:"+game.wallet.getMoney();
        else 
            money = "<p>Lv."+game.wallet.getLevel()+"</p>Wallet:"+game.wallet.getMoney();

        enemyCastleBlood = "Castle blood:"+game.enemy.getCastle().getBlood() ;
        playerCastleBlood = "Castle blood:"+game.player.getCastle().getBlood() ;
        textString = "<html><p></p><p></p>"+money+"<p></p>"+playerCastleBlood+"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
            +"&emsp&emsp&emsp&emsp&emsp "+enemyCastleBlood+"</html>";

        topLabel.setText(textString);
        
        updateButton(game.wallet.getMoney());
        repaint();
    }

    public void run(){
        if ( game.run() ){
            endGame();
        }
        else
            updateInformation();
    }

    public void endGame(){
        money = "<p>Lv."+game.wallet.getLevel()+"</p>Wallet:"+game.wallet.getMoney();
        if ( game.player.getCastle().getBlood() <= 0  )
            playerCastleBlood = "Castle blood: 0" ;
        else
            enemyCastleBlood = "Castle blood: 0";
        textString = "<html><p></p><p></p>"+money+"<p></p>"+playerCastleBlood+"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
        +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
        +"&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp"
        +"&emsp&emsp&emsp&emsp&emsp "+enemyCastleBlood+"</html>";

        topLabel.setText(textString);
        for ( JButton button : buttons )
            button.setEnabled(false);
        accelerateWalletButton.setEnabled(false);
        locationButton.setEnabled(false);
        attackButton.setEnabled(false);
        BGMPlay.setStop(true);

        Object[] options = {"again","return map","next stage"};
        int response = JOptionPane.showOptionDialog(this,(game.player.getCastle().getBlood() <= 0 ? "Player Lose... \nChoose your next move.":"Player Win!!!!!\nChoose your next move."),
            "battle result",JOptionPane.PLAIN_MESSAGE,1, null,options,options[0] );
        if(response == 0)
        {
            arrangeFrame.setVisible(true);
            this.setVisible(false);
        }
        else if( response == 1 ){
            mapFrame.setVisible(true);
            this.setVisible(false);
        }else{
            arrangeFrame = new ArrangeFrame(new GameFrame(), mapID+1);
            arrangeFrame.setVisible(true);
            this.setVisible(false);
        }

    }

    public void print(){
        if ( !game.isFinished ){
            //print enemy's information
            System.out.println("Enemy :");
            if ( game.isArrived )
                System.out.println("Enemy castle blood : "+game.enemy.getCastle().getBlood());
            System.out.printf("warfront: %d \n",game.enemy.getWarfrontLocation());
            for(Role role : game.enemy.getMovingArmy() ){
                String s = String.format("%s %d %d %d ||", role.getName(), role.getID(), role.getBlood(), role.getLocation());
                System.out.print(s);
            }
            System.out.println("");
            //print player's information
            System.out.println("Player:");
            if ( game.isArrived )
                System.out.println("Player castle blood : "+game.player.getCastle().getBlood());
            System.out.printf("warfront: %d \n",game.player.getWarfrontLocation());
            for(Role role : game.player.getMovingArmy() ){
                String s = String.format("%s %d %d %d ||", role.getName(), role.getID(), role.getBlood(), role.getLocation());
                System.out.print(s);
            }
            System.out.println("");
            System.out.println("______________________________________");
        }else{

            System.out.println();
            System.out.println("*******************************************");
            if ( game.enemy.getCastle().getBlood() <= 0 ){
                System.out.println("Player WIN!!!!!!!");
            }
            else if ( game.player.getCastle().getBlood() <= 0 ){
                System.out.println("Player LOSE.........");
            }
            System.out.println();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
	    g.drawImage(playerCastleImg, 0, this.getHeight()/2-150,200,250, this);
        g.drawImage(enemyCastleImg, 800, this.getHeight()/2-100, 200, 250, this);
        if(game.enemy.getMovingArmy().size()>0){
            for(int i=0;i<game.enemy.getMovingArmy().size();i++)
            {
                int location = game.enemy.getMovingArmy().get(i).getLocation();
                Image image = Toolkit.getDefaultToolkit().getImage("picture/"+game.enemy.getMovingArmy().get(i).getFileName());
                if(location < game.player.getWarfrontLocation())
                {
                    g.drawImage(image, this.getWidth()/2+80*game.player.getWarfrontLocation()-400, this.getHeight()/2-75,200,200, this);
                }
                else
                    g.drawImage(image, this.getWidth()/2+80*location-400, this.getHeight()/2-75,200,200, this);
            }
        }

        if(game.player.getMovingArmy().size()>0){
            for(int j=0;j<game.player.getMovingArmy().size();j++)
            {
                int location = game.player.getMovingArmy().get(j).getLocation();
                Image image = Toolkit.getDefaultToolkit().getImage("picture/"+game.player.getMovingArmy().get(j).getFileName());
                if(location >= game.enemy.getWarfrontLocation())
                {
                    if(this.getWidth()/2+100*game.enemy.getWarfrontLocation()-600>700)
                        g.drawImage(image, 800, this.getHeight()/2-75,200,200, this);
                    else
                        g.drawImage(image, this.getWidth()/2+100*game.enemy.getWarfrontLocation()-600, this.getHeight()/2-75,200,200, this);
                }
                else if(this.getWidth()/2+100*(location+1)-600>700)
                    g.drawImage(image, 800, this.getHeight()/2-75,200,200, this);
                else
                    g.drawImage(image, this.getWidth()/2+100*(location+1)-600, this.getHeight()/2-75,200,200, this);
            }
        }
        while(!ready){
            JLabel readyLabel = new JLabel(readyImage);
            midPanel.add(readyLabel,BorderLayout.CENTER);
            try{
                Thread.sleep(1000);
                readyLabel.setVisible(false);
                ready = true;
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(!exit){
            g.drawImage(fightImage, this.getWidth()/2-300, this.getHeight()/2-300,600,600, this);
            try {
                Thread.sleep(1000);
                exit = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        musicPlay.setStop(true);
        update(getGraphics());
        //print();
        
    }
    
    public void update(Graphics g){}


    class BackgroundPanel extends JPanel{
        Image image ;
        public BackgroundPanel(Image image){
            this.image = image ;
            this.setOpaque(false);
        }
        public void paintComponent(Graphics g){
            super.paintComponents(g);
            g.drawImage(this.image,0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}


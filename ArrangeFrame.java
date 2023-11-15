import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class ArrangeFrame extends JFrame implements ActionListener{
    int mapID = 1 ;

    InfoFileReader readAllPlayerMember = new InfoFileReader(mapID, "1;2;3;4;5;6;7;8;9;10;11;12");
    ArrayList<PlayerRole> allPlayerMember = readAllPlayerMember.getPlayerMemberList() ;

    InfoFileReader readInfo = new InfoFileReader();
    ArrayList<String> animalInfo = readInfo.getAnimalInfo();

    ArrayList<PlayerRole> playerTeam = new ArrayList<PlayerRole>();

    JPanel northPanel;
    JPanel midPanel;
    JPanel southPanel;
    JPanel eastPanel;
    JPanel buttonPanel;

    JButton[] playerTeamButton;
    JButton[] memberLabel;

    JButton putInButton;
    JButton moreInfoButton;
    JButton infoArea;
    JButton playButton;

    int index = 0;
    int target = 0;
    String playerTeamString = "" ;

    FightFrame fightFrame ;
    GameFrame gameFrame;

    public ArrangeFrame(GameFrame gameFrame,int mapID){
        this.gameFrame = gameFrame;
        this.setTitle("Arrange your team");
        this.setSize(1000,690);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);

        this.mapID = mapID ;

        northPanel = new JPanel();
        northPanel.setOpaque(false);
        northPanel.setPreferredSize(new Dimension(1000,60));
        ImageIcon labelImage = new ImageIcon(getClass().getResource("picture/label.png"));
        Image labelIcon = labelImage.getImage().getScaledInstance(900, 50, Image.SCALE_DEFAULT);
        labelImage.setImage(labelIcon);
        JLabel label = new JLabel(labelImage) ;
        northPanel.add(label);
        this.add(northPanel,BorderLayout.NORTH);

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setPreferredSize(new Dimension(150,800));
        this.add(westPanel,BorderLayout.WEST);

        midPanel = new JPanel();
        midPanel.setOpaque(false);
        midPanel.setPreferredSize(new Dimension(500,400));
        midPanel.setLocation(50, 100);
        midPanel.setLayout(new GridLayout(4,4,20,10));
        memberLabel = new JButton[allPlayerMember.size()];
        for ( int i = 0 ; i < allPlayerMember.size() ; i++ ){
            ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/"+allPlayerMember.get(i).getFileName()));
            Image image = roleImage.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            roleImage.setImage(image);
            memberLabel[i] = new JButton(roleImage);
            memberLabel[i].setBackground(Color.GRAY);
            memberLabel[i].addActionListener(this);
            midPanel.add(memberLabel[i]);
        }
        this.add(midPanel,BorderLayout.CENTER);
        
        eastPanel = new JPanel() ;
        eastPanel.setPreferredSize(new Dimension(300,400));
        eastPanel.setOpaque(false);
        eastPanel.setLayout(new BorderLayout());
        this.add(eastPanel,BorderLayout.EAST);

        infoArea = new JButton() ;
        infoArea.setContentAreaFilled(false);
        infoArea.setOpaque(false);
        infoArea.setBorderPainted(false);
        eastPanel.add(infoArea,BorderLayout.CENTER);

        JPanel gapPanel = new JPanel();
        gapPanel.setOpaque(false);
        gapPanel.setPreferredSize(new Dimension(30,400));
        eastPanel.add(gapPanel,BorderLayout.EAST);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setVisible(false);
        eastPanel.add(buttonPanel,BorderLayout.SOUTH);
        
        //moreInfo button
        moreInfoButton = new JButton();
        moreInfoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moreInfoButton.setContentAreaFilled(false);
        moreInfoButton.setPreferredSize(new Dimension(130,100));
        ImageIcon infoIcon = new ImageIcon(getClass().getResource("picture/moreInfo.png"));
        Image infoImage = infoIcon.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        infoIcon.setImage(infoImage);
        moreInfoButton.setIcon(infoIcon);
        moreInfoButton.setBorder(null);
        moreInfoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == moreInfoButton)
                {
                    String info = animalInfo.get(target);
                    info = info.replaceAll("\\.","\n");
                    JOptionPane.showMessageDialog(ArrangeFrame.this, info,allPlayerMember.get(target).getName(),JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        buttonPanel.add(moreInfoButton);

        //putInto button
        putInButton = new JButton();
        putInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        putInButton.setContentAreaFilled(false);
        putInButton.setPreferredSize(new Dimension(130,100));
        ImageIcon putIcon = new ImageIcon(getClass().getResource("picture/put.png"));
        Image putImage = putIcon.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        putIcon.setImage(putImage);
        putInButton.setIcon(putIcon);
        putInButton.setBorder(null);
        putInButton.addActionListener(this);
        buttonPanel.add(putInButton);
        
        
        southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setLayout(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(900,200));

        JPanel memberPanel = new JPanel();
        memberPanel.setOpaque(false);
        memberPanel.setLayout(new FlowLayout());
        memberPanel.setPreferredSize(new Dimension(800,100));

        playerTeamButton = new JButton[5];
        for(int j=0;j<5;j++)
        {
            playerTeamButton[j] = new JButton();
            playerTeamButton[j].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            playerTeamButton[j].setBackground(Color.DARK_GRAY);
            ImageIcon woodIcon = new ImageIcon(getClass().getResource("picture/title.png"));
            Image woodImage = woodIcon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            woodIcon.setImage(woodImage);
            playerTeamButton[j].setIcon(woodIcon);
            playerTeamButton[j].setEnabled(false);
            playerTeamButton[j].setPreferredSize(new Dimension(150,150));
            memberPanel.add(playerTeamButton[j]);
            playerTeamButton[j].addActionListener(this);
        }
        southPanel.add(memberPanel,BorderLayout.CENTER);

        playButton = new JButton();
        playButton.setPreferredSize(new Dimension(150,60));
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playButton.setContentAreaFilled(false);
        ImageIcon playIcon = new ImageIcon(getClass().getResource("picture/save.png"));
        Image playImage = playIcon.getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT);
        playIcon.setImage(playImage);
        playButton.setIcon(playIcon);
        playButton.setBorder(null);
        playButton.addActionListener(this);
        southPanel.add(playButton,BorderLayout.SOUTH);

        this.add(southPanel,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for ( int i = 0 ; i < allPlayerMember.size() ; i++ ){
            if ( e.getSource() == memberLabel[i] ){
                target = i ;
                ImageIcon woodIcon = new ImageIcon(getClass().getResource("picture/info"+allPlayerMember.get(i).getFileName()));
                Image woodImage = woodIcon.getImage().getScaledInstance(450, 300, Image.SCALE_DEFAULT);
                woodIcon.setImage(woodImage);
                infoArea.setIcon(woodIcon);
                buttonPanel.setVisible(true);
                break;
            }
        }
        if ( e.getSource() == putInButton ){
            infoArea.setIcon(null);
            buttonPanel.setVisible(false);
        
            if(playerTeam.size() == 0)
            {
                playerTeam.add(allPlayerMember.get(target));
                ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/"+allPlayerMember.get(target).getFileName()));
                Image image = roleImage.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                roleImage.setImage(image);
                playerTeamButton[0].setIcon(roleImage);
                playerTeamButton[0].setEnabled(true);
                memberLabel[target].setEnabled(false);
            }
            else{

                if(playerTeam.size() >= 5)
                    JOptionPane.showMessageDialog(ArrangeFrame.this, "Sorry, you can only choose at most 5 animals into your team.");
                else if ( playerTeam.size() <= 0 ){
                    JOptionPane.showMessageDialog(ArrangeFrame.this, "Sorry, you should have at least one role.");
                }
                else{
                    playerTeam.add(allPlayerMember.get(target));
                    ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/"+allPlayerMember.get(target).getFileName()));
                    Image image = roleImage.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                    roleImage.setImage(image);
                    playerTeamButton[playerTeam.size()-1].setIcon(roleImage);
                    playerTeamButton[playerTeam.size()-1].setEnabled(true);
                    memberLabel[target].setEnabled(false);
                }
            }
        }
        else if(e.getSource() == playButton)
        {
            for ( int i = 0 ; i < playerTeam.size() ; i++ ){
                playerTeamString += playerTeam.get(i).getID() ;
                if ( i != playerTeam.size()-1 ){
                    playerTeamString += ";" ;
                }
            }
            gameFrame.getMusicPlay().setStop(true);

            fightFrame = new FightFrame(playerTeamString,mapID,ArrangeFrame.this,new MapFrame(gameFrame)) ;
            this.setVisible(false);
            fightFrame.setVisible(true);
        }
        
        for ( int i = 0 ; i < playerTeamButton.length ; i++ ){
            if ( e.getSource() == playerTeamButton[i] ){
                int ID = playerTeam.get(i).getID() ;
                playerTeam.remove(i) ;
                memberLabel[ID-1].setEnabled(true);

                //reset all playerTeamButton
                for ( int j = 0 ; j < playerTeamButton.length ; j ++ ){
                    playerTeamButton[j].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    ImageIcon woodIcon = new ImageIcon(getClass().getResource("picture/title.png"));
                    Image woodImage = woodIcon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                    woodIcon.setImage(woodImage);
                    playerTeamButton[j].setIcon(woodIcon);
                    playerTeamButton[j].setEnabled(false);
                    playerTeamButton[j].setPreferredSize(new Dimension(100,100));
                }
                
                //set new playerTeamButton
                for ( int j = 0 ; j < playerTeam.size() ; j ++ ){
                    ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/"+playerTeam.get(j).getFileName()));
                    Image image = roleImage.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                    roleImage.setImage(image);
                    playerTeamButton[j].setBackground(Color.DARK_GRAY);
                    playerTeamButton[j].setIcon(roleImage);
                    playerTeamButton[j].setEnabled(true);
                }
                break;
            }
        }
    }
}
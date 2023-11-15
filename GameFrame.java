import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class GameFrame extends JFrame {

    private MapFrame mapFrame ;
    private MusicPlay musicPlay = new MusicPlay("music/AllBGM.wav",false);

    public GameFrame(){
        
        this.setTitle("Animal Fight");
        this.setSize(800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);

        musicPlay.startMusic();

        ImageIcon roleImage = new ImageIcon(getClass().getResource("picture/title.png"));
        Image image = roleImage.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT);
        roleImage.setImage(image);
        JLabel label = new JLabel(roleImage);
        this.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);
        this.add(buttonPanel,BorderLayout.SOUTH);

        buttonPanel.add(new JLabel("                                           "), BorderLayout.WEST);

        JButton startAlone = new JButton();
        startAlone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startAlone.setContentAreaFilled(false);
        ImageIcon startIcon = new ImageIcon(getClass().getResource("picture/start.png"));
        Image startImage = startIcon.getImage().getScaledInstance(200, 60, Image.SCALE_DEFAULT);
        startIcon.setImage(startImage);
        startAlone.setIcon(startIcon);
        startAlone.setBorder(null);
        startAlone.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                mapFrame = new MapFrame(GameFrame.this) ;
                GameFrame.this.setVisible(false);
                mapFrame.setVisible(true);

            }
        });
        buttonPanel.add(startAlone, BorderLayout.CENTER);

        
        JButton rulesButton = new JButton();
        ImageIcon ruleImg = new ImageIcon(getClass().getResource("picture/rules1.png"));
        ruleImg.setImage(ruleImg.getImage().getScaledInstance(800, 400,Image.SCALE_DEFAULT ));
        rulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rulesButton.setContentAreaFilled(false);
        rulesButton.setPreferredSize(new Dimension(130,100));
        ImageIcon rulesIcon = new ImageIcon(getClass().getResource("picture/instruction.png"));
        Image rulesImage = rulesIcon.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        rulesIcon.setImage(rulesImage);
        rulesButton.setIcon(rulesIcon);
        rulesButton.setBorder(null);
        rulesButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if(event.getSource() == rulesButton)
                {
                    int response2 = 0;
                    while(response2!=1){
                        Object[] options = {"return","next page"};
                        String rules = "";
                        int response = JOptionPane.showOptionDialog(GameFrame.this,rules,"instruction",JOptionPane.PLAIN_MESSAGE,1, ruleImg,options,options[0] );
                        if(response == 1)
                        {
                            ImageIcon ruleImg2 = new ImageIcon(getClass().getResource("picture/rules2.png"));
                            ruleImg2.setImage(ruleImg2.getImage().getScaledInstance(800, 400,Image.SCALE_DEFAULT ));
                            Object[] newOptions = {"last page","return"};
                            int reponse2 = JOptionPane.showOptionDialog(GameFrame.this, rules, "instruction", JOptionPane.PLAIN_MESSAGE, 1, ruleImg2, newOptions, newOptions[0]);
                            if(reponse2 == 1)
                                break;
                        }
                        else
                            break;
                    }
                }
            }
        });

        buttonPanel.add(rulesButton, BorderLayout.EAST);
    }

    public MusicPlay getMusicPlay(){
        return this.musicPlay ;
    }

}
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class MapFrame extends JFrame implements ActionListener{

    private JButton[] mapButtons ;
    private GameFrame gameFrame ;

    public static void main(String[] args){
        MapFrame frame = new MapFrame(new GameFrame());
        frame.setVisible(true);
    }

    public MapFrame(GameFrame gameFrame){
        this.setTitle("Map");
        this.setSize(700,560);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(50,12,0));

        this.gameFrame = gameFrame ;

        JButton infoArea = new JButton() ;
        infoArea.setContentAreaFilled(false);
        infoArea.setOpaque(false);
        infoArea.setBorderPainted(false);
        ImageIcon woodIcon = new ImageIcon(getClass().getResource("picture/mapBrand.png"));
        Image woodImage = woodIcon.getImage().getScaledInstance(800, 200, Image.SCALE_DEFAULT);
        woodIcon.setImage(woodImage);
        infoArea.setIcon(woodIcon);
        infoArea.setBorder(null);
        infoArea.setPreferredSize(new Dimension(700,100));
        add(infoArea);
        
        JPanel buttonPanel = new JPanel() ;
        buttonPanel.setLayout(new GridLayout(3,4,20,20));
        buttonPanel.setOpaque(false);
        this.add(buttonPanel);

        mapButtons = new JButton[9] ;
        for ( int i = 0 ; i < mapButtons.length ; i ++ ){
            ImageIcon startIcon = new ImageIcon(getClass().getResource("picture/mapButton"+(i+1)+".png"));
            Image startImage = startIcon.getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT);
            startIcon.setImage(startImage);
            mapButtons[i] = new JButton(startIcon) ;
            mapButtons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            mapButtons[i].setContentAreaFilled(false);
            mapButtons[i].setBorder(null);
            mapButtons[i].setPreferredSize(new Dimension(100,100));
            mapButtons[i].addActionListener(this);
            buttonPanel.add(mapButtons[i]);
        }
    }
    @Override 
    public void actionPerformed(ActionEvent event){
        for ( int i = 0 ; i < mapButtons.length ; i ++ ){
            if ( event.getSource() == mapButtons[i] ){
                ArrangeFrame frame = new ArrangeFrame(this.gameFrame,i+1) ;
                frame.setVisible(true);
            }
        }
    }
}

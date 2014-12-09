import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sathya
 */

        
public class DoublePlayer extends JFrame implements ActionListener{
    
    Player player1 , player2;
    Player winner, loser;
    
    private boolean firstPlayer;
    private final ImagePanel panel = new ImagePanel();
    private final XOButton b[] = new XOButton[9];
    private boolean victory;
   
   
    public static void main(String[] args) {
         
      //  new DoublePlayer()
    }
            
    public DoublePlayer(){
        
        this.firstPlayer = true;
        this.victory = false;
        this.setTitle("TicTacToe");
        this.setSize(300, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
       // panel.add(new JLabel(new ImageIcon(this.getClass().getResource("wooddfsdfds.png")))); 
        //this.add(panel);
        
        
        panel.setLayout(new GridLayout(3,3));
        
        
        for(int i=0; i<b.length; i++){
            
            b[i] = new XOButton(this) ;

            panel.add(b[i]);
        }
        
        this.add(panel);
        this.setVisible(true);
        
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
       
            XOButton button = (XOButton)e.getSource();
            
        if(!button.isClicked()){
            
        
        button.clicked = true; 
            /* if the firist player clicked this button*/
            if(firstPlayer){
                
                        firstPlayer = false; //second player makes the next move
                        button.setIcon(button.getx());
                        button.choice = 1; 
            }
            
            /*else if the second player clicked this button*/
            else{
                
                firstPlayer = true;
                button.setIcon(button.geto());
                button.choice = 2;
            }
        }
        
        if((b[0].clicked&&b[1].clicked&&b[2].clicked)&&(b[0].choice == b[1].choice)&&(b[1].choice==b[2].choice)){
            
           
                
                victory = true;
          
        }
        else if((b[2].clicked&&b[4].clicked&&b[6].clicked)&&(b[2].choice == b[4].choice)&&(b[4].choice==b[6].choice)){
               
                
                victory = true;
        
        }
        else if((b[3].clicked&&b[4].clicked&&b[5].clicked)&&(b[3].choice == b[4].choice)&&(b[4].choice==b[5].choice)){
      
                
                victory = true;
               
            }
        else if((b[6].clicked&&b[7].clicked&&b[8].clicked)&&(b[6].choice == b[7].choice)&&(b[7].choice==b[8].choice)){
           
                victory = true;
            
        }
        else if((b[0].clicked&&b[3].clicked&&b[6].clicked)&&(b[0].choice == b[3].choice)&&(b[3].choice==b[6].choice)){
        
            
            victory = true;
        }
        
        else if((b[1].clicked&&b[4].clicked&&b[7].clicked)&&(b[1].choice == b[4].choice)&&(b[4].choice==b[7].choice)){
          
            victory = true;
        }
        
        else if((b[2].clicked&&b[5].clicked&&b[8].clicked)&&(b[2].choice == b[5].choice)&&(b[5].choice==b[8].choice)){
           
            victory = true;
        
        }
        else if((b[0].clicked&&b[4].clicked&&b[8].clicked)&&(b[0].choice == b[4].choice)&&(b[8].choice == b[4].choice)){
           
            victory = true;
        
        }
 
        
        if(victory){
            
            if(!firstPlayer){
                
                player1.setWins(player1.getWins() + 1);
                player2.setLoses(player2.getLoses() + 1);
                
                this.winner = player1;
                this.loser = player2;
             
                
               
            //JOptionPane.showMessageDialog(null, player1.getUsername()+" won! congrats!");
            }
            else{
                
                player2.setWins(player2.getWins() + 1);
                player1.setLoses(player1.getLoses() + 1);
                
                this.winner = player2;
                this.loser = player1;
              
               // JOptionPane.showMessageDialog(null, player2.getUsername()+" won! congrats!");
           
            }
            
            update(player1);
            update(player2);
            JOptionPane.showMessageDialog(this, winner.getUsername()+" won! Congrats! :)");
            
            Stats stats = new Stats(this);
            stats.setVisible(true);
            this.dispose();
        
         
           
        }
        else if(b[0].clicked&&b[1].clicked&&b[2].clicked&&b[3].clicked&&b[4].clicked&&b[5].clicked
                &&b[6].clicked&&b[7].clicked&&b[8].clicked){
                
                JOptionPane.showMessageDialog(null, "DRAW!");
                removeAll();
                }
     }
    
    
    public Player SetPlayers(String username){
        
        Connection con = null;
        Player player;
        String query1 = "select * from Player where username=?";
       
      try {
           con = DBConnector.getDBConnection();
          
          PreparedStatement ps1 = con.prepareStatement(query1);
          ps1.setString(1, username);
          ResultSet rs1 = ps1.executeQuery();
          rs1.next();
          
          player = new Player(rs1.getString("username"), rs1.getInt("wins"), rs1.getInt("loses"));
          return player;
          
     
          
      } catch (ClassNotFoundException ex) {
         JOptionPane.showMessageDialog(this, ex);
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(this, "New Member");
            try {
                
                con = DBConnector.getDBConnection();
                
                String str = "insert into player (username,wins,loses) " + "values ('"+username+"',0,0)";
    
                PreparedStatement create1 = con.prepareStatement(str);
                
                
              create1.execute();
              
              player = new Player(username,0,0);
              return player;
               
                
            } catch (    SQLException | ClassNotFoundException ex1) {
             JOptionPane.showMessageDialog(null, ex1);
             
            }
          
      }
      
      try{
         if(con!=null)
            con.close();
      }catch(SQLException se){
         JOptionPane.showMessageDialog(null, se);
      }
        return null;
    }
    
    
    public void update(Player player){
        try {
            Connection con = DBConnector.getDBConnection();
            
            
           String update ="UPDATE player SET wins = ?, loses = ? WHERE username = ?";
           PreparedStatement ps;
            try {
                ps = con.prepareStatement(update);
                
                ps.setInt(1, player.getWins());
                ps.setInt(2, player.getLoses());

                ps.setString(3, player.getUsername());
                ps.executeUpdate();
                
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(DoublePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DoublePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
                
}
    
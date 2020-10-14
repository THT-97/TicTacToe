import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TranHuyThinh
 */
public class Game extends JFrame implements MouseListener{
    private Menu menu;
    private Board board;
    private boolean isTurn;
    int player, cpu, winner;
    int line, size;
    JPanel boardZone, bottomZone;
    JLabel result;

    public Game(boolean isTurn, int line, int size, String title, Menu menu) throws HeadlessException {
        super(title);
        this.menu = menu;
        this.isTurn = isTurn;
        this.line = line;
        this.size = size;
        if(isTurn) {
            player = 2;
            cpu = 1;
        }
        else{
            player = 1;
            cpu = 2;
        }
        createUI();
    }
    private void createUI() {
        setResizable(false);
        setSize(size*80+20,size*80+20);
	setLocationRelativeTo(null);
	setVisible(true);
        createBoard();
        add(bottomZone = new JPanel(), BorderLayout.SOUTH);
        bottomZone.setPreferredSize(new Dimension(size*80, 20));
        bottomZone.setBackground(Color.white);
        bottomZone.add(result = new JLabel());
        //If game closed. Reopen menu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                menu.setVisible(true);
            }
        });
    }

    private void createBoard() {
        add(boardZone = new JPanel());
        boardZone.setPreferredSize(new Dimension(size*80, size*80));
        boardZone.setBackground(Color.black);
        boardZone.setLayout(new GridLayout(size, size, 1, 1));
        board = new Board(size, line, player, cpu, isTurn, this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }
    
}

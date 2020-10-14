
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
public class Menu extends JFrame implements MouseListener{
    ArrayList<JMenuItem> sizeList;
    ArrayList<JMenuItem> lineList;
    private JPanel max,min;
    private JButton X,O;
    private int size, line;
    private Game game;
    
    public Menu(String title) throws HeadlessException {
        super(title);
        size = 3;
        line = 3;
        setResizable(false);
        setSize(200,200);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenus();
	setVisible(true);
        
        max = new JPanel();
        max.setSize(200, 50);
        add(max);
        min = new JPanel();
        min.setSize(200, 50);
        add(min,BorderLayout.SOUTH);
        addButtons();
    }

    private void addMenus() {
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        sizeList = new ArrayList<>();
        lineList = new ArrayList<>();
        JMenu options = new JMenu("Game options");
        //add size menu
        JMenu sizeOption = new JMenu("Size: ");
        sizeList.add(new JMenuItem("3X3"));
        sizeList.add(new JMenuItem("5X5"));
        sizeList.add(new JMenuItem("10X10"));
        sizeList.forEach((t) -> {
            t.addMouseListener(this);
            sizeOption.add(t);
        });
        options.add(sizeOption);
        //add line menu
        JMenu lineOption = new JMenu("Length to win: ");
        lineList.add(new JMenuItem("3"));
        lineList.add(new JMenuItem("5"));
        lineList.forEach((t) -> {
            t.addMouseListener(this);
            lineOption.add(t);
        });
        options.add(lineOption);
        menu.add(options);
    }
    
    private void addButtons() {
        X = new JButton("First X");
        X.setForeground(Color.red);
        X.setPreferredSize(new Dimension(100, 50));
        X.addMouseListener(this);
        max.add(X,BorderLayout.CENTER);
        
        O = new JButton("Second O");
        O.setForeground(Color.blue);
        O.setPreferredSize(new Dimension(100, 50));
        O.addMouseListener(this);
        min.add(O,BorderLayout.CENTER);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==sizeList.get(0)) size = 3;
        if(e.getSource()==sizeList.get(1)) size = 5;
        if(e.getSource()==sizeList.get(2)) size = 10;
        if(e.getSource()==lineList.get(0)) line = 3;
        if(e.getSource()==lineList.get(1) && size >= 5) line = 5;
        if(e.getSource()==X){
            game = new Game(false, line, size, "Tic Tac Toe", (Menu)this);
            setVisible(false);
        }
        if(e.getSource()==O){
            game = new Game(true, line, size, "Tic Tac Toe", (Menu)this);
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

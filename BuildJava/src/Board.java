
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TranHuyThinh
 */
class Board implements MouseListener{

    private int size, line;
    private int[][] board;
    private JButton[][] buttons;
    private int player, cpu, winner;
    private boolean isEnd, isTurn;
    Game game;

    public Board(int size, int line, int player, int cpu, boolean isTurn, Game game) {
        this.size = size;
        this.line = line;
        this.player = player;
        this.cpu = cpu;
        this.isTurn = isTurn;
        this.game = game;
        isEnd = false;
        winner = 0;
        board = new int[size][size];
        buttons = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(size*80/size, size*80/size));
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].setFont(new Font("Mangal", Font.BOLD, (size*80)/(size*2)));
                buttons[i][j].addMouseListener(this);
                game.boardZone.add(buttons[i][j]);
            }
        }
        if(isTurn) cpuMove();
    }
    
    private boolean checkWin(int player){
        int count;
	//check horizontal routes
	for(int i=0; i<size; i++){
            count = 0;
            for(int j=0; j<size; j++){
		if(board[i][j]==player){
                    count += 1;
                    if(count >= line) return true;
                }
                else count = 0;
            }
	}
	//check vertical routes
	for(int i=0; i<size; i++){
            count = 0;
            for(int j=0; j<size; j++){
		if(board[j][i]==player){
                    count += 1;
                    if(count >= line) return true;
		}
		else count = 0;
            }
	}
	//check angle routes
	for(int i=0; i<size; i++){
            count = 0;
            for(int j=0; j<size; j++){
		if(board[i][j]==player){
                    count = 1;
                    for(int l=1; l<=line; l++){
                        if(i+l<size && j+l<size && board[i+l][j+l]==player){
                            count += 1;
                            if(count >= line) return true;
                        }
                        else count = 1;
                    }
                    for(int l=1; l<=line; l++){
                        if(i+l<size && j-l>=0 && board[i+l][j-l]==player){
                            count += 1;
                            if(count >= line) return true;
                        }
                        else count = 1;
                    }
                }
		else count = 0;
            }
	}
	//if no win route found
        return false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!isEnd){
            if(!isTurn){
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if(e.getSource()==buttons[i][j] && board[i][j]==0){
                            if(player==1) {
                                buttons[i][j].setForeground(Color.red);
                                buttons[i][j].setText("X");
                            }
                            else{
                                buttons[i][j].setForeground(Color.blue);
                                buttons[i][j].setText("O");
                            }
                            board[i][j] = player;
                            isTurn = true;
                            System.out.println("click");
                            cpuMove();
                        }
                    }
                }
            }
            if(checkWin(player)){
                winner = player;
                game.result.setText("U won");
                isEnd = true;
            }
            else if(checkWin(cpu)){
                winner = cpu;
                game.result.setText("U lost");
                isEnd = true;
            }
            else if(generateMoves(cpu).isEmpty() && winner==0) game.result.setText("Draw");
        }
    }
    
    private int minmax(int depth, boolean isTurn){
        List <Position> moves = generateMoves(isTurn?cpu:player);
        if(checkWin(player)){
            return -100;
        }
        else if(checkWin(cpu)){
            return 100;
        }
        else if(moves.isEmpty() || depth==0) return getScore();
        int bestScore = 0;
        //try moves
            if (isTurn){  // simulate cpu move
               bestScore = -10000;
		for(Position move : moves){
                    int x = move.getX();
                    int y = move.getY();
                    board[x][y] = cpu;
                    bestScore = max(bestScore, minmax(depth-1, !isTurn));
                    board[x][y] = 0; 
                }
			
            }
            else {  // simulate player move
                bestScore = 10000;
		for(Position move : moves){
                    int x = move.getX();
                    int y = move.getY();
                    board[x][y] = 1;
                    bestScore = min(bestScore, minmax(depth-1, !isTurn));
                    board[x][y] = 0;
                }
            }
        return bestScore;
    }
            
    
    private void cpuMove() {
        int depth = 5; //if size = 3x3 depth is 5
        switch(size){ //decrease depth on larger sizes for more speed
            case 5: depth = 3; break;
            case 10: depth = 1; break;
        }
        int best = -1000;
	Position move = new Position(-1, -1);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < board[i].length; j++) {
		if (board[i][j] == 0) {
                    board[i][j] = 2;
                    int score = minmax(depth, isTurn);
                    board[i][j] = 0;
                    if (score > best) {
                        best = score;
                        move.setX(i);
                        move.setY(j);
                    }
		}
            }
	}
        int x = move.getX();
        int y = move.getY();
        board[x][y] = cpu;
        if(cpu==1){
            buttons[x][y].setForeground(Color.red);
            buttons[x][y].setText("X");
        }
        else{
            buttons[x][y].setForeground(Color.blue);
            buttons[x][y].setText("O");
        }
        isTurn = false;
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

    private List generateMoves(int turn) {
        List<Position> moves = new ArrayList();
        List<Integer> scores = new ArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j]==0){
                    moves.add(new Position(i, j));
                    board[i][j] = turn;
                    scores.add(getScore());
                    board[i][j] = 0;
                }
            }
        }
        //sort moves
        for (int i = 0; i < scores.size(); i++) {
            for (int j = i+1; j < scores.size()-1; j++) {
                if(scores.get(i) < scores.get(j)) Collections.swap(moves, i, j);
            }
            
        }
        return moves;
    }

    private int getScore() {
        int score = 0;
	//check horizontal routes
	for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
		if(board[i][j]==cpu) score += 1;
                else if(board[i][j]==player) score -= 1;
            }
	}
	//check vertical routes
	for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
		if(board[i][j]==cpu) score += 1;
                else if(board[i][j]==player) score -= 1;
            }
	}
	//check angle routes
	for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
		if(board[i][j]!=0){
                    if (board[i][j]==cpu) score+=1;
                    else if(board[i][j]==player) score -= 1;
                    for(int l=1; l<=line; l++){
                        if(i+l<size && j+l<size && board[i+l][j+l]==cpu) score += 1;
                        else if(i+l<size && j+l<size && board[i+l][j+l]==player) score -= 1;
                    }
                    for(int l=1; l<=line; l++){
                        if(i+l<size && j-l>=0 && board[i+l][j-l]==cpu) score += 1;
                        else if(i+l<size && j+l<size && board[i+l][j+l]==player) score -= 1;
                    }
                }
            }
	}
        return score;
    }
}

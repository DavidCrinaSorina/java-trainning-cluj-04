import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TheInterface extends JPanel implements MouseListener, MouseMotionListener{
    static int mouseX, mouseY, newMouseX, newMouseY;//pentru a lucra cu mouse-ul
    static int squareSize=60;//dimensiunea tablei
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.gray);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {//pentru tabla de joc
            g.setColor(new Color(255,200,100));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(14, 63, 150));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("ChessPieces.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (ABChess.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=2; k=0;
                    break;
                case "r": j=2; k=1;
                    break;
                case "K": j=4; k=0;
                    break;
                case "k": j=4; k=1;
                    break;
                case "B": j=3; k=0;
                    break;
                case "b": j=3; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "A": j=0; k=0;
                    break;
                case "a": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }


    }
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //daca ii in interiorul tablei mouse-ul
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //daca mouse-ul ii in interiorul tablei
            newMouseX=e.getX();
            newMouseY=e.getY();
            if (e.getButton()==MouseEvent.BUTTON1) {
                String dragMove;//interpreteaza newMouseX si newMouseY pentru locatia pionului pe care il mutam
                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(ABChess.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
                    //pionul promoveaza
                    dragMove=""+mouseX/squareSize+newMouseX/squareSize+ABChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                } else {
                    //mutare obisnuita
                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+ABChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
                }
                String userPosibilities=ABChess.posibleMoves();
                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
                    //daca ii miscare valida( cand ii miscare invalida nu se intampla nimic)
                    ABChess.makeMove(dragMove);
                    ABChess.flipBoard();
                    ABChess.makeMove(ABChess.alphaBeta(ABChess.globalDepth, 1000000, -1000000, "", 0));
                    ABChess.flipBoard();
                    repaint();
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
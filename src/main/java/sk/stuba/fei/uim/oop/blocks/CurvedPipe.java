package sk.stuba.fei.uim.oop.blocks;

import java.awt.*;

public class CurvedPipe extends Pipe {
    private boolean isClockwise;
    private int orientation;

    public CurvedPipe(int x, int y, int size, boolean isClockwise, int orientation) {
        super(x,y,size);
        this.isClockwise = isClockwise;
        this.orientation = orientation;
    }

    public int getOrientation(){
        return orientation;
    }

    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    @Override
    public boolean canConnectTo(Pipe other) {
        return (other instanceof StraightPipe || other instanceof CurvedPipe);
    }

    @Override
    public void rotate() {
        this.orientation = (this.orientation + 1) % 4;
        this.isClockwise = !this.isClockwise;
    }

    @Override
    public void redrawPipe() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);

        g.setColor(Color.BLACK);
        g.drawLine(x,y,x+size,y);
        g.drawLine(x+size,y,x+size,y+size);
        g.drawLine(x,y+size,x+size,y+size);
        g.drawLine(x,y,x,y+size);
        if (orientation == 0) { // 0 degrees
            g.drawLine(x + size/2, y, x + size/2, y + size/2);
            g.drawLine(x + size/2, y + size/2, x + size, y + size/2);
        } else if (orientation == 1) { // 90 degrees
            g.drawLine(x+size/2,y+size/2,x+size,y+size/2);
            g.drawLine(x+size/2,y+size/2, x+size/2, y+size);
        } else if (orientation == 2) { // 180 degrees
            g.drawLine(x,y+size/2,x+size/2,y+size/2);
            g.drawLine(x+size/2,y+size/2, x+size/2, y+size);
        } else if (orientation == 3) { // 270 degrees
            g.drawLine(x + size/2, y, x + size/2, y + size/2);
            g.drawLine(x,y+size/2,x+size/2,y+size/2);
        }
        if(isHighlighted){
            g.setColor(Color.BLUE);
            g.drawLine(x,y,x+size,y);
            g.drawLine(x+size,y,x+size,y+size);
            g.drawLine(x+size,y+size,x,y+size);
            g.drawLine(x,y+size,x,y);
        }
    }
}

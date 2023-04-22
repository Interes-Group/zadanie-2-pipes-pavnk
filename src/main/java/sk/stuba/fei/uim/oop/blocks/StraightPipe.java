package sk.stuba.fei.uim.oop.blocks;

import java.awt.Color;
import java.awt.Graphics;

public class StraightPipe extends Pipe {


    private boolean isHorizontal;

    public StraightPipe(int x, int y, int size, boolean isHorizontal) {
        super(x,y,size);
        this.isHorizontal = isHorizontal;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    @Override
    public boolean canConnectTo(Pipe other) {
        if (other == null) {
            return false;
        }
        if (other instanceof EmptyPipe) {
            return true;
        }
        if (other instanceof StraightPipe) {
            StraightPipe otherStraightPipe = (StraightPipe) other;
            return this.isHorizontal() != otherStraightPipe.isHorizontal();
        }
        return false;
    }

    @Override
    public void rotate() {
        isHorizontal = !isHorizontal;
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
        if (isHorizontal) {
            g.drawLine(x, y + size / 2, x + size, y + size / 2);
        } else {
            g.drawLine(x + size / 2, y, x + size / 2, y + size);
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

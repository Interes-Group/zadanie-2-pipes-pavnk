package sk.stuba.fei.uim.oop.blocks;

import java.awt.Graphics;

public class EmptyPipe extends Pipe {

    public EmptyPipe(int x, int y, int size, boolean waterFlows, boolean isHighlighted) {
        super(x,y,size,waterFlows,isHighlighted);
    }
    @Override
    public void setWaterFlows(boolean waterFlows) {
        this.waterFlows = waterFlows;
    }

    @Override
    public boolean isFacingUp() {
        return false;
    }

    @Override
    public boolean isFacingDown() {
        return false;
    }

    @Override
    public boolean isFacingLeft() {
        return false;
    }

    @Override
    public boolean isFacingRight() {
        return false;
    }

    public void rotate() {
    }


    @Override
    public void draw(Graphics g) {
        if(isHighlighted)
            drawHighlight(g);
        else
            drawBackground(g);
        drawBorder(g);
    }
}

package sk.stuba.fei.uim.oop.blocks;

import java.awt.Color;
import java.awt.Graphics;

public class StraightPipe extends Pipe {
    private boolean isHorizontal;

    public StraightPipe(int x, int y, int size, boolean isHorizontal,  boolean waterFlows, boolean isHighlighted) {
        super(x,y,size,waterFlows,isHighlighted);
        this.isHorizontal = isHorizontal;
    }

    @Override
    public void setWaterFlows(boolean waterFlows) {
        this.waterFlows = waterFlows;
    }

    @Override
    public boolean isFacingUp() {
        return !isHorizontal;
    }

    @Override
    public boolean isFacingDown() {
        return !isHorizontal;
    }

    @Override
    public boolean isFacingLeft() {
        return isHorizontal;
    }

    @Override
    public boolean isFacingRight() {
        return isHorizontal;
    }

    @Override
    public void rotate() {
        isHorizontal = !isHorizontal;
    }


    @Override
    public void draw(Graphics g) {
        if(isHighlighted)
            drawHighlight(g);
        else
            drawBackground(g);
        drawBorder(g);
        if(waterFlows){
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.GRAY);
        }
        if (isHorizontal) {
            g.fillRect(x, y+size/3, size, size/3);
        } else {
            g.fillRect(x + size / 3, y, size / 3, size);
        }
    }
}

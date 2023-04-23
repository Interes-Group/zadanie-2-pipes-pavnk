package sk.stuba.fei.uim.oop.blocks;

import java.awt.*;

public abstract class Pipe {
    protected int x;
    protected int y;
    protected int size;
    protected boolean isHighlighted;
    protected boolean waterFlows;

    public Pipe(int x, int y, int size,boolean waterFlows, boolean isHighlighted){
        this.x = x*size;
        this.y = y*size;
        this.size = size;
        this.isHighlighted = isHighlighted;
        this.waterFlows = waterFlows;
    }

    public void setHighlighted(boolean highlighted){
        isHighlighted = highlighted;
    }
    protected void drawBorder(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(x,y,x+size,y);
        g.drawLine(x+size,y,x+size,y+size);
        g.drawLine(x,y+size,x+size,y+size);
        g.drawLine(x,y,x,y+size);
    }
    protected void drawBackground(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }
    protected void drawHighlight(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
    }

    public abstract void setWaterFlows(boolean waterFlows);
    public abstract boolean isFacingUp();
    public abstract boolean isFacingDown();
    public abstract boolean isFacingLeft();
    public abstract boolean isFacingRight();

    public abstract void rotate();
    public abstract void draw(Graphics g);
}
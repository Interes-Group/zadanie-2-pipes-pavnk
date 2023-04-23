package sk.stuba.fei.uim.oop.blocks;

import java.awt.*;

public class StartPipe extends Pipe{
    private Direction orientation;

    public StartPipe(int x, int y, int size, Direction orientation,boolean waterFlows, boolean isHighlighted) {
        super(x,y,size,waterFlows,isHighlighted);
        this.orientation = orientation;
    }

    @Override
    public void setWaterFlows(boolean waterFlows) {
        this.waterFlows = waterFlows;
    }

    @Override
    public boolean isFacingUp() {
        return this.orientation == Direction.UP;
    }

    @Override
    public boolean isFacingDown() {
        return this.orientation == Direction.DOWN;
    }

    @Override
    public boolean isFacingLeft() {
        return this.orientation == Direction.LEFT;
    }

    @Override
    public boolean isFacingRight() {
        return this.orientation == Direction.RIGHT;
    }

    @Override
    public void rotate() {
        this.orientation = Direction.values()[(this.orientation.ordinal()+1)%4];
    }

    @Override
    public void draw(Graphics g) {
        if(isHighlighted)
            drawHighlight(g);
        else
            drawBackground(g);
        drawBorder(g);
        if(this.waterFlows){
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        if (orientation == Direction.UP) {
            g.fillRect(x + size / 3, y, size / 3, size-size/3);
        } else if (orientation == Direction.RIGHT) {
            g.fillRect(x + size / 3, y+size/3, size-size / 3, size/3);
        } else if (orientation == Direction.DOWN) {
            g.fillRect(x + size / 3, y+size/3, size / 3, size-size/3);
        } else if (orientation == Direction.LEFT) {
            g.fillRect(x, y+size/3, size-size/3, size/3);
        }
    }
}

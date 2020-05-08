package pattern.structural.facade;

import com.baomidou.mybatisplus.extension.api.R;

public class ShapeMaker {

    private Circle circle;

    private Rectangle rectangle;

    public ShapeMaker(){
        circle = new Circle();
        rectangle = new Rectangle();
    }

    public void drawCircle(){
        circle.draw();
    }

    public void drawRectangle(){
        rectangle.draw();
    }
}

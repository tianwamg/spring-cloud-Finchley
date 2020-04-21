package pattern.decoration;

public class redShapeDecorator extends shapeDecorator {


    public redShapeDecorator(shape decoratorShape) {
        super(decoratorShape);
    }

    public void draw(){
        decoratorShape.draw();
    }

    public void setRedBorder(shape decoratorShape){
        System.out.println("Border Color: red");
    }
}

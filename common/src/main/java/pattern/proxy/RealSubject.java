package pattern.proxy;

public class RealSubject implements Subject {

    @Override
    public void dosomething() {
        System.out.println("start");
    }
}

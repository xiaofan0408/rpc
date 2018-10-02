package my;

import my.single.Reactor;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Reactor reactor = new Reactor(8081);
            new Thread(reactor).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

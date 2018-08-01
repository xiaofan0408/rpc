package my.single;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements  Runnable {

    private ServerSocketChannel serverSocket;
    private Selector selector;

    public Acceptor(ServerSocketChannel serverSocket, Selector selector) {
        this.serverSocket = serverSocket;
        this.selector = selector;
    }
    @Override
    public void run() {
        try {
            SocketChannel c = serverSocket.accept();
            if (c != null) {
                new Handler(selector, c);
            }
        }
        catch(IOException ex) { /* ... */ }
    }
}

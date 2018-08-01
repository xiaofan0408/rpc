package my.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements  Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocket;
    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(
                new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk =
                serverSocket.register(selector,
                        SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor(this.serverSocket, this.selector));
    }


    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()){
                    dispatch((SelectionKey)it.next());
                }
                selected.clear();
            }
        } catch (IOException ex) { /* ... */ }
    }
    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment());
        if (r != null){
            r.run();
        }
    }
}

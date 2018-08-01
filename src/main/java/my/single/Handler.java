package my.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable{
    private final int MAXIN = 1024*10;
    private final int MAXOUT = 1024*10;
    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;
    Handler(Selector sel, SocketChannel c)
            throws IOException {
        socket = c; c.configureBlocking(false);
// Optionally try first read now
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }
    boolean inputIsComplete() {
        return input.hasRemaining();
    }
    boolean outputIsComplete() {
        return output.hasRemaining();
    }
    void process() {
//        byte[] length_prefix = new byte[4];
//        input.get(length_prefix, 0 , 4);
//        if (length_prefix.length < 4) {
//            return;
//        }
//        String len = new String(length_prefix);
        System.out.println(input);
        if (input.hasArray()) {
            System.out.println(new String(input.array()));
        }

    }
    @Override
    public void run() {
        try {
            if (state == READING){
                read();
            }
            else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) { /* ... */ }
    }
    void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
// Normally also do first write now
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
        state = READING;
        sk.interestOps(SelectionKey.OP_READ);

    }
}

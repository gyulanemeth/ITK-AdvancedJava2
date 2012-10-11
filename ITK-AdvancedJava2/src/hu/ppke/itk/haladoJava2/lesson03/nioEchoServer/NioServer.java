package hu.ppke.itk.haladoJava2.lesson03.nioEchoServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
	private static int PORT = 61133;
	
	private static ByteBuffer buffer = ByteBuffer.allocate(1024);
	
	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			ServerSocket serverSocket = serverSocketChannel.socket();
			Selector selector = Selector.open();
			
			serverSocket.bind(new InetSocketAddress(PORT));
			serverSocketChannel.configureBlocking(false);
			
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while (true) {
				int n = selector.select();
				
				if (n == 0) {
					continue;
				}
				
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				
				while (it.hasNext()) {
					SelectionKey nextKey = it.next();
					
					if (nextKey.isAcceptable()) {
						ServerSocketChannel ssc = (ServerSocketChannel) nextKey.channel();
						SocketChannel channel = ssc.accept();
						
						registerChannel(selector, channel, SelectionKey.OP_READ);
						sayHello(channel);
					} else if (nextKey.isReadable()) {
						readDataFromSocket(nextKey);
					}
					
					it.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void registerChannel (Selector selector, SelectableChannel channel, int ops) throws IOException {
		if (channel == null) {
			return;
		}
		
		channel.configureBlocking(false);
		channel.register(selector, ops);
	}
	
	private static void readDataFromSocket (SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		
		
		buffer.clear();
		
		int count = 0;
		while ((count = channel.read(buffer)) > 0) {
			buffer.flip();
			
			while (buffer.hasRemaining()) {
				channel.write(buffer);
			}
			
			buffer.clear();
		}
		
		//EOF invalidates the key
		if (count < 0) {
			channel.close();
		}
	}
	
	private static void sayHello (SocketChannel channel) throws IOException {
		buffer.clear();
		buffer.put("Hi client!".getBytes());
		buffer.flip();
		
		channel.write(buffer);
	}
}

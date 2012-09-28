package hu.ppke.itk.haladoJava2.lesson03.nioEchoServerWithObjectResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer2 {
	public static class CommunicationObject implements Serializable{
		private static final long serialVersionUID = -984749567567997535L;

		private static int nextId = 0;
		
		private int id;
		private String message;
		
		public CommunicationObject(String message) {
			this.id = nextId++;
			this.message = message;
		}
		
		@Override
		public String toString() {
			return "id: " + id + " message: " + message;
		}
	}
	
	
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
			byte[] bytes = new byte[buffer.limit()];
			buffer.get(bytes);
			
			CommunicationObject communicationObject = new CommunicationObject(new String (bytes));
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream (baos);
			
			oos.writeObject(communicationObject);
			
			byte[] byteArray = baos.toByteArray();
			
			buffer.clear();
			
			
			System.out.println("responseSize: " + byteArray.length);
			
			ByteBuffer buffer1 = ByteBuffer.allocate(4);
			buffer1.putInt(byteArray.length);
			ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
			
			buffer1.flip();
			//buffer2.flip();
			
			while (buffer1.hasRemaining()) {
				channel.write(buffer1);
			}
			
			while (buffer2.hasRemaining()) {
				channel.write(buffer2);
			}
			
			
//			buffer.flip();
//			
//			while (buffer.hasRemaining()) {
//				channel.write(buffer);
//			}

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

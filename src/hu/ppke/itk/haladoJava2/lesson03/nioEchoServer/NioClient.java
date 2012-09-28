package hu.ppke.itk.haladoJava2.lesson03.nioEchoServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class NioClient {
	
	public static void main(String[] args) {
		String host = "localhost";
		int port = 61133;
		
		try {
			SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
			
			WritableByteChannel out = Channels.newChannel(System.out);

			ByteBuffer inBuffer = ByteBuffer.allocate(1024);
			socketChannel.read(inBuffer);
			inBuffer.flip();
			
			out.write(inBuffer);
			
			ByteBuffer outBuffer = ByteBuffer.allocate(1024);
			outBuffer.put("testing".getBytes());
			outBuffer.flip();
			
			while (true) {
				System.out.println();
				
				socketChannel.write(outBuffer);
				outBuffer.rewind();
				
				inBuffer.clear();
				socketChannel.read(inBuffer);
				inBuffer.flip();
				
				out.write(inBuffer);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
//			while (true) {
//				channelCopy(in, socketChannel);
//				channelCopy(socketChannel, out);
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

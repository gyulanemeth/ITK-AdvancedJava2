package hu.ppke.itk.haladoJava2.lesson03.nioEchoServerWithObjectResponse;


import hu.ppke.itk.haladoJava2.lesson03.nioEchoServerWithObjectResponse.NioServer2.CommunicationObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;


public class NioClient2 {
	
	public static void main(String[] args) {
		String host = "localhost";
		int port = 61133;
		
		try {
			SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
			
			WritableByteChannel out = Channels.newChannel(System.out);
			ReadableByteChannel in = Channels.newChannel(System.in);

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
				
				ByteBuffer responseSizeBuffer = ByteBuffer.allocate(4);
				socketChannel.read(responseSizeBuffer);
				responseSizeBuffer.flip();
				
				int responseSize = responseSizeBuffer.getInt();
				
				System.out.println("responseSize: " + responseSize);
				
				ByteBuffer responseBuffer = ByteBuffer.allocate(responseSize);
				
				socketChannel.read(responseBuffer);
				
				responseBuffer.flip();
				
				byte[] responseBytes = new byte[responseBuffer.limit()];
				responseBuffer.get(responseBytes);
				
				
				ByteArrayInputStream bais = new ByteArrayInputStream (responseBytes);
				ObjectInputStream ois = new ObjectInputStream (bais);
				
				try {
					CommunicationObject comObj = (CommunicationObject) ois.readObject();
					System.out.println(comObj);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
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

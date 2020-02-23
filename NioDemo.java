import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Scanner;

public class NioDemo {
    public static void main(String[] args) {
        try {
            File file = new File("/home/project/nio.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileChannel writeChannel = new RandomAccessFile(file, "rw").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            System.out.println("Please input a string: ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            byteBuffer.put(s.getBytes());
            System.out.println("Position: " + byteBuffer.position() + " Limit: " + byteBuffer.limit() + " Capacity: " + byteBuffer.capacity());
            byteBuffer.flip();
            System.out.println("After flip:: Position: " + byteBuffer.position() + " Limit: " + byteBuffer.limit() + " Capation: " + byteBuffer.capacity());
            writeChannel.write(byteBuffer);
            byteBuffer.clear();
            writeChannel.close();

            FileChannel readChannel = new RandomAccessFile(file, "r").getChannel();
            while (readChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();

                Charset charset = Charset.forName("UTF-8");
                CharsetDecoder decoder = charset.newDecoder();
                System.out.println("The String is: " + decoder.decode(byteBuffer));
                byteBuffer.clear();
            }
            readChannel.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
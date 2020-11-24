package com.assign8;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class EchoByteBuffer {

    private ByteBuffer byteBuffer;
    private String message;

    public EchoByteBuffer() {
        byteBuffer = ByteBuffer.allocate(256);
        message = "";
    }

    public boolean updateOnRead() {
        byteBuffer.flip();
        int bytesNum = byteBuffer.limit();
        byte[] data = new byte[bytesNum];
        byteBuffer.get(data);
        String result = new String(data);
        message += result;
        byteBuffer.clear();
        message = message.replace("_EOM", " - Echoed by the server");
        byteBuffer = Charset.forName("ISO-8859-1").encode(CharBuffer.wrap(message.toCharArray()));
        return true;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}

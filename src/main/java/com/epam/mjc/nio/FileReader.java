package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = null;
        String allStr = "", name = "", email = "";
        int age = 0;
        long phoneNumber = 0;

        try(RandomAccessFile aFile = new RandomAccessFile("src/main/resources/Profile.txt", "r");
            FileChannel inChannel = aFile.getChannel();) {

            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            for (int i = 0; i < fileSize; i++) {
                char c = (char) buffer.get();
                if(c == '\n'){
                    if(allStr.contains("Name")){
                        name = allStr.substring(allStr.indexOf(" ")+1);
                        allStr = "";
                    }
                    else if(allStr.contains("Age")){
                        age = Integer.parseInt(allStr.substring(allStr.indexOf(" ")+1));
                        allStr = "";
                    }
                    else if(allStr.contains("Email")){
                        email = allStr.substring(allStr.indexOf(" ")+1);
                        allStr = "";
                    }else if(allStr.contains("Phone")){
                        phoneNumber = Long.parseLong(allStr.substring(allStr.indexOf(" ")+1));
                        allStr = "";
                    }
                }else {
                    allStr += c;
                }
            }
            profile = new Profile(name, age, email,phoneNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile;
    }
}

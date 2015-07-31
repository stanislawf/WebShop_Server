/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author sf
 */
public abstract class ByteTransformer {
    public static byte[] getBytesOfFile(String filePath){
        byte[] result = null;
        File f = new File(filePath);
        
        try {
            result = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            System.out.println("An error occured during transforming the file to a byte array");
        }
        return result;
    }
   
}

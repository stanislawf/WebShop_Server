/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.test.bytetransformer;

import info.novatec.webshop.helpers.ByteTransformer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sf
 */
public class TestByteTransformer {

    @Test
    public void testImageToByteArrayTransformer(){
        String s = getClass().getClassLoader().getResource("articleData/smartphones/Apple/iphone6.jpg").getFile();
        
        assertNotNull("String is Null", s);
        assertNotNull("Byte Array is Null", ByteTransformer.getBytesOfFile(s));
    }
}

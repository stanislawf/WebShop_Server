/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.test.passwordsecure;


import info.novatec.webshop.helpers.PasswordEncryption;
import java.security.NoSuchAlgorithmException;
import junit.framework.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author sf
 */
public class PasswordSecure {
    @Test
    public void testPasswordSecure() throws NoSuchAlgorithmException{
        String password ="password";
        String md5Hash = "5f4dcc3b5aa765d61d8327deb882cf99";
        String securedPassword = PasswordEncryption.securePassword(password);
        assertNotNull("securedPassword is Null", securedPassword);
        Assert.assertEquals(md5Hash, securedPassword);
    }
}

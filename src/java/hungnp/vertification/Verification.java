/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.vertification;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Win 10
 */
public class Verification {
    
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LeaElwaAAAAAJhGG2TH1BuZthhDBdmECeeX4_p_";
    
    public static boolean verifyRecaptcha(String gRecaptchaResponse){
        try {
            if(gRecaptchaResponse==null || gRecaptchaResponse.trim().isEmpty()){
                return false;
            }
            URL verifiedURL = new URL(SITE_VERIFY_URL);
            //open connection to this url
            HttpURLConnection connection = (HttpURLConnection) verifiedURL.openConnection();
            
            //set method POST for request to the url
            connection.setRequestMethod("POST");
            
            //data send to server is post parameters: secret, response, remoteip(optional)
            //more infomation: https://developers.google.com/recaptcha/docs/verify#api_request
            String params = "secret="+SECRET_KEY+"&response="+gRecaptchaResponse;
            
            //set for writing params bytes to the url connection
            connection.setDoOutput(true);
            
            //write params bytes to the url connection by OutputStream 
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();
            
            //get response code from url connection
            int responseCode = connection.getResponseCode();
//            System.out.println("response code:"+responseCode);
            
            //the response is a JSON object: key:value
            //more information: https://www.google.com/recaptcha/api/siteverify
            //get jsonObject by InputStream and JsonReader
            InputStream inputStream = connection.getInputStream();
//            System.out.println("in:"+inputStream.toString());
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            inputStream.close();
            
            //if "success": true
//            System.out.println("jsonObject:"+jsonObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String verifyEmail(String email) throws AddressException, MessagingException{
        
        // setup Mail Server:  Simple Mail Transfer Protocol: smtp to send mail
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
        //587 là cổng SMTP Gmail - TLS (thay thế SSL vì lỗi thời)
        mailServerProperties.put("mail.smtp.port", "587");
        //auth : xác thực
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        //get Mail Session
        Session mailSession = Session.getDefaultInstance(mailServerProperties, null);
        Message message = new MimeMessage(mailSession);

        //received user
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //title
        message.setSubject("Code Verification");
        Random random = new Random();
        int codeVerification = 0 + random.nextInt(999999);
        message.setText("Code to verify your account: "+codeVerification);
        
        //Send mail
        Transport transport = mailSession.getTransport("smtp");
        //smtp.gmail.com là địa chỉ máy chủ SMTP Gmail
        transport.connect("sieunhangao101a@gmail.com", "phihungadgjmptw");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
        return codeVerification+"";
    }
    
}












































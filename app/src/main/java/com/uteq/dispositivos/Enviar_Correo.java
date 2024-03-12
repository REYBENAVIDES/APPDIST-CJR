package com.uteq.dispositivos;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Enviar_Correo extends AsyncTask<Void, Void, Void> {

    private String de, para, asunto, cuerpo;

    public Enviar_Correo(String from, String to, String subject, String body) {
        this.de = from;
        this.para = to;
        this.asunto = subject;
        this.cuerpo = body;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        sendMail();
        return null;
    }

    private void sendMail() {
        String host = "smtp.gmail.com";
        String username = "ereyb@uteq.edu.ec"; // Reemplaza con tu dirección de correo de Gmail
        String password = "bofn xvtv cswq tdty"; // Reemplaza con tu contraseña de Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(de,"[Dispositivos]"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);

            // Aquí puedes agregar código para manejar el éxito del envío de correo
        } catch (MessagingException e) {
            // Aquí puedes agregar código para manejar errores
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

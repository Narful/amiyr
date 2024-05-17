package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilisateur {
   public static int idCpt;
   public static String nom;
   public static String prenom;
   public static String email;
   public static String motDePasse;
   public static String role;

   public static Boolean CheckEmail(String email) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT * FROM utilisateur WHERE email = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            return statement.executeQuery().next();
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   } // Vérifier si l'email existe déjà dans la base de données

   public Boolean modifierCompte(int idCpt, String nom, String prenom, String email, String motDePasse) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, motDePasse = ? WHERE idCpt = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, motDePasse);
            statement.setInt(5, idCpt);
            statement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public static String[] consulterCompte() {
      String[] compteInfo = { nom, prenom, email, motDePasse };
      return compteInfo;
   }

   public static Boolean seConnecter(String email, String motDePasse) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT * FROM utilisateur WHERE email = ? AND motDePasse = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, motDePasse);
            try (ResultSet resultSet = statement.executeQuery()) {
               if (resultSet.next()) {
                  idCpt = resultSet.getInt("idCpt");
                  nom = resultSet.getString("nom");
                  prenom = resultSet.getString("prenom");
                  Utilisateur.email = resultSet.getString("email");
                  Utilisateur.motDePasse = resultSet.getString("motDePasse");
                  role = resultSet.getString("role");
                  return true;
               } else {
                  return false;
               }
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public static void seDéconnecter() {
      idCpt = 0;
      nom = null;
      prenom = null;
      email = null;
      motDePasse = null;
      role = null;
   }

   public static String generatePassword() {
      String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      StringBuilder password = new StringBuilder();
      Random random = new Random();

      for (int i = 0; i < 8; i++) {
         int index = random.nextInt(characters.length());
         password.append(characters.charAt(index));
      }

      return password.toString();
   }

   public static void sendEmail(String toEmail, String newPassword) {
      // Sender's email address and password
      final String username = "ayatnourhachmi@gmail.com";
      final String password = "hywu iihe mobs ljzu";

      // SMTP server details
      String host = "smtp.gmail.com";
      int port = 587;

      // Create properties object
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", port);

      try {
         // Create session
         Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

         // Create message
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(username));
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
         message.setSubject("AMIYR - Réinitialisation du mot de passe");
         message.setText("Cher utilisateur,\n\nVotre nouveau mot de passe est : " + newPassword
                 + "\n\nVeuillez le garder confidentiel.");

         // Send message
         Transport.send(message);

         System.out.println("Email envoyé avec succès à : " + toEmail);
      } catch (MessagingException e) {
         System.out.println("Échec de l'envoi de l'email à : " + toEmail);
         e.printStackTrace();
      }
   }

   public static Boolean motDePasseOublie(String email) {
      String nouveauMotDePasse = generatePassword();
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "UPDATE Utilisateur SET motDePasse = ? WHERE email = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nouveauMotDePasse);
            statement.setString(2, email);
            statement.executeUpdate();
            sendEmail(email, nouveauMotDePasse);
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
}

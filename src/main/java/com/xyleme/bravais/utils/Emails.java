package com.xyleme.bravais.utils;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.web.WebPage.ENVIRONMENT;

public class Emails {
    private Store store = null;
    private Folder inbox = null;
    private Message messages[] ;
    private List<String> list;

    /**
     * Gets email body of the last email in the inbox list (the oldest one).
     * @return {@code String[]}
     */
    public String[] getMails() {
        list = new ArrayList<>();
        int i = 0;

        getConnection();
        getInboxMessages();

        if (messages.length == 0) {

            while (messages.length == 0 && i < 40) {
                staticSleep(1);
                getInboxMessages();
                i++;
            }
        }
        processMessageBody(messages[0]);

        if (messages != null && messages.length > 0) {
           // deleteMessage(messages[0]);
            closeSession();
        }
        return list.get(0).split("\r\n");
    }

    /**
     * Gets number of all inbox emails.
     * @return {@code int}
     */
    public int getInboxMessagesCount() {
        int i = 0;

        getConnection();
        getInboxMessages();

        if (messages.length == 0) {

            while (messages.length == 0 && i < 20) {
                staticSleep(0.5);
                getInboxMessages();
                i++;
            }
        }
        return messages.length;
    }

    /**
     * Establishes connection to the mailing service.
     */
    private void getConnection() {

        switch (ENVIRONMENT.env.get("tenantWhichUsesMailTrapService")) {
            case "yes":
                getConnectionToMailTrapService();
                break;
            case "no":
                getConnectionToMSOfficeMailService();
                break;
        }
    }

    /**
     * Gets connection to MailTrap service.
     */
    private void getConnectionToMailTrapService() {
        Properties properties = new Properties();
        properties.setProperty("mail.pop3.host", "smtp.mailtrap.io");
        properties.setProperty("mail.pop3.port", "9950");
        properties.setProperty("mail.starttls.enable", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("a17c36ae8a4056", "63e34458e5a91a");
                    }
                });
        try {
            store = session.getStore("pop3");
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets connection to MS office 365 mail service.
     */
    private void getConnectionToMSOfficeMailService() {
        Properties properties = new Properties();
        properties.setProperty("mail.host", "outlook.office365.com");
        properties.setProperty("mail.port", "995");
        properties.setProperty("mail.transport.protocol", "pop3");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "automation-testing@xyleme.com", "gpmwnAZgOI8YHsAp4Kct");
                    }
                });
        try {
            store = session.getStore("pop3s");
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all inbox emails.
     *
     * @return {@code Message[]}
     */
    private Message[] getInboxMessages() {

        try {

            if (inbox != null) {
                messages = inbox.getMessages();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private void closeSession() {

        if (inbox != null && store != null) {

            try {
                inbox.close(true);
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets subject of the last email in the inbox list (the oldest one).
     *
     * @return {@code String}
     */
    public String getMailSubject() {
        String subject = "";

        getConnection();
        getInboxMessages();

        try {
            subject = messages[0].getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return subject;
    }

    /**
     * Gets name of sender of the last email in the inbox list (the oldest one).
     *
     * @return {@code String}
     */
    public String getMailSender() {
        String sender = "";

        getConnection();
        getInboxMessages();

        try {
            sender = messages[0].getFrom()[0].toString();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return sender;
    }

    /**
     * Gets name of receiver of the last email in the inbox list (the oldest one).
     *
     * @return {@code String}
     */
    public String getReceiverName() {
        String receiver = "";

        getConnection();
        getInboxMessages();

        try {
            receiver = String.valueOf(messages[0].getRecipients(Message.RecipientType.TO)[0]);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return receiver;
    }

    /**
     * Deletes specific email message.
     *
     * @param message - Specifies the message
     */
    public void deleteMessage(Message message) {

        try {
            message.setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

     /**
     * Deletes all emails which exist in the inbox folder.
     */
    public void deleteMessages() {
        getConnection();
        Message[] messages = getInboxMessages();

        try {

            if (messages.length > 0) {

                for (Message message : messages) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        staticSleep(3);
    }

    /**
     * Gets the most recent email from the mailbox.
     *
     * @return {@code Message}
     */
    public Message getMostRecentEmail() {
        getConnection();
        return getInboxMessages()[0];
    }

    /**
     * Gets the most recent email sent from tenant.
     *
     * @return {@code Message}
     */
    public Message getMostRecentEmailFromTenant() {
        int i = 0;
        List<Message> messagesSentFromTenant = new ArrayList<>();

        getConnection();
        Message[] inboxMessages = getInboxMessages();

        while (messagesSentFromTenant.size() > 0 || i < 50) {

            if (inboxMessages.length > 0) {
                messagesSentFromTenant = getTenantEmails();
            } else if (messagesSentFromTenant.size() > 0) {
                break;
            } else {
                staticSleep(1);
                messagesSentFromTenant = getTenantEmails();
                i++;
            }
        }
        return messagesSentFromTenant.get(messagesSentFromTenant.size() - 1);
    }

    /**
     * Gets emails sent to a specific user.
     *
     * @param userNameAndAddress - Specifies username and email address of the user
     * @return {@code List<Message>}
     */
    public List<Message> getEmailsSentToSpecificUser(String userNameAndAddress) {
        List<Message> usersMessages = new ArrayList<>();

        getConnection();

        for (Message message : getInboxMessages()) {

            try {

                if (message.getRecipients(Message.RecipientType.TO)[0].toString().equals(userNameAndAddress)) {
                    usersMessages.add(message);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return usersMessages;
    }

    /**
     * Gets email messages sent from tenant.
     *
     * @return {@code List<Message>}
     */
    private List<Message> getTenantEmails() {
        List<Message> messagesSentFromTenant = new ArrayList<>();

        getConnection();

        for (Message message : getInboxMessages()) {

            try {

                if (message.getFrom()[0].toString().contains(ENVIRONMENT.env.get("TENANT"))) {
                    messagesSentFromTenant.add(message);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return messagesSentFromTenant;
    }

    /**
     * Gets body of a specific email.
     *
     * @param email - Specifies the email
     * @return {@code String[]}
     */
    public String[] getEmailBody(Message email) {
        list = new ArrayList<>();

        getConnection();

        processMessageBody(email);

        if (messages != null && messages.length > 0) {
            closeSession();
        }
        return list.get(0).split("\\r?\\n");
    }

    /**
     * Gets subject of a specific email.
     *
     * @param message - Specifies the email
     * @return {@code String}
     */
    public String getSubjectOfSpecificEmail(Message message) {
        String subject = "";

        getConnection();

        try {
            subject = message.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return subject;
    }

    /**
     * Gets name of sender of a specific email.
     *
     * @param message - Specifies the email
     * @return {@code String}
     */
    public String getSenderNameOfSpecificEmail(Message message) {
        String sender = "";

        getConnection();

        try {
            sender = message.getFrom()[0].toString();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return sender;
    }

    /**
     * Gets name of receiver of a specific email.
     *
     * @param message - Specifies the email
     * @return {@code String}
     */
    public String getReceiverNameOfSpecificEmail(Message message) {
        String receiver = "";

        getConnection();

        try {
            receiver = String.valueOf(message.getRecipients(Message.RecipientType.TO)[0]);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeSession();
        return receiver;
    }

    /**
     * Deletes emails received from the tenant.
     */
    public void deleteMessagesReceivedFromTenant() {
        getConnection();
        Message[] messages = getInboxMessages();

        try {

            if (messages.length > 0) {

                for (Message message : messages) {

                    if (message.getFrom()[0].toString().contains(ENVIRONMENT.env.get("tenantNumber"))) {
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        staticSleep(3);
        closeSession();
    }

    /**
     * Processes the message body.
     *
     * @param message - Specifies the message the body of which is intended to be processed
     */
    private void processMessageBody(Message message) {

        try {
            Object content = message.getContent();
            // check for string
            // then check for multipart

            if (content instanceof String) {
                System.out.println(content);
            } else if (content instanceof Multipart) {
                Multipart multiPart = (Multipart) content;
                processMultiPart(multiPart);
            } else if (content instanceof InputStream) {
                InputStream inStream = (InputStream) content;
                int ch;

                while ((ch = inStream.read()) != -1) {
                    System.out.write(ch);
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the multipart message content.
     *
     * @param content - Specifies the content intended to be processed
     */
    private void processMultiPart(Multipart content) {

        try {
            int multiPartCount = content.getCount();

            for (int i = 0; i < multiPartCount; i++) {
                BodyPart bodyPart = content.getBodyPart(i);
                Object o;
                o = bodyPart.getContent();

                if (o instanceof String) {
                    //System.out.println(o);
                    list.add((String) o);
                } else if (o instanceof Multipart) {
                    processMultiPart((Multipart) o);
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtil {
    public static void sendMail(String to, String subject, String content) throws Exception {
        final String username = "trankimthang0207@gmail.com"; // tài khoản gửi
        final String password = "klzd ompr qmfn aywv"; // App Password Gmail

        System.out.println("[EMAILUTIL] Bắt đầu gửi mail...");
        System.out.println("  To: " + to);
        System.out.println("  Subject: " + subject);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("[EMAILUTIL] Đang xác thực SMTP...");
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));


            message.setContent(content, "text/html; charset=UTF-8");

            System.out.println("[EMAILUTIL] Đang gửi mail...");
            Transport.send(message);
            System.out.println("[EMAILUTIL] Gửi mail thành công tới: " + to);
        } catch (Exception e) {
            System.out.println("[EMAILUTIL] LỖI khi gửi mail:");
            e.printStackTrace();
            throw e; // Nếu muốn bắn lỗi lên servlet để xử lý tiếp
        }
    }

    // ================== HÀM MAIN TEST ==================
    public static void main(String[] args) {
        try {
            String to = "trankimthang857@gmail.com"; // Đổi thành email bạn muốn test nhận
            String subject = "Thử nghiệm gửi mail từ Java";
            String content = "<h2>Chào bạn!</h2><p>Đây là email test gửi từ Java NetBeans (class EmailUtil).</p>";
            sendMail(to, subject, content);
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi test gửi mail:");
            e.printStackTrace();
        }
    }
}

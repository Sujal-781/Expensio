package in.sujal.expensio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    private static final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";

    // Basic email
    public void sendEmail(String to, String subject, String body) {
        sendEmailWithAttachment(to, subject, body, null, null);
    }

    // Email with optional attachment
    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment, String filename) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", Map.of("name", "Expensio", "email", "chsujal8@gmail.com"));
            payload.put("to", List.of(Map.of("email", to)));
            payload.put("subject", subject);
            payload.put("htmlContent", "<html><body>" + body + "</body></html>");

            // ✅ Add attachment if provided
            if (attachment != null && filename != null) {
                String base64Attachment = Base64.getEncoder().encodeToString(attachment);
                payload.put("attachment", List.of(Map.of(
                        "content", base64Attachment,
                        "name", filename
                )));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(BREVO_URL, request, String.class);

            System.out.println("Brevo email response: " + response.getStatusCode() + " " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}

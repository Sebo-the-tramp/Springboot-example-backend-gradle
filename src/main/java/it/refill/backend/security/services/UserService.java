package it.refill.backend.security.services;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import it.refill.backend.models.users.UserAuth;
import it.refill.backend.models.users.VerificationToken;
import it.refill.backend.repository.users.VerificationTokenRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void createVerificationToken(UserAuth user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public boolean deleteVerificationToken(VerificationToken token) {
        try {
            tokenRepository.delete(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void confirmRegistration(UserAuth user, String appUrl) {
        // UserAuth user = event.getUser();
        String token = UUID.randomUUID().toString();
        this.createVerificationToken(user, token);

        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation";
        String confirmationUrl = appUrl + "/api/auth/registrationConfirm?token=" + token;
        // String message = messages.getMessage("message.regSucc", null,
        // event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
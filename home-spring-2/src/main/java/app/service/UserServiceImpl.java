package app.service;

import app.dto.FindUserDTO;
import app.entities.PasswordResetToken;
import app.entities.User;
import app.mail.EmailService;
import app.repositories.PasswordResetTokenRepository;
import app.repositories.UserRepository;
import app.search.SearchCriteria;
import app.search.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           EmailService emailService) {

        this.userRepository=userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService=emailService;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User getById(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" User not found for id :: " + id);
        }
        return user;
    }

    @Override
    public void deleteById(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findUserPaginated(FindUserDTO findUserDTO, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);


        Specification<User> query =
                Specification
                        .where(new SearchSpecification(new SearchCriteria("name", ":", findUserDTO.getName())))
                        .and(new SearchSpecification(new SearchCriteria("email", ":", findUserDTO.getEmail())));

        return this.userRepository.findAll(query, pageable);
    }

    @Override
    public void resetPasswordSendEmail(User user, String domain) {
        PasswordResetToken passwordResetToken;
        String token = UUID.randomUUID().toString();
        Optional<PasswordResetToken> optional = passwordResetTokenRepository.findById(user.getId());

        if (optional.isPresent()) {
            passwordResetToken = optional.get();
            passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis()+PasswordResetToken.getEXPIRATION()));
            passwordResetToken.setToken(token);
        }
        else
            passwordResetToken=new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);

        final String url =domain  + "/user/changePassword?token=" + token;

        String subject = "Reset Password";
        String text = "Щоб відновити пароль нажміть на силку \r\n" +
                "<a href = '"+url+"'>Відновити</a>";
        emailService.sendMimeMessage(user.getEmail(),subject,text);
    }


}

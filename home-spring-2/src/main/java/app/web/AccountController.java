package app.web;

import app.dto.FindUserDTO;
import app.dto.ForgotPasswordDTO;
import app.entities.PasswordResetToken;
import app.entities.User;
import app.mail.EmailService;
import app.repositories.PasswordResetTokenRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.core.env.Environment;
import java.util.*;

@Controller
public class AccountController {

    private final UserRepository userRepository;
    private final UserService userService;
    //private final Environment env;

    @Autowired
    public AccountController(UserRepository userRepository,
                             UserService userService

                             //Environment env,
                             ) {
        this.userRepository = userRepository;
        this.userService=userService;

        //this.env=env;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        return "forgotPassword";
    }

    @PostMapping("/sendMessagePassword")
    public String sendResetPassword(final HttpServletRequest request,
                                    @Valid ForgotPasswordDTO forgotPasswordDTO,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "forgotPassword";
        }
        //Send message email
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
        if(user==null)
        {
            model.addAttribute("error", "Дана пошта відсутня");
            return "forgotPassword";
        }
        String domain = getAppUrl(request);
        userService.resetPasswordSendEmail(user, domain);


        return "sendEmailSuccess";
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }




}

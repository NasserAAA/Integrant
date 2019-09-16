package com.example.demo.controller;


import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.EmailExistsException;
import com.example.demo.exceptions.PasswordsNotMatchingException;
import com.example.demo.model.UserRecord;
import com.example.demo.services.ConfirmationTokenService;
import com.example.demo.services.EmailSenderService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ValidationSequence;
import com.example.demo.model.ConfirmationToken;;
@RestController
public class RegistrationController {
	@Autowired
	private UserService userService; 
	@Autowired
	private ConfirmationTokenService confirmationTokenService; 
	@Autowired
    private EmailSenderService emailSenderService;
	@GetMapping(value = "/user/registration")
	public ModelAndView showRegistrationForm(WebRequest request, ModelAndView modelAndView) {
	    UserDto userDto = new UserDto();
	    userDto.setName("test");
	    modelAndView.addObject("user", userDto);
	    modelAndView.setViewName("registration");
	    return modelAndView;
	}
	
	@PostMapping(value = "/user/registration")
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") @Validated(ValidationSequence.class) @Valid UserDto accountDto, 
	  BindingResult result, 
	  WebRequest request, 
	  Errors errors) {
	    
		if (result.hasErrors()) {
	        return new ModelAndView("registration", "user", accountDto);
	    }
	     
	    UserRecord registered = null;
	    try {
	        registered = userService.registerNewUserAccount(accountDto);
	    } catch (EmailExistsException e) {
	    	result.rejectValue("email", "email.registered");
	        return new ModelAndView("registration", "user", accountDto);
	    } catch (PasswordsNotMatchingException e) {
	    	result.rejectValue("matchingPassword", "matchingPassword.notMatch");
	        return new ModelAndView("registration", "user", accountDto);
		}
	    if (registered == null) {
	        result.rejectValue("email", "email.registered");
	        return new ModelAndView("registration", "user", accountDto);
	    }
	    else
	    {
            ConfirmationToken confirmationToken=confirmationTokenService.createConfirmationToken(registered);
	        SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(registered.getEmail());
	        mailMessage.setSubject("Complete Registration!");
	        mailMessage.setFrom("marwanayman1998@gmail.com");
	        mailMessage.setText("To confirm your account, please click here : "
	        +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

	        emailSenderService.sendEmail(mailMessage);
	        ModelAndView modelAndView=new ModelAndView();
	        modelAndView.addObject("emailId", registered.getEmail());
	        modelAndView.setViewName("successfulRegisteration");
	        return modelAndView;
	    }
	}
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {	
		ConfirmationToken token = confirmationTokenService.getConfirmationToken(confirmationToken);
        if(token != null)
        {	
            UserRecord user = userService.getUserByEmail(token.getUser().getEmail());
            if (!user.isEnabled())
            {
            	Calendar cal = Calendar.getInstance();
                if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            		modelAndView.addObject("message", "Expired Token");
            		modelAndView.setViewName("expiredToken");
                }
                else
                {
                    user.setEnabled(true);
                	userService.updateUser(user);
                    modelAndView.setViewName("accountVerified");
                }
            }
            else
            {
            	modelAndView.addObject("message", "This account is already verified");
        		modelAndView.setViewName("alreadyVerified");
            }
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("invalidToken");
        }
        return modelAndView;
    }
	
// 			Resend Verification
	@GetMapping(value = "/user/resendRegistrationToken")
	@ResponseBody
	public ModelAndView resendRegistrationToken(
	  HttpServletRequest request, @RequestParam("token") String existingToken) {
		ModelAndView modelAndView=new ModelAndView();
        ConfirmationToken token = confirmationTokenService.getConfirmationToken(existingToken);
        if(token!=null)
        {
    	    UserRecord user = userService.getUserByEmail(token.getUser().getEmail());
    	    if(!user.isEnabled())
    	    {
    	        token.setExpiryDate();
    	        confirmationTokenService.updateToken(token);
    		    SimpleMailMessage mailMessage = new SimpleMailMessage();
    	        mailMessage.setTo(user.getEmail());
    	        mailMessage.setSubject("Resend Registration Token");
    	        mailMessage.setFrom("marwanayman1998@gmail.com");
    	        mailMessage.setText("To confirm your account, please click here : "
    	        +"http://localhost:8080/confirm-account?token="+token.getConfirmationToken());

    	        emailSenderService.sendEmail(mailMessage);
    	        modelAndView.addObject("emailId", user.getEmail());
    	        modelAndView.setViewName("successfulRegisteration");
    	    }
    	    else
    	    {	
    	    	modelAndView.addObject("message", "This account is already verified");
        		modelAndView.setViewName("alreadyVerified");
    	    }
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("invalidToken");
        }
		return modelAndView;
	}
	
}

package siit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import siit.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private int value = 0;

    @RequestMapping(method = RequestMethod.GET)
    protected String displayLoginForm() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView performLogin(HttpSession session,
                                        @RequestParam String user, @RequestParam String password) {

        ModelAndView mav = new ModelAndView();

        if (user.equals(password)) {
            session.setAttribute("logged_user", user);
            mav.setViewName("redirect:/customers");
        } else {
            value++;
            String error = "User and password do not match! " + value;
            mav.setViewName("login");
            mav.addObject("error", error);
        }

        return mav;
    }
}

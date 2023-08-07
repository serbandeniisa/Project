package siit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import siit.ValidationException;
import siit.model.Customer;
import siit.model.Order;
import siit.sevices.CustomerService;
import siit.sevices.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayCustomers(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("customers-list");
        mav.addObject("customers", customerService.getCustomers());

        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/edit")
    public ModelAndView displayCustomerEditForm(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("customer-edit");
        mav.addObject("customer", customerService.getCustomerById(id));
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/edit")
    public ModelAndView performCustomerEdit(@ModelAttribute Customer customer) {
        ModelAndView mav = new ModelAndView();
        try {
            customerService.updateCustomer(customer);
            mav.setViewName("redirect:/customers");
        } catch (ValidationException ex) {
            mav.setViewName("customer-edit");
            mav.addObject("error", ex.getMessage());
        }
        return mav;
    }


    @GetMapping("{customer_id}/orders")
    public ModelAndView displayCustomerOrders(@PathVariable("customer_id") int customerId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer-orders");
        modelAndView.addObject("customer", customerService.getCustomerById(customerId));

        return modelAndView;
    }

    @GetMapping("/{customer_id}/orders/{order_id}/delete")
    public ModelAndView deleteCustomerOrder(@PathVariable("customer_id") int customerId,@PathVariable("order_id") int orderId){
  //delete for an order by orderId
        ModelAndView modelAndView = new ModelAndView();
            orderService.deleteOrder(orderId);
            modelAndView.setViewName("customer-orders");
            modelAndView.addObject("customer", customerService.getCustomerById(customerId));

        return modelAndView;
    }
}

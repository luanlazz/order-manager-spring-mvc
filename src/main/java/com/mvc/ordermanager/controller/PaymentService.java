package com.mvc.ordermanager.controller;

import com.mvc.ordermanager.dao.ICustomerDAO;
import com.mvc.ordermanager.dao.IOrderDAO;
import com.mvc.ordermanager.dao.IPaymentDAO;
import com.mvc.ordermanager.model.EmailTuple;
import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.Customer;
import com.mvc.ordermanager.resource.avro.OrderState;
import com.mvc.ordermanager.resource.avro.Payment;
import com.mvc.ordermanager.service.IEmailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/payments")
public class PaymentService {

    private static final String CALL_TIMEOUT = "100";

    private final IOrderDAO orderDAO;
    private final ICustomerDAO customerDAO;
    private final IPaymentDAO paymentDAO;
    private final IEmailer emailer;

    @Autowired
    public PaymentService(IOrderDAO orderDAO, ICustomerDAO customerDAO, IPaymentDAO paymentDAO, IEmailer emailer) {
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
        this.paymentDAO = paymentDAO;
        this.emailer = emailer;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<?> receivePayment(@PathVariable final Integer orderId,
                                            @RequestParam final String ccy,
                                            @RequestParam final Double amount,
                                            @RequestParam(defaultValue = CALL_TIMEOUT) final Long timeout) {
        try {
            OrderBean bean = this.orderDAO.getOrder(orderId, timeout.intValue());

            if (bean.getState() == OrderState.FAILED) {
                return new ResponseEntity<>("Payment for invalid order", HttpStatus.BAD_REQUEST);
            }

            if (bean.getQuantity() * bean.getPrice() > amount) {
                return new ResponseEntity<>("Amount insufficient", HttpStatus.BAD_REQUEST);
            }

            Payment payment = new Payment(this.paymentDAO.getNextId(timeout.intValue()).toString(), orderId.toString(), ccy, amount);
            this.paymentDAO.savePayment(payment, timeout.intValue());

            Customer customer = this.customerDAO.getCustomer(bean.getcustomerId(), timeout.intValue());

            this.emailer.sendEmail(new EmailTuple(bean, payment, customer));

            return ResponseEntity.created(URI.create("/api/payments")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error to process payment", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.mvc.ordermanager.service;

import com.mvc.ordermanager.model.EmailTuple;

public interface IEmailer {

    void sendEmail(final EmailTuple details);
}

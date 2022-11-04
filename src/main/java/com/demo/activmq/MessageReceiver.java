package com.demo.activmq;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    MessageConverter messageConverter;

    public String receiveMessage(){
        try        {
            LOGGER.info("Stared MessageReceiver:receiveMessage()");

            /*
             * Here we receive the message.
             */
            Message message = jmsTemplate.receive();
            String response = (String) messageConverter.fromMessage(message);
            LOGGER.info("MessageReceiver:receiveMessage() message received successfully");

            return response;
        }
        catch (Exception exe){
            exe.printStackTrace();
        }
        return null;
    }
}

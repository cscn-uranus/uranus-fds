package com.cscn.uranus.fds;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.flight.entity.FlightRawMsg;
import com.cscn.uranus.fds.flight.repo.FlightRawMsgRepo;
import com.cscn.uranus.fds.flowcontrol.entity.FlowcontrolRawMsg;
import com.cscn.uranus.fds.flowcontrol.repo.FlowcontrolRawMsgRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

//        FlightRawMsg flightRawMsg = new FlightRawMsg();
//        FlowcontrolRawMsg flowcontrolRawMsg = new FlowcontrolRawMsg();
//
//        FlightRawMsgRepo flightRawMsgRepo = context.getBean(FlightRawMsgRepo.class);
//        FlowcontrolRawMsgRepo flowcontrolRawMsgRepo = context.getBean(FlowcontrolRawMsgRepo.class);
//
//        flightRawMsgRepo.save(flightRawMsg);
//        flowcontrolRawMsgRepo.save(flowcontrolRawMsg);
    }

}

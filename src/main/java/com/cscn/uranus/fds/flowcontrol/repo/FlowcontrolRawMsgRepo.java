package com.cscn.uranus.fds.flowcontrol.repo;

import com.cscn.uranus.fds.flowcontrol.entity.FlowcontrolRawMsg;
import org.springframework.data.repository.CrudRepository;



public interface FlowcontrolRawMsgRepo extends CrudRepository<FlowcontrolRawMsg, Long> {
}

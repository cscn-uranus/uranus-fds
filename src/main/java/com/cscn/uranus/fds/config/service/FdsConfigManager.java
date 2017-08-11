package com.cscn.uranus.fds.config.service;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FdsConfigManager {
    public enum OperationStatus {
        Multiple,
        None,
        Success
    }
    private final FdsConfigRepo fdsConfigRepo;


    @Autowired
    public FdsConfigManager(FdsConfigRepo fdsConfigRepo) {
        this.fdsConfigRepo = fdsConfigRepo;
    }

    @Transactional
    public OperationStatus setConfigItem(String code, String value) {
        long length = this.fdsConfigRepo.findByCode(code).size();
        if (length > 1) {
            return OperationStatus.Multiple;
        } else if (length < 1) {
            return OperationStatus.None;
        } else {
            FdsConfig config = this.fdsConfigRepo.findByCode(code).get(0);
            config.setValue(value);
            this.fdsConfigRepo.save(config);
            return OperationStatus.Success;
        }
    }

    public boolean setFlightRawMsgLength(long length) {
        String value = String.valueOf(length);
        String code = "FlightRawMsgLength";
        OperationStatus status = this.setConfigItem(code, value);
        return status == OperationStatus.Success;
    }
    public boolean setFlightRawMsgIndex(long index) {
        String value = String.valueOf(index);
        String code = "FlightRawMsgIndex";
        OperationStatus status = this.setConfigItem(code, value);
        return status == OperationStatus.Success;
    }
    public boolean setFlowcontrolRawMsgLength(long length) {
        String value = String.valueOf(length);
        String code = "FlowcontrolRawMsgLength";
        OperationStatus status = this.setConfigItem(code, value);
        return status == OperationStatus.Success;
    }
    public boolean setFlowcontrolRawMsgIndex(long index) {
        String value = String.valueOf(index);
        String code = "FlowcontrolRawMsgIndex";
        OperationStatus status = this.setConfigItem(code, value);
        return status == OperationStatus.Success;
    }
}

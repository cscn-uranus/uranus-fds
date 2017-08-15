package com.cscn.uranus.fds.external.data.provider;

import com.cscn.uranus.fds.FdsProperties;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

@Service
public class AmqManager {

    private final FdsProperties fdsProperties;
    private String jmxUrlString;
    private final String[] jmxCredential = {"admin", "admin"};
    private final JmsTemplate jmsTemplate;
    private Map<String, String[]> jmxEnvironment = new HashMap<>();
    private JMXServiceURL jmxServiceURL;
    private JMXConnector jmxConnector;
    private MBeanServerConnection mBeanServerConnection;
    private ObjectName amqInstance;
    private BrokerViewMBean brokerViewMBean;

    @Autowired
    public AmqManager(FdsProperties fdsProperties, JmsTemplate jmsTemplate) {
        this.fdsProperties = fdsProperties;
        this.jmsTemplate = jmsTemplate;

        this.initJmxConnection();
    }

    public void sendXmlMsg(Destination destination, String message) {
        this.jmsTemplate.convertAndSend(destination, message);
    }


    private void initJmxConnection() {
        try {
            this.jmxUrlString = "service:jmx:rmi:///jndi/rmi://" + this.fdsProperties.getHost() + ":1099/jmxrmi";
            this.jmxServiceURL = new JMXServiceURL(this.jmxUrlString);
            this.jmxEnvironment.put(JMXConnector.CREDENTIALS, this.jmxCredential);

            this.jmxConnector = JMXConnectorFactory.connect(this.jmxServiceURL, this.jmxEnvironment);
            this.mBeanServerConnection = jmxConnector.getMBeanServerConnection();
            this.amqInstance = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
            this.brokerViewMBean = MBeanServerInvocationHandler.newProxyInstance(this.mBeanServerConnection, this.amqInstance, BrokerViewMBean.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQueue(String queueName) {
        try {
            brokerViewMBean.removeQueue(queueName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private QueueViewMBean getQueue(String queueName) {
        QueueViewMBean queueViewMBean = null;
        for (ObjectName name : this.brokerViewMBean.getQueues()) {
            QueueViewMBean queueItem = MBeanServerInvocationHandler.newProxyInstance(this.mBeanServerConnection, name, QueueViewMBean.class, true);
            if (queueItem.getName().equals(queueName)) {
                queueViewMBean = queueItem;
            }
        }
        return queueViewMBean;
    }

    public long getQueueSize(String queueName) {
        return this.getQueue(queueName).getQueueSize();
    }

}

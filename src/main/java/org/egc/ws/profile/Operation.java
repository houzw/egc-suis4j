package org.egc.ws.profile;

import org.egc.ws.driver.*;

/**
 * Class Operation.java
 * SUIS Operation
 *
 * @author Ziheng Sun
 * @time Dec 8, 2017 1:35:08 PM
 */
public class Operation {
    Message input, output;
    String name, description;
    String driverid;

    protected Operation() {
        input = new Message.Builder().build();
        output = new Message.Builder().build();
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public Message getInput() {
        return input;
    }

    public void setInput(Message input) {
        this.input = input;
    }

    public Message getOutput() {
        return output;
    }

    public void setOutput(Message output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {
        Operation o;

        public Builder() {
            o = new Operation();
        }

        public Builder parse(String descfile, ServiceType type) {
            AbstractDriver.Builder builder = null;
            switch (type) {
                case OGC:
                    builder = new OGCDriver.Builder();
                    break;
                case SOAP:
                    builder = new SOAPDriver.Builder();
                    break;
                case REST:
                    builder = new RESTDriver.Builder();
                    break;
            }
            AbstractDriver driver = builder.parse(descfile).build();
            DriverManager.add(driver);
//			o = driver.disgest();
            return this;
        }

        public Builder name(String n) {
            o.setName(n);
            return this;
        }

        public Builder description(String desc) {
            o.setDescription(desc);
            return this;
        }

        public Builder input(Message msg) {
            o.setInput(msg);
            return this;
        }

        public Builder output(Message msg) {
            o.setOutput(msg);
            return this;
        }

        public Builder driver(String did) {
            o.setDriverid(did);
            return this;
        }

        public Operation build() {
            return o;
        }
    }
}

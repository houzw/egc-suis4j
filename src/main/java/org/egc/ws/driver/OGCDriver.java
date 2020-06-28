package org.egc.ws.driver;

import org.egc.ws.profile.Message;
import org.egc.ws.profile.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class OGCWPS2SUNIS.java
 *
 * @author Ziheng Sun
 * @time Dec 8, 2017 10:23:52 AM
 */
public class OGCDriver extends AbstractDriver {
    String category = "wps";
    String version = "1.0.0";
    Object capa;
    String dataurl = null; //this variable is used to store the GetMap, GetCoverage, GetFeature URL link for service chaining
    Map<String, Object> processdescriptions; //operation name to process description
    Logger log = LoggerFactory.getLogger(this.getClass());

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public PayLoad encodeReq(Message msg) {
        Object content = null;
        return new PayLoad.Builder().content(content).build();
    }

    @Override
    public void fakesend(PayLoad req) {
        try {
            String resp = null;
            log.info(">> " + (String) req.getContent());
            if ("GetFeature".equals(this.getCurrentOperation())
                    || "DescribeFeatureType".equals(this.getCurrentOperation())
                    || "GetMap".equals(this.getCurrentOperation())) {
                //doGetFile
                String url = null;
                if (this.getAccessEndpoint().toString().endsWith("?")) {
                    url = this.getAccessEndpoint().toString();
                } else if (this.getAccessEndpoint().toString().contains("?")) {
                    url = this.getAccessEndpoint().toString() + "&";
                } else {
                    url = this.getAccessEndpoint().toString() + "?";
                }
                url += "service=" + category + "&version=" + version + "&request=" + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                this.dataurl = url;
            } else if ("GetCoverage".equals(this.getCurrentOperation())) {
                if (this.version.equals("2.0.0")) {
                    super.fakesend(req);
                } else if (this.version.equals("1.0.0")) {
                    //doGetFile
                    String url = null;
                    if (this.getAccessEndpoint().toString().endsWith("?")) {
                        url = this.getAccessEndpoint().toString();
                    } else if (this.getAccessEndpoint().toString().contains("?")) {
                        url = this.getAccessEndpoint().toString() + "&";
                    } else {
                        url = this.getAccessEndpoint().toString() + "?";
                    }
                    url += "service=" + category + "&version=" + version + "&request="
                            + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                    this.dataurl = url;
                } else {
                    throw new RuntimeException("The version is not supported");
                }
            } else if ("GetMap".equals(this.getCurrentOperation())) {
                //doGetFile
                String url = null;
                if (this.getAccessEndpoint().toString().endsWith("?")) {
                    url = this.getAccessEndpoint().toString();
                } else if (this.getAccessEndpoint().toString().contains("?")) {
                    url = this.getAccessEndpoint().toString() + "&";
                } else {
                    url = this.getAccessEndpoint().toString() + "?";
                }
                url += "service=" + category + "&version=" + version + "&request="
                        + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                this.dataurl = url;
            } else if ("GetCapabilities".equals(this.getCurrentOperation())) {
                resp = this.getOperation(this.getCurrentOperation()).getOutput().getValueAsString("capabilities");
            } else {
                super.fakesend(req);
            }
            log.info(">> " + resp);
            response = new PayLoad.Builder().content(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = new PayLoad.Builder().content(e.getLocalizedMessage()).build();
        }
    }

    @Override
    public void send(PayLoad req) {
        try {
            String resp = null;
            log.info(">> " + (String) req.getContent());
            if ("GetFeature".equals(this.getCurrentOperation())
                    || "DescribeFeatureType".equals(this.getCurrentOperation())
                    || "GetMap".equals(this.getCurrentOperation())) {
                String filename = "temp-" + UUID.randomUUID().toString();
                //doGetFile
                String url = null;
                if (this.getAccessEndpoint().toString().endsWith("?")) {
                    url = this.getAccessEndpoint().toString();
                } else if (this.getAccessEndpoint().toString().contains("?")) {
                    url = this.getAccessEndpoint().toString() + "&";
                } else {
                    url = this.getAccessEndpoint().toString() + "?";
                }
                url += "service=" + category + "&version=" + version + "&request=" + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                this.dataurl = url;
                HttpUtils.doGETFile(url, HttpUtils.TEMPORARY_PATH + filename);
                resp = "file:" + HttpUtils.TEMPORARY_PATH + filename;
            } else if ("GetCoverage".equals(this.getCurrentOperation())) {
                //download the file and save to a temporary file path
                String filename = "coverage-" + UUID.randomUUID().toString();
                if (this.version.equals("2.0.0")) {
                    //doPostFile
                    HttpUtils.doPostFile(this.getAccessEndpoint().toString(), String.valueOf(req.getContent()), HttpUtils.TEMPORARY_PATH + filename);
                } else if (this.version.equals("1.0.0")) {
                    //doGetFile
                    String url = null;
                    if (this.getAccessEndpoint().toString().endsWith("?")) {
                        url = this.getAccessEndpoint().toString();
                    } else if (this.getAccessEndpoint().toString().contains("?")) {
                        url = this.getAccessEndpoint().toString() + "&";
                    } else {
                        url = this.getAccessEndpoint().toString() + "?";
                    }
                    url += "service=" + category + "&version=" + version + "&request="
                            + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                    this.dataurl = url;
                    HttpUtils.doGETFile(url, HttpUtils.TEMPORARY_PATH + filename);
                } else {
                    throw new RuntimeException("The version is not supported");
                }
                resp = "file:" + HttpUtils.TEMPORARY_PATH + filename;
            } else if ("GetMap".equals(this.getCurrentOperation())) {
                String filename = "map-" + UUID.randomUUID().toString();
                //doGetFile
                String url = null;
                if (this.getAccessEndpoint().toString().endsWith("?")) {
                    url = this.getAccessEndpoint().toString();
                } else if (this.getAccessEndpoint().toString().contains("?")) {
                    url = this.getAccessEndpoint().toString() + "&";
                } else {
                    url = this.getAccessEndpoint().toString() + "?";
                }
                url += "service=" + category + "&version=" + version + "&request="
                        + this.getCurrentOperation() + "&" + String.valueOf(req.getContent());
                this.dataurl = url;
                HttpUtils.doGETFile(url, HttpUtils.TEMPORARY_PATH + filename);
                resp = "file:" + HttpUtils.TEMPORARY_PATH + filename;
            } else if ("GetCapabilities".equals(this.getCurrentOperation())) {
                //do nothing
                resp = this.getOperation(this.getCurrentOperation()).getOutput().getValueAsString("capabilities");
            } else {
                resp = HttpUtils.doPost(this.getAccessEndpoint().toString(), (String) req.getContent());
            }
            log.info(">> " + resp);
            response = new PayLoad.Builder().content(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = new PayLoad.Builder().content(e.getLocalizedMessage()).build();
        }
    }

    @Override
    public PayLoad receive() {
        return response;
    }

    @Override
    public Message decodeResp(PayLoad resp) {
        Operation oper = this.getOperation(this.getCurrentOperation());
        Message respmsg = oper.getOutput();
        return respmsg;
    }

    @Override
    public Message decodeSUIS(Object rawmsg) {
        return null;
    }

    @Override
    public Object encodeSUIS(Message msg) {
        //nothing
        return null;
    }

    @Override
    public void initParams(Operation o) {
        //nothing see WPS driver
    }

    @Override
    public List<Operation> digest() {
        connect();
        return this.getOperlist();
    }

    public static class Builder extends AbstractDriver.Builder {
        OGCDriver driver = null;

        @Override
        public Builder parse(String descfile) {
            //Parse the GetCapabilities URL. First, know what type of service it is.
            try {
                Map<String, String> querykvp = HttpUtils.splitQuery(new URL(descfile));
                if (querykvp.get("service") == null || querykvp.get("version") == null) {
                    throw new RuntimeException("The link doesn't specify the service category or version number.");
                }
                if ("wcs".equals(querykvp.get("service"))) {
                    if ("2.0.0".equals(querykvp.get("version"))) {
                        driver = new WCS200Driver();
                    } else if ("1.0.0".equals(querykvp.get("version"))) {
                        driver = new WCS100Driver();
                    }
                } else if ("wfs".equals(querykvp.get("service"))) {
                    if ("2.0.0".equals(querykvp.get("version"))) {
                        driver = new WFS200Driver();
                    }
                } else if ("wms".equals(querykvp.get("service"))) {
                    if ("1.3.0".equals(querykvp.get("version"))) {
                        driver = new WMS130Driver();
                    }
                } else if ("wps".equals(querykvp.get("service"))) {
                    if ("1.0.0".equals(querykvp.get("version"))) {
                        driver = new WPS100Driver();
                    }
                }
                if (driver == null) {
                    throw new RuntimeException("Fail to pull a suitable driver for this service. ");
                }
                driver.setCategory(querykvp.get("service"));
                driver.setVersion(querykvp.get("version"));
                descEndpoint(new URL(descfile));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public Builder accessEndpoint(URL url) {
            driver.setAccessEndpoint(url);
            return this;
        }

        @Override
        public Builder descEndpoint(URL url) {
            driver.setDescEndpoint(url);
            return this;
        }

        @Override
        public AbstractDriver build() {
            return driver;
        }
    }
}

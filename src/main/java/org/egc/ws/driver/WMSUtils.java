package org.egc.ws.driver;

import net.opengis.wms.v_1_3_0.HTTP;
import net.opengis.wms.v_1_3_0.Layer;
import net.opengis.wms.v_1_3_0.OperationType;
import net.opengis.wms.v_1_3_0.WMSCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * updated by Li Lin on 3/28/2016
 * <p>
 * updated by Ziheng Sun on 4/11/2016 - make the getting and parsing wms capability response work
 * <p>
 * Updated by Lei Hu on 4/18/2016 return the Layer to jsp
 *
 * @author Administrator
 */
public class WMSUtils {
    private static Logger logger = LoggerFactory.getLogger(WMSUtils.class);


    /**
     * Parse WMS capabilities document to Layer list
     * created by Lei Hu on 4/30/2016
     *
     * @return
     */
    public static List<Layer> parseWMS(String url) {

        List<Layer> layers = null;
        try {
            WMSCapabilities ca = WMSUtils.parseWMSCapabilityURL(url);
            logger.info("Layers : " + ca.getCapability().getLayer().getLayer().size());
            logger.info("WMS Name : " + ca.getCapability().getLayer().getName());
            logger.info("WMS Title: " + ca.getCapability().getLayer().getTitle());
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\nWMS Layer List:");
            layers = ca.getCapability().getLayer().getLayer();
            for (int i = 0; i < layers.size(); i++) {
                Layer l = layers.get(i);
                logger.info("Layer No : " + i);
                logger.info("Layer Title : " + l.getTitle());
                logger.info(l.getBoundingBox().get(0).getCRS());
                logger.info(l.getBoundingBox().get(0).getMaxy() + "");
                logger.info(l.getAttribution().getLogoURL().getFormat());
                logger.info("height=" + l.getAttribution().getLogoURL().getHeight());
                logger.info("width=" + l.getAttribution().getLogoURL().getWidth());
                if (l.getIdentifier().size() != 0) {
                    logger.info("Layer Identifier : " + l.getIdentifier().get(0));
                }
                logger.info("==================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layers;
    }

    public static URL getEndpoint(WMSCapabilities wmc) throws MalformedURLException {
        HTTP http = wmc.getCapability().getRequest().getGetMap().getDCPType().get(0).getHTTP();
        String exeurl = null;
        if (http.isSetGet()) {
            exeurl = http.getGet().getOnlineResource().getHref();
        } else {
            exeurl = http.getPost().getOnlineResource().getHref();
        }
        return new URL(exeurl);
    }

    /**
     * Parse WMS capabilities document
     * created by Lei Hu on 4/30/2016
     *
     * @param capability_doc_url
     * @return
     */
    public static WMSCapabilities parseWMSCapabilityURL(String capability_doc_url) {
        String getCapabilitiesResponse = null;
        WMSCapabilities ca = null;
        //added by Lei Hu 8/12/2016
        try {
            if (!capability_doc_url.contains("version")) {
                capability_doc_url = capability_doc_url + "&version=1.3.0";
            } else if (!capability_doc_url.contains("1.3.0")) {
                capability_doc_url = capability_doc_url.replace("1.3.2", "1.3.0");
            }
            getCapabilitiesResponse = HttpUtils.doGet(capability_doc_url);
            logger.info(getCapabilitiesResponse);
            JAXBContext jaxbContext = null;
            ca = JAXB.unmarshal(new StringReader(getCapabilitiesResponse), WMSCapabilities.class);
            //Added by Lei Hu
            //for DGIWG wms
            logger.info(ca.getCapability().getRequest().getGetCapabilities().getDCPType().get(0).getHTTP().getGet().getOnlineResource().getHref());
            logger.info(ca.getCapability().getRequest().getGetMap().getDCPType().get(0).getHTTP().getGet().getOnlineResource().getHref());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ca;
    }

    public static String getWMSGetCapabilitiesResponse(String prefix, String content) throws Exception {
        String url = prefix + content;
        return HttpUtils.doGet(url);
    }

    /**
     * Added by Lei Hu
     * Return the WMS Layer List
     */
    public static List<Layer> getWMSLayers(String wmsurl) throws Exception {
        wmsurl = wmsurl.trim();
        String wmsstr = wmsurl;
        String wmscont;
        if (wmsurl.endsWith("?")) {
            wmscont = "service=WMS&version=1.3.0&request=GetCapabilities";
        } else {
            wmscont = "?service=WMS&version=1.3.0&request=GetCapabilities";
        }
        return WMSUtils.parseWMS(wmsstr);
    }

    /**
     * Get Layer List of WMS
     *
     * @return
     */
    public static List<Layer> getLayerList(WMSCapabilities wmsc) {
        //updated by Lei Hu 8/13/2016
        //For the DGIWG WMS
        List<Layer> l = wmsc.getCapability().getLayer().getLayer().get(0).getLayer();
        for (int i = 0; i < l.size(); i++) {
            Layer ll = l.get(i);
            if (ll.getName() != null) {
                logger.info(ll.getName());
                logger.info(ll.getTitle());
            } else {
                for (int j = 0; j < ll.getLayer().size(); j++) {
                    Layer lll = ll.getLayer().get(j);
                    logger.info(lll.getName());
                    logger.info(lll.getTitle());
                }
            }
        }
        return l;
    }

    //updated by Lei Hu 8/16/2016
    public static List<Layer> getLayerL(WMSCapabilities wmsc) {
        List<Layer> l = wmsc.getCapability().getLayer().getLayer();
        for (int i = 0; i < l.size(); i++) {
            Layer ll = l.get(i);
            if (ll.getName() != null) {
                System.out.println(ll.getName());
                System.out.println(ll.getTitle());
            } else {
                for (int j = 0; j < ll.getLayer().size(); j++) {
                    Layer lll = ll.getLayer().get(j);
                    System.out.println(lll.getName());
                    System.out.println(lll.getTitle());
                }
            }
        }
        return l;
    }

    public static String convertJAXBElementToXML(JAXBElement ele) {
        StringWriter writer = new StringWriter();
        Marshaller m;
        try {
            JAXBContext context = JAXBContext.newInstance(OperationType.class);
            m = context.createMarshaller();
            m.marshal(ele, writer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // output string to console
        String theXML = writer.toString();
        logger.info(theXML);
        return theXML;
    }

    public static WMSCapabilities parseWMSCapabilityResponse(String xml) throws JAXBException {
        JAXBContext jaxbContext;
        WMSCapabilities ca = null;
        ca = JAXB.unmarshal(new StringReader(xml), WMSCapabilities.class);
        System.out.print(ca);
        return ca;
    }
}

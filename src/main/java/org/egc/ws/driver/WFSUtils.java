package org.egc.ws.driver;

import net.opengis.ows.v_1_1_0.Operation;
import net.opengis.wfs.v_2_0.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * updated and tested by Ziheng Sun on 3/16/2016
 * <p>
 * updated by Ziheng Sun on 4/29/2016 - to make the plan clear
 * <p>
 * This class is for WFS 2.0. Pay attention to this. WFS 1.0 will be supported in another class.
 *
 * @author Ziheng Sun
 */
public class WFSUtils {
    private static Logger theLogger = LoggerFactory.getLogger(WFSUtils.class);
    public static final String supported_version = "2.0";

    /**
     * Parse WFS capabilities document
     * created by Ziheng Sun on 4/29/2016
     *
     * @param capa the capa
     * @return wfs capabilities type
     */
    public static WFSCapabilitiesType parseCapabilities(String capa) {
        WFSCapabilitiesType wct = null;
        try {
            JAXBContext jaxbContext = null;
            wct = JAXB.unmarshal(new StringReader(capa), WFSCapabilitiesType.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return wct;
    }

    /**
     * Get Feature List of WFS
     *
     * @param wct
     * @return
     */
    public static List<FeatureTypeType> getFeatureList(WFSCapabilitiesType wct) {
        List<FeatureTypeType> ftts = wct.getFeatureTypeList().getFeatureType();
        return ftts;
    }

    /**
     * create a describefeaturetype request
     *
     * @param type
     * @return
     */
    public static DescribeFeatureTypeType createADescribeFeatureTypeRequest(String type) {
        ObjectFactory of = new ObjectFactory();
        DescribeFeatureTypeType gft = of.createDescribeFeatureTypeType();
        List qnames = new ArrayList();
        qnames.add(new QName(type.split(":")[0], type.split(":")[1]));
        gft.setTypeName(qnames);
        return gft;
    }

    public static String turnDescribeFeatureTypeTypeToXML(DescribeFeatureTypeType dftt) {
        String theXML = null;
        try {
            // serialise to xml
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(DescribeFeatureTypeType.class);
            Marshaller m = context.createMarshaller();
            QName qName = new QName("http://www.opengis.net/wfs/2.0", "DescribeFeatureType");
            JAXBElement<DescribeFeatureTypeType> root = new JAXBElement<>(qName, DescribeFeatureTypeType.class, dftt);
            m.marshal(root, writer);
            // output string to console
            theXML = writer.toString();
//			System.out.println(theXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theXML;
    }

    /**
     * Parse describefeaturetype response
     *
     * @param xml
     * @return
     */
    public static FeatureTypeListType parseDescribeFeatureTypeResponse(String xml) {
        return null;
    }

    public static URL getEndpoint(WFSCapabilitiesType wct) throws MalformedURLException {
        List<Operation> opers = wct.getOperationsMetadata().getOperation();
        String exeurl = null;
        for (int i = 0; i < opers.size(); i++) {
            Operation oper = opers.get(i);
            if ("GetFeature".equals(oper.getName())) {
                exeurl = oper.getDCP().get(0).getHTTP().getGetOrPost().get(0).getValue().getHref();
            }
        }
        return new URL(exeurl);
    }
}

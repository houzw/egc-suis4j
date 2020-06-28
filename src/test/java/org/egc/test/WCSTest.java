package org.egc.test;

import net.opengis.wcs.v_1_0_0.WCSCapabilitiesType;
import org.egc.ws.client.WSClient;
import org.egc.ws.driver.ServiceType;
import org.egc.ws.driver.WCSUtils;
import org.egc.ws.profile.Message;
import org.egc.ws.profile.Operation;
import org.junit.Test;

/**
 * @author houzhiwei
 * @date 2020/5/15 18:49
 */

public class WCSTest {

    @Test
    public void wcs() throws Exception {
        String wcsurl = "http://cube.csiss.gmu.edu/cgi-bin/gbwcs-dem.cgi?service=wcs&request=getcapabilities&version=1.0.0";
//		String wcsurl = "http://ows9.csiss.gmu.edu/cgi-bin/WCS20-r?service=wcs&request=getcapabilities&version=2.0.0";
//		String wcsurl = "https://tb12.cubewerx.com/a045/cubeserv?DATASTORE=Satellite_Soil_Moisture&SERVICE=WCS&REQUEST=GetCapabilities";
//		String wcsurl = "http://www3.csiss.gmu.edu/axis2swa/services/GMU_SOAP_WCS_Service.GMU_SOAP_WCS_ServiceHttpSoap12Endpoint/";
//		theLogger.info(wcsurl);
        /**
         * test parsing capability
         */
//		CapabilitiesType cat = WCSUtils.parseCapabilities(wcsurl);
//		CapabilitiesType cat = WCSUtils.parseCapabilities_SOAP(wcsurl);
//		theLogger.info("There are total "+cat.getContents().getCoverageSummary().size() + " coverages in this WCS.");
        WCSCapabilitiesType wct = WCSUtils.parse100Capabilities(wcsurl);
        System.out.println(WCSUtils.get100Endpoint(wct) + "");
        System.out.println(WCSUtils.get100CoverageListString(wct));

        /**
         * test describe coverage
         */
//		DescribeCoverageType dct = WCSUtils.createADescribeCoverageRequest("xyz", "2.0.0");
//		CoverageDescriptionsType cdt = WCSUtils.getCoverageDescription(wcsurl, dct);
        /**
         * test get coverage
         */
//		WCSUtils.getCoverage_SOAP(wcsurl, "GEOTIFF:\"/home/zsun/testfiles/data/2010_305_30H.tif\":Band", "image/GEOTIFF");
        /**
         * test security
         */
//		WCSUtils.parseCapabilities_Security("http://cube.csiss.gmu.edu/axis2secure/services/GMU_SOAP_WCS_Service.GMU_SOAP_WCS_ServiceHttpSoap12Endpoint/", "client", "apache");
    }

    @Test
    public void getCoverage() {
        WSClient sc = new WSClient.Builder()
                .initialize("http://ows9.csiss.gmu.edu/cgi-bin/WCS20-r?service=WCS&version=2.0.0&request=GetCapabilities", ServiceType.OGC)
                .build();

        sc.listOperations();
        Operation o = sc.operation("GetCoverage");

        sc.listInputParams(o);
        sc.listOutputParams(o);

        o.getInput().value("coverageId", "GEOTIFF:\"/home/zsun/testfiles/data/bay_dem.tif\":Band")
                .value("format", "image/geotiff");

        Message outm = sc.call(o);
        sc.listOutputValues(outm);
    }
}

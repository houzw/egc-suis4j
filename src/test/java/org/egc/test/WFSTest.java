package org.egc.test;

import org.junit.Test;

/**
 * @author houzhiwei
 * @date 2020/5/15 18:51
 */

public class WFSTest {

    @Test
    public void wfs() {
        /**
         *
         * test conventional WFS 2.0
         *
         */
////		String wfsurl = "http://ws.csiss.gmu.edu:8080/geoserver/wfs";
//
//		String wfsurl = "http://cube.csiss.gmu.edu/geoserver/topp/ows";
//
//		//String wfscapaurl = "http://cube.csiss.gmu.edu/geoserver/topp/ows?service=wfs&request=getcapabilities&version=2.0.0";
//
//		String wfscapaurl = "http://59.120.223.164:443/geoserver/ows?service=wfs&version=2.0.0&request=GetCapabilities";
//
//		WFSCapabilitiesType wct = WFSUtils.parseCapabilities(wfscapaurl);
//
//		FeatureTypeListType ftlt = wct.getFeatureTypeList();
//
//		System.out.println("Feature type nubmer : " + ftlt.getFeatureType().size());
//	//	BBOX box = new BBOX("EPSG:4326", -77.527282, 38.934311, -76.887893, 39.353648);
////		BBOX box = new BBOX("EPSG:32618", 280940.92757638777, 4312524.259656975, 337335.10634472733, 4357722.154561454);
////		try {
////			Vector<GeoFeature> gfVec2 = getWFSFeatures(wfsurl, box);
////			System.out.println("--------------------------------");
////			System.out.println("gfVec2.size()=" + gfVec2.size());
////			for(GeoFeature gf:gfVec2) {
////				System.out.println("Name=" + gf.getName());
////			}
////		} catch(Exception e) {
////			e.printStackTrace();
////		}
        /**
         *
         *  Test SOAP WFS 2.0
         *
         */
//	//	String wfsurl = "http://ws.csiss.gmu.edu:8080/geoserver/wfs";
//
//		String wfsurl = "http://polar.geodacenter.org/services/ows/wfs/soap/1.2";
//
//		//String wfscapaurl = "http://cube.csiss.gmu.edu/geoserver/topp/ows?service=wfs&request=getcapabilities&version=2.0.0";
//
//		//String wfscapaurl = "http://59.120.223.164:443/geoserver/ows?service=wfs&version=2.0.0&request=GetCapabilities";
//
////		BBOX bb = new BBOX("EPSG:4326", -122.5754909, 37.3862364, -121.861508, 38.0220317);
////
////		try {
////
////			Vector<GeoFeature> featurevec = WFSUtils.getWFSFeatures20_SOAP(wfsurl, bb);
////
////
//////			/**
//////			 *
//////			 * Test ASU WFS SOAP Capabilities
//////			 *
//////			 */
//////			WFSUtils.getWFSFeatures20("http://localhost:8080/capa.xml", bb);
////
////		} catch (Exception e) {
////
////			e.printStackTrace();
////
////		}
//
//		//test cache feature
//
//		WFSUtils.cacheFeatureSOAP("landuse", wfsurl);
//
        //WFSUtils.parseCapabilities_SOAP_Security("https://tb12.secure-dimensions.com/soap/services");
//		WFSUtils.cacheFeatureSOAPSecurity("cw:Elev_Contour", "https://tb12.secure-dimensions.com/soap/services");
    }
}

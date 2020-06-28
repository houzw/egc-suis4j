package org.egc.test;

import org.egc.ws.driver.WMSUtils;
import org.junit.Test;

/**
 * @author houzhiwei
 * @date 2020/5/15 19:41
 */

public class WMSTest {

    @Test
    public void wms() {
        WMSUtils.parseWMS("http://ogc.bgs.ac.uk/cgi-bin/exemplars/BGS_Bedrock_and_Superficial_Geology/ows?service=WMS&request=GetCapabilities&version=1.3.0");
        //WMSUtils.parseWMS("http://tb12.cubewerx.com/a041/cubeserv?","service=WMS&request=GetCapabilities&version=1.3.0");
        //WMSUtils.parseWMS("http://sampleserver1.arcgisonline.com/ArcGIS/services/Specialty/ESRI_StatesCitiesRivers_USA/MapServer/WMSServer?", "service=WMS&request=GetCapabilities&version=1.3.0");
    }

}

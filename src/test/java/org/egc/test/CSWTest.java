package org.egc.test;

import net.opengis.csw.v_2_0_2.GetRecordsResponseType;
import net.opengis.wcs.v_2_0.CapabilitiesType;
import org.egc.ws.driver.CSWUtils;
import org.junit.Test;

import static org.egc.ws.driver.CSWUtils.getRecords;

/**
 * @author houzhiwei
 * @date 2020/5/15 18:39
 */

public class CSWTest {
    @Test
    public void csw() throws Exception {
        CapabilitiesType ca = CSWUtils.parseCapability("http://tb12.essi-lab.eu/pubsub-csw/services/cswiso?service=CSW&version=2.0.2&request=GetCapabilities");
//		CapabilitiesType ca = CSWUtils.parseCapability("http://www3.csiss.gmu.edu/cnrcsw.xml");
        GetRecordsResponseType resp = getRecords(ca);
        System.out.println(resp.getSearchResults().getNumberOfRecordsMatched());
    }
}

代码整理自 SUIS (simple universal interface for services) for java 

https://github.com/ZihengSun/suis4j

 移除不必要的代码，移除依赖包的本地引用，改为从 maven 仓库下载
 
 ---
 问题：与 geotools 中的 opengis 库不兼容
 
 ---
 
 An example code of calling RESTful service (https://github.com/ZihengSun/suis4j/blob/master/README.md):
 ```java

 SUISClient sc = new SUISClient.Builder()
 	.initialize("https://service.iris.edu/irisws/timeseries/1/application.wadl", ServiceType.REST)
 	.build(); 
 		
 sc.listOperations();
 		
 Operation o = sc.operation("http://service.iris.edu/timeseries/1/version");
 
 sc.listInputParams(o);
 
 sc.listOutputParams(o);
 
 o.getInput()
 	.value("network", "IU")
 	.value("station", "ANMO")
 	.value("location", "00")
 	.value("channel", "BHZ")
 	.value("starttime", "2001-12-09T12:00:00")
 	.value("endtime", "2001-12-09T12:20:00")
 	.value("output", "plot");
 	
 Message outm = sc.call(o);
 		
 sc.listOutputValues(outm);
 
```


 An example code of calling SOAP service:
```java
 SUISClient sc = new SUISClient.Builder()
 	.initialize("http://www3.csiss.gmu.edu/GeoprocessingWS/services/Vector_Buffer_OGR?wsdl", ServiceType.SOAP)
 	.build(); 
 		
 sc.listOperations();
 		
 Operation o = sc.operation("buffer");
 		
 sc.listInputParams(o);
 		
 sc.listOutputParams(o);
 
 o.getInput()
 	.value("sourceURL", "http://www3.csiss.gmu.edu/data/building.zip")
 	.value("buffer", 100);
 	
 Message outm = sc.call(o);
 		
 sc.listOutputValues(outm);
```
 An example code of calling WPS 1.0.0:
```java
 SUISClient sc = new SUISClient.Builder()
 	.initialize("http://geoprocessing.demo.52north.org/latest-wps/WebProcessingService?Request=GetCapabilities&Service=WPS&version=1.0.0", ServiceType.OGC)
 	.build(); 
 		
 sc.listOperations();
 		
 Operation o = sc.operation("org.n52.wps.server.algorithm.SimpleBufferAlgorithm");
 		
 sc.listInputParams(o);
 
 sc.listOutputParams(o);
 
 o.getInput().value("data", "http://geoprocessing.demo.52north.org:8080/geoserver/wfs?SERVICE=WFS&VERSION=1.0.0&REQUEST=GetFeature&TYPENAME=topp:tasmania_roads&SRS=EPSG:4326&OUTPUTFORMAT=GML3")
 	.value("width", 0.05);
 	
 Message outm = sc.call(o);
 		
 sc.listOutputValues(outm);
```
 An example code of calling WCS 2.0.0:
```java

 SUISClient sc = new SUISClient.Builder()
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

```
 An example code of calling WMS 1.3.0:
```java
 SUISClient sc = new SUISClient.Builder()
 	.initialize("http://cube.csiss.gmu.edu/geoserver/topp/ows?service=WMS&request=GetCapabilities&version=1.3.0", ServiceType.OGC)
 	.build(); 
 
 sc.listOperations();
 		
 Operation o = sc.operation("GetMap");
 		
 sc.listInputParams(o);
 
 sc.listOutputParams(o);
 
 o.getInput().value("layers", "topp:states")
 	.value("bbox", "-124.73142200000001,24.955967,-66.969849,49.371735")
 	.value("width", 768)
 	.value("height", 330)
 	.value("crs", "EPSG:4326")
 	.value("format", "image/jpeg");
 
 Message outm = sc.call(o);
 		
 sc.listOutputValues(outm);
``` 

 An example code of calling WFS 2.0.0:
```java
 SUISClient sc = new SUISClient.Builder()
 	.initialize("http://cube.csiss.gmu.edu/geoserver/topp/ows?service=WFS&request=GetCapabilities&version=2.0.0", ServiceType.OGC)
 	.build(); 
 
 sc.listOperations();
 		
 Operation o = sc.operation("GetFeature");
 		
 sc.listInputParams(o);
 
 sc.listOutputParams(o);
 
 o.getInput().value("query", "typeNames=topp:tasmania_roads");
 
 Message outm = sc.call(o);
 		
 sc.listOutputValues(outm);
```
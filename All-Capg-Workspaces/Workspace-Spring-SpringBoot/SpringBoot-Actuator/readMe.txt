

http://localhost:9091/hello

Hello Actuator!

http://localhost:9091/actuator/health

{
"groups": [
"liveness",
"readiness"
],
"status": "UP"
}


Application Info
http://localhost:9091/actuator/info
(Default empty unless configured)
    oput is  :  { }
Beans Loaded in Application
http://localhost:9091/actuator/beans


All Mappings (URLs)
http://localhost:9091/actuator/mappings

Environment Properties
http://localhost:9091/actuator/env


Important Actuator Endpoints (Student Table)
Endpoint	             Purpose
/actuator/health	     Check app health
/actuator/info	         App information
/actuator/beans	         Loaded Spring beans
/actuator/mappings	     All URL mappings
/actuator/env	         Environment properties
/actuator/metrics       	Memory, CPU, requests
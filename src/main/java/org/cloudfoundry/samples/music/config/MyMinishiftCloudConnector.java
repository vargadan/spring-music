package org.cloudfoundry.samples.music.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.CloudConnector;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.MongoServiceInfo;

public class MyMinishiftCloudConnector implements CloudConnector{
	
	List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
	
	{
		String host = System.getenv("MONGODB_SERVICE_HOST");
		String port = System.getenv("MONGODB_SERVICE_PORT");
		MongoServiceInfo mongoService = new MongoServiceInfo("mongodb", host, Integer.parseInt(port), "userJ28", "IEwaAEMIR6goS63o", "spring-music");
		serviceInfos.add(mongoService);
	}
	
	ApplicationInstanceInfo appInfo = new ApplicationInstanceInfo() {

		@Override
		public String getInstanceId() {
			return System.getenv("HOSTNAME");
		}

		@Override
		public String getAppId() {
			return "spring-music";
		}

		@Override
		public Map<String, Object> getProperties() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};

	@Override
	public boolean isInMatchingCloud() {
		return true;
	}

	@Override
	public ApplicationInstanceInfo getApplicationInstanceInfo() {
		return appInfo;
	}

	@Override
	public List<ServiceInfo> getServiceInfos() {
		return serviceInfos;
	}

}

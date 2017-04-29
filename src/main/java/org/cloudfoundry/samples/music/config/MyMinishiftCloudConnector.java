package org.cloudfoundry.samples.music.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.CloudConnector;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.cloud.service.common.RedisServiceInfo;

public class MyMinishiftCloudConnector implements CloudConnector{
	
	List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
	
	{
		{
			String host = System.getenv("MONGODB_SERVICE_HOST");
			String port = System.getenv("MONGODB_SERVICE_PORT");
			MongoServiceInfo service = new MongoServiceInfo("mongodb", host, Integer.parseInt(port), "userJ28", "IEwaAEMIR6goS63o", "spring-music");
			serviceInfos.add(service);
		}
		
//		{
//			String host = System.getenv("REDIS_SERVICE_HOST");
//			String port = System.getenv("REDIS_SERVICE_PORT");
//			RedisServiceInfo service = new RedisServiceInfo("redis", host, Integer.parseInt(port), null);
//			serviceInfos.add(service);	
//		}
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

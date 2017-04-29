package org.cloudfoundry.samples.music.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
		{
			String host = System.getenv("MONGODB_SERVICE_HOST");
			String port = System.getenv("MONGODB_SERVICE_PORT");
			String username = readSecretFile("/secret/mongodb/database-user");
			String password = readSecretFile("/secret/mongodb/database-password");
			MongoServiceInfo service = new MongoServiceInfo("mongodb", host, Integer.parseInt(port), username, password, "spring-music");
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
			return System.getenv("OPENSHIFT_BUILD_NAMESPACE");
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
	
	String readSecretFile(String path) {
		String secret = null;
		try(FileInputStream fis = new FileInputStream(path)) {
			int size = fis.available();
			byte[] bytes = new byte[size];
			fis.read(bytes);
			secret = new String(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return secret;
	}

}

/*******************************************************************************
* Copyright (c) 2013 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package com.acmeair.astyanax.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import com.netflix.config.DynamicPropertyFactory;

public class AstyanaxContextHelper {

	private static final Log LOG = LogFactory
			.getLog(AstyanaxContextHelper.class);

	private static final AstyanaxContext<Keyspace> context;

	static {

		DynamicPropertyFactory dpf = DynamicPropertyFactory.getInstance();

		String host = dpf.getStringProperty("cassandra.host", "localhost")
				.get();
		int port = dpf.getIntProperty("cassandra.port", 9160).get();
		int maxConns = dpf.getIntProperty("cassandra.maxConnectionsPerHost", 1)
				.get();
		
		LOG.info(String.format("Connection to cassandra %s:%d with %d max connections", host,port,maxConns));

		context = new AstyanaxContext.Builder()
				.forCluster("ClusterName")
				.forKeyspace("acmeair")
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl()
								.setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE))
				.withConnectionPoolConfiguration(
						new ConnectionPoolConfigurationImpl("MyConnectionPool")
								.setPort(port).setMaxConnsPerHost(maxConns)
								.setSeeds(host))
				.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
	}

	public Keyspace getKeyspace() {
		return context.getClient();
	}
}

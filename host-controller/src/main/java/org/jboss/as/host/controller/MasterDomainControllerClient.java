/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 *
 */
package org.jboss.as.host.controller;

import java.io.IOException;

import org.jboss.as.controller.Cancellable;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.repository.HostFileRepository;
import org.jboss.msc.service.ServiceName;

/**
 * Client for interacting with the master {@link MasterDomainControllerClient} on a remote host.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 */
public interface MasterDomainControllerClient extends ModelControllerClient {

    /** Standard service name to use for a service that returns a MasterDomainControllerClient */
    ServiceName SERVICE_NAME = ServiceName.JBOSS.append("domain", "controller", "connection");

    /**
     * Register with the remote domain controller
     *
     * @throws IOException if there was a problem talking to the remote host
     */
    void register() throws IOException;

    /**
     * Unregister with the remote domain controller.
     */
    void unregister();

    /**
     * Gets a {@link HostFileRepository} capable of retrieving files from the
     * master domain controller.
     *
     * @return the file repository
     */
    HostFileRepository getRemoteFileRepository();

    /**
     * Pulls down missing data from the domain controller and applies it to the local model as a result of a change to a/an added server-config
     *
     * @param context the operation context
     * @param original the original domain model before the change
     * @throws OperationFailedException
     */
    void fetchAndSyncMissingConfiguration(OperationContext context, Resource original) throws OperationFailedException;

    /**
     * Repeatedly try to connect to the domain controller until successful. Should only
     * be called after a call to {@link #register()} has failed.
     *
     * @return handle to allow polling to be cancelled
     */
    Cancellable pollForConnect();

    /**
     * Report to the domain controller that a server has been reported as unstable.
     * @param serverName  the name of the server
     */
    default void reportServerInstability(String serverName) {
        // default no-op because I'm tired of writing no-op impls in testsuite classes
    }
}

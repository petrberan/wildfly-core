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
package org.jboss.as.controller.interfaces;

import java.io.ObjectStreamException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * {@link InterfaceCriteria} that tests whether a given address is not
 * {@link InetAddress#isSiteLocalAddress() site-local},
 * {@link InetAddress#isLinkLocalAddress() link-local}
 * or a {@link InetAddress#isAnyLocalAddress() wildcard address}.
 *
 * @author Brian Stansberry
 */
public class PublicAddressInterfaceCriteria extends AbstractInterfaceCriteria {

    private static final long serialVersionUID = 8151472860427215473L;

    public static final PublicAddressInterfaceCriteria INSTANCE = new PublicAddressInterfaceCriteria();

    private PublicAddressInterfaceCriteria() {}

    /**
     * {@inheritDoc}
     *
     * @return <code>address</code> if <code>address</code> is not
     *         {@link InetAddress#isSiteLocalAddress() site-local},
     *         {@link InetAddress#isLinkLocalAddress() link-local}
     *         or a {@link InetAddress#isAnyLocalAddress() wildcard address}.
     */
    @Override
    protected InetAddress isAcceptable(NetworkInterface networkInterface, InetAddress address) throws SocketException {

        if( !address.isSiteLocalAddress() && !address.isLinkLocalAddress() && !address.isAnyLocalAddress() )
            return address;
        return null;
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}

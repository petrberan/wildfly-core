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

package org.jboss.as.controller.transform;

import java.util.List;

import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathAddress;

/**
 * A potentially remote target requiring transformation.
 *
 * @author Emanuel Muckenhuber
 */
public interface TransformationTarget {

    /**
     * Get the version of this target.
     *
     * @return the model version
     */
    ModelVersion getVersion();

    /**
     * Get the subsystem version.
     *
     * @param subsystemName the subsystem name
     * @return the version of the specified subsystem, {@code null} if it does not exist
     */
    ModelVersion getSubsystemVersion(String subsystemName);

    /**
     * Get the transformer entry.
     * @param context TODO
     * @param address the path address
     *
     * @return the transformer entry
     */
    TransformerEntry getTransformerEntry(TransformationContext context, PathAddress address);

    /**
     * Get path transformations.
     *
     * @param address the path address
     * @return a list of registered path transformers
     */
    List<PathAddressTransformer> getPathTransformation(PathAddress address);

    /**
     * Resolve a resource transformer for agiven address.
     * @param context TODO
     * @param address the path address
     *
     * @return the transformer
     */
    ResourceTransformer resolveTransformer(ResourceTransformationContext context, PathAddress address);

    /**
     * Resolve an operation transformer for a given address.
     * @param context TODO
     * @param address the address
     * @param operationName the operation name
     *
     * @return the operation transformer
     */
    OperationTransformer resolveTransformer(TransformationContext context, PathAddress address, String operationName);

    /**
     * Add version information for a subsystem.
     *
     * @param subsystemName the name of the subsystem. Cannot be {@code null}
     * @param majorVersion the major version of the subsystem's management API
     * @param minorVersion the minor version of the subsystem's management API
     */
    void addSubsystemVersion(String subsystemName, int majorVersion, int minorVersion);

    /**
     * Add version information for a subsystem.
     *
     * @param subsystemName the subsystem name
     * @param version the version
     */
    void addSubsystemVersion(String subsystemName, ModelVersion version);

    /**
     * Get the type of the target.
     *
     * @return the target type
     */
    TransformationTargetType getTargetType();

    /**
     * Get the name of the host we are talking to
     */
    String getHostName();

    /**
     * Gets whether this target can make its list of ignored resources known when it registers.
     *
     * @return {@code true} if the target can provide the ignored resources list; {@code false} if that is not supported.
     */
    boolean isIgnoredResourceListAvailableAtRegistration();

    boolean isIgnoreUnaffectedConfig();

    enum TransformationTargetType {

        DOMAIN,
        HOST,
        SERVER,
        ;
    }
}

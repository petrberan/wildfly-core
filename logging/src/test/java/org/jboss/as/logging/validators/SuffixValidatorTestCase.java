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

package org.jboss.as.logging.validators;

import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class SuffixValidatorTestCase {

    @Test
    public void testValidator() throws Exception {
        final SuffixValidator validator = new SuffixValidator();
        try {
            validator.validateParameter("suffix", new ModelNode("s"));
            Assert.assertTrue("The model should be invalid", false);
        } catch (OperationFailedException e) {
            // no-op
        }
        try {
            //invalid pattern with one single quote
            validator.validateParameter("suffix", new ModelNode(".yyyy-MM-dd'custom suffix"));
            Assert.assertTrue("The model should be invalid", false);
        } catch (OperationFailedException e) {
            // no-op
        }
        //valid pattern with custom suffix
        validator.validateParameter("suffix", new ModelNode(".yyyy-MM-dd'custom suffix'"));
    }

    @Test
    public void testCompressionSuffixes() {
        final SuffixValidator validator = new SuffixValidator();

        // A pattern should be able to end with .zip
        try {
            validator.validateParameter("suffix", new ModelNode(".yyyy-MM-dd'T'HH:mm.zip"));
        } catch (Exception e) {
            Assert.fail("Failed to allow .zip suffix: " + e.getMessage());
        }

        // A pattern should be able to end with .gz
        try {
            validator.validateParameter("suffix", new ModelNode(".yyyy-MM-dd'T'HH:mm.gz"));
        } catch (Exception e) {
            Assert.fail("Failed to allow .gz suffix: " + e.getMessage());
        }
    }
}

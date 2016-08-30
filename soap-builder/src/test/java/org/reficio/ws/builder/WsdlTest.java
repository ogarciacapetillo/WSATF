/**
 * Copyright (c) 2012-2013 Reficio (TM) - Reestablish your software!. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.reficio.ws.builder;


import org.junit.Test;
import org.reficio.ws.SoapBuilderException;
import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.common.ResourceUtils;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WsdlTest {

    @Test(expected = NullPointerException.class)
    public void testParseNullUrl() {
        Wsdl.parse((String) null);
    }

    @Test(expected = SoapBuilderException.class)
    public void testParseWrongUrl() throws MalformedURLException {
        URL wsdlUrl = new URL("http://asd.com/asd.wsdl");
        Wsdl parser = Wsdl.parse(wsdlUrl);
        assertNotNull(parser);
    }

    @Test
    public void testParseTestWsdl() {
        URL wsdlUrl = ResourceUtils.getResourceWithAbsolutePackagePath("wsdl", "TestService.wsdl");
        Wsdl parser = Wsdl.parse(wsdlUrl);
        assertNotNull(parser);

        List<QName> bindings = parser.getBindings();
        String expectedBindingString = "{http://schemas.eviware.com/TestService/v1/}TestServiceSoap";
        QName expectedBinding = QName.valueOf(expectedBindingString);

        assertEquals(1, bindings.size());
        assertEquals(expectedBinding, bindings.iterator().next());

        assertNotNull(parser.binding().name(expectedBindingString).find());
        assertNotNull(parser.binding().name(expectedBinding).find());
    }

    @Test(expected = NullPointerException.class)
    public void testParseTestWsdlNullContext() {
        URL wsdlUrl = ResourceUtils.getResourceWithAbsolutePackagePath("wsdl", "TestService.wsdl");
        Wsdl parser = Wsdl.parse(wsdlUrl);
        String expectedBindingString = "{http://schemas.eviware.com/TestService/v1/}TestServiceSoap";
        parser.binding().name(expectedBindingString).find(null);
    }

    @Test
    public void testParseTestWsdlProperContext() {
        URL wsdlUrl = ResourceUtils.getResourceWithAbsolutePackagePath("wsdl", "TestService.wsdl");
        Wsdl parser = Wsdl.parse(wsdlUrl);
        String expectedBindingString = "{http://schemas.eviware.com/TestService/v1/}TestServiceSoap";
        SoapContext context = SoapContext.builder().typeComments(true).build();
        SoapBuilder builder = parser.binding().name(expectedBindingString).find(context);

        assertEquals(context, builder.getContext());
    }

}

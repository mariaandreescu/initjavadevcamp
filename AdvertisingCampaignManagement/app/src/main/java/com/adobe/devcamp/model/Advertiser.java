/*
 ************************************************************************
  * ADOBE CONFIDENTIAL
  * ___________________
  *
  * Copyright 2018 Adobe Systems Incorporated
  * All Rights Reserved.
  *
  * NOTICE:  All information contained herein is, and remains
  * the property of Adobe Systems Incorporated and its suppliers,
  * if any.  The intellectual and technical concepts contained
  * herein are proprietary to Adobe Systems Incorporated and its
  * suppliers and are protected by all applicable intellectual property laws,
  * including trade secret and copyright laws.
  * Dissemination of this information or reproduction of this material
  * is strictly forbidden unless prior written permission is obtained
  * from Adobe Systems Incorporated.
  ***********************************************************************
 */

package com.adobe.devcamp.model;

import java.util.List;
import java.util.Objects;

public final class Advertiser {
    private final String name;
    private final List<Integer> publishers;

    public Advertiser(String name, List<Integer> publishers) {
        this.name = name;
        this.publishers = publishers;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getPublishers() {
        return publishers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advertiser)) {
            return false;
        }
        final Advertiser that = (Advertiser) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPublishers(), that.getPublishers());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getPublishers());
    }

    @Override
    public String toString() {
        return "Advertiser{" +
                "name='" + name + '\'' +
                ", publishers=" + publishers +
                '}';
    }


}
